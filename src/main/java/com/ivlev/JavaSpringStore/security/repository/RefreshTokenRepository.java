package com.ivlev.JavaSpringStore.security.repository;

import com.ivlev.JavaSpringStore.security.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.sql.Ref;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(UUID userId);

}
