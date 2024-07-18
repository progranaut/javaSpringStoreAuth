package com.ivlev.JavaSpringStore.security.service;

import com.ivlev.JavaSpringStore.security.entity.SecurityUser;
import com.ivlev.JavaSpringStore.security.feign.FeignImpl;
import com.ivlev.JavaSpringStore.security.web.dto.SendUserToApp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeignService {

    private final FeignImpl feign;

    public void sendUser(String jwt, SecurityUser securityUser) {

        feign.sendUser(jwt, SendUserToApp.builder()
                        .id(securityUser.getId().toString())
                        .email(securityUser.getName())
                        .roles(securityUser.getRoles().stream()
                                .map(roleType -> roleType.toString())
                                .collect(Collectors.toSet()))
                .build());

    }

}
