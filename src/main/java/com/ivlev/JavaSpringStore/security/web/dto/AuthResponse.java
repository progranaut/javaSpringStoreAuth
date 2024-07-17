package com.ivlev.JavaSpringStore.security.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private UUID id;

    private String token;

    private String refreshToken;

    private String username;

    private List<String> roles;

}
