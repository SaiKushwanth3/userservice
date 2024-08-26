package com.userservice.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document(collection = "User")
public class User {
  @Id private String userId;
  private String userName;
  private String email;
  private String password;
  private String mobileNumber;
  private LocalDate dateOfBirth;
}
