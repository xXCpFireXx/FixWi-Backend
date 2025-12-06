package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.fixwi.fixwi_backend.domain.model.Category;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryEntity toEntity(Category domain) {
        if (domain == null) {
            return null;
        }
        return CategoryEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    public Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Category(
                entity.getId(),
                entity.getName()
        );
    }
}
