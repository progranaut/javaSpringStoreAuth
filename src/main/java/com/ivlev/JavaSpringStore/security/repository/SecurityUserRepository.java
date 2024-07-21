package com.ivlev.JavaSpringStore.security.repository;

import com.ivlev.JavaSpringStore.security.entity.SecurityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, UUID> {

    Optional<SecurityUser> findByName(String name);

    Boolean existsByName(String name);

}
