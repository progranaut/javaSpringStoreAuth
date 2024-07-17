package com.ivlev.JavaSpringStore.security.security;

import com.ivlev.JavaSpringStore.security.entity.SecurityUser;
import com.ivlev.JavaSpringStore.security.repository.SecurityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final SecurityUserRepository securityUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        SecurityUser securityUser = securityUserRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с именем " + username + " не найден!"));

        return new AppUserDetails(securityUser);
    }
}
