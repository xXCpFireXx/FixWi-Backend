package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        return UserEntity.builder()
                .id(domain.getId())
                .fullName(domain.getFullName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .role(domain.getRole())
                .build();
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new User(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole()
        );
    }
}