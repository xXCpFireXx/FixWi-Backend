package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository;

import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {
}
