package com.auth.service;

import com.auth.dto.AuthUserDto;
import com.auth.mapper.AuthUserMapper;
import com.auth.model.AppUserEntity;
import com.auth.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthUserService {

    private final AppUserRepository repo;
    private final AuthUserMapper mapper;

    public AuthUserService(AppUserRepository repo, AuthUserMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Transactional
    public AuthUserDto upsert(AuthUserDto dto) {
        AppUserEntity entity = repo.findByGoogleSub(dto.googleSub)
                .map(existing -> {
                    if (dto.email != null) existing.setEmail(dto.email);
                    if (dto.displayName != null) existing.setDisplayName(dto.displayName);
                    return existing;
                })
                .orElseGet(() -> repo.save(mapper.toEntity(dto)));

        return mapper.toDto(entity);
    }
}