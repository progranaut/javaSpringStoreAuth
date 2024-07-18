package com.ivlev.JavaSpringStore.security.security;

import com.ivlev.JavaSpringStore.security.entity.RefreshToken;
import com.ivlev.JavaSpringStore.security.entity.SecurityUser;
import com.ivlev.JavaSpringStore.security.exception.RefreshTokenException;
import com.ivlev.JavaSpringStore.security.repository.SecurityUserRepository;
import com.ivlev.JavaSpringStore.security.security.jwt.JwtUtils;
import com.ivlev.JavaSpringStore.security.service.FeignService;
import com.ivlev.JavaSpringStore.security.service.RefreshTokenService;
import com.ivlev.JavaSpringStore.security.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    private final SecurityUserRepository securityUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final FeignService feignService;

    @Value("${app.jwt.mainSender}")
    private String mainSender;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return AuthResponse.builder()
                .id(userDetails.getId())
                .token(jwtUtils.generateJwtToken(userDetails))
                .refreshToken(refreshToken.getToken())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();

    }

    public void register(CreateUserRequest createUserRequest) {
        SecurityUser securityUser = SecurityUser.builder()
                .name(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .roles(createUserRequest.getRoles())
                .build();

        securityUser = securityUserRepository.save(securityUser);

        String senderJwtToken = jwtUtils.generateTokenFromUsername(mainSender);

        feignService.sendUser("Bearer " + senderJwtToken, securityUser);

    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {

        String requestRefreshToken = refreshTokenRequest.getRefreshToken();

        return refreshTokenService.findByRefreshToken(requestRefreshToken)
                .map(token -> refreshTokenService.checkRefreshToken(token))
                .map(token -> token.getUserId())
                .map(userId -> {
                    SecurityUser tokenOwner = securityUserRepository.findById(userId).orElseThrow(()->
                        new RefreshTokenException("Ошибка при получении пользователя по айди: " + userId));
                    String token = jwtUtils.generateTokenFromUsername(tokenOwner.getName());
                    return RefreshTokenResponse.builder()
                            .accessToken(token)
                            .refreshToken(refreshTokenService.createRefreshToken(userId).getToken())
                            .build();
                }).orElseThrow(()-> new RefreshTokenException(requestRefreshToken, "Рефрешь токен не найден!"));

    }

    public void logout() {
        var currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentPrincipal instanceof AppUserDetails userDetails) {
            UUID userId = userDetails.getId();
            refreshTokenService.deleteByUserId(userId);
        }
    }

}
