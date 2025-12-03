package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository;

import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity,Long> {
}
