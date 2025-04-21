package com.userservice.service;

import com.userservice.model.MyUserDetails;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(user);
        return new MyUserDetails(user.getUserId(),user.getUserName(),user.getEmail(),user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
