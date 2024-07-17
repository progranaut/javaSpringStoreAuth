package com.ivlev.JavaSpringStore.security.web.controller;

import com.ivlev.JavaSpringStore.security.entity.RefreshToken;
import com.ivlev.JavaSpringStore.security.exception.AlreadyExistException;
import com.ivlev.JavaSpringStore.security.repository.SecurityUserRepository;
import com.ivlev.JavaSpringStore.security.security.SecurityService;
import com.ivlev.JavaSpringStore.security.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final SecurityUserRepository securityUserRepository;

    private final SecurityService securityService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(securityService.authenticateUser(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {
        if (securityUserRepository.existsByName(createUserRequest.getUsername())) {
            throw new AlreadyExistException("Имя уже существует");
        }

        securityService.register(createUserRequest);

        return ResponseEntity.ok(new SimpleResponse("Пользователь создан."));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(securityService.refreshToken(request));
    }


    @PostMapping("/logout")
    public ResponseEntity<SimpleResponse> logout(@AuthenticationPrincipal UserDetails userDetails) {
        securityService.logout();
        return ResponseEntity.ok(new SimpleResponse("Пользователь вышел. Имя: " + userDetails.getUsername()));
    }
}
