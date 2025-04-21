package com.userservice.controller;

import com.userservice.dto.AuthRequest;
import com.userservice.dto.AuthResponse;
import com.userservice.dto.UserDto;
import com.userservice.model.MyUserDetails;
import com.userservice.model.User;
import com.userservice.service.UserServiceImpl;
import java.util.Objects;

import com.userservice.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired UserServiceImpl userService;
  @Autowired JwtUtil jwtUtil;
  @Autowired AuthenticationManager authenticationManager;

  @PostMapping("/register")
  public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
    System.out.println(userDto);

    userService.addUser(userDto);
    return new ResponseEntity<>("User details Created successfully", HttpStatus.CREATED);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
    User user = userService.getUser(userId);
    if (Objects.isNull(user)) {
      return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(user, HttpStatus.OK);
    }
  }

  @DeleteMapping("{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
    User user = userService.getUser(userId);
    if (Objects.isNull(user)) {
      return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>("Successfully Deleted", HttpStatus.OK);
    }
  }

  @PutMapping("{userId}")
  public ResponseEntity<?> updateUser(
      @PathVariable("userId") String userId, @RequestBody UserDto userDto) {
    User user = userService.getUser(userId);
    if (Objects.isNull(user)) {
      return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
    } else {
      userService.updateUser(user, userDto);
      return new ResponseEntity<>("Successfully Updated", HttpStatus.OK);
    }
  }
  
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest){
    System.out.println("authenticate");
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
    System.out.println("authenticated");
    if(authenticate.isAuthenticated()) {
      MyUserDetails userDetails = (MyUserDetails) authenticate.getPrincipal();
      AuthResponse authResponse = AuthResponse.builder()
              .userId(userDetails.getUserId()).userName(userDetails.getUsername())
              .email(userDetails.getEmail())
              .jwt(jwtUtil.generateToken(authRequest.getUserName()))
              .build();
      return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }
    return new ResponseEntity<>("Invalid User",HttpStatus.BAD_REQUEST);
  }
}
