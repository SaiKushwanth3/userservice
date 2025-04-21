package com.userservice.dto;

import com.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String userId;
    private String userName;
    private String email;
    private String jwt;
}
