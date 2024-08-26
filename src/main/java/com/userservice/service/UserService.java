package com.userservice.service;

import com.userservice.dto.UserDto;
import com.userservice.model.User;
import java.util.List;

public interface UserService {
  public User addUser(UserDto user);

  public void removeUser(String userId);

  public User getUser(String userId);

  public List<User> getAllUsers();

  public void updateUser(User user, UserDto userDto);

  public String generateId();
}
