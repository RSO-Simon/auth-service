package com.auth.mapper;

import com.auth.dto.AuthUserDto;
import com.auth.model.AppUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthUserMapper {

    @Mapping(target = "id", ignore = true)
    AppUserEntity toEntity(AuthUserDto dto);

    @Mapping(target = "ownerUserId", source = "id")
    AuthUserDto toDto(AppUserEntity entity);

}
