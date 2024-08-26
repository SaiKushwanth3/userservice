package com.userservice.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private String userName;
  private String email;
  private String password;
  private String mobileNumber;
  private LocalDate dateOfBirth;
}
