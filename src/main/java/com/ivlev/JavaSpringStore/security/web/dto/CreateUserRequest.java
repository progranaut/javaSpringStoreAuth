package com.ivlev.JavaSpringStore.security.web.dto;

import com.ivlev.JavaSpringStore.security.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String username;

    private String password;

    private Set<RoleType> roles;

}
