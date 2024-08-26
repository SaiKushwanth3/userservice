package com.userservice.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.userservice.dto.UserDto;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import java.security.SecureRandom;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired UserRepository userRepository;

  @Override
  public User addUser(UserDto userDto) {

    System.out.println(userDto.getUserName());
    User user =
        User.builder()
            .userId(generateId())
            .userName(userDto.getUserName())
            .email(userDto.getEmail())
            .mobileNumber(userDto.getMobileNumber())
            .password(userDto.getPassword())
            .build();
    return userRepository.save(user);
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public void removeUser(String userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public User getUser(String userId) {
    return userRepository.findById(userId).orElse(null);
  }

  @Override
  public void updateUser(User user, UserDto userDto) {
    user.setUserName(userDto.getUserName());
    user.setEmail(userDto.getEmail());
    user.setDateOfBirth(userDto.getDateOfBirth());
    user.setMobileNumber(userDto.getMobileNumber());

    userRepository.save(user);
  }

  @Override
  public String generateId() {
    char[] numbers = "0123456789".toCharArray();

    // SecureRandom instance for cryptographic security
    SecureRandom random = new SecureRandom();

    // Generate a NanoID of length 7 using the custom numbers
    return NanoIdUtils.randomNanoId(random, numbers, 7);
  }
}
