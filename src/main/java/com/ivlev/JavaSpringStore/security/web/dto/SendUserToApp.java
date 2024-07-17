package com.ivlev.JavaSpringStore.security.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendUserToApp {

    private String id;

    private String email;

    private Set<String> roles;

}
