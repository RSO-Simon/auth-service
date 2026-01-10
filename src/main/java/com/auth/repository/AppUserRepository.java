package com.auth.repository;

import com.auth.model.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {
    Optional<AppUserEntity> findByGoogleSub(String googleSub);
}
