package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fixwi.fixwi_backend.domain.model.Category;
import com.fixwi.fixwi_backend.domain.ports.out.CategoryPersistencePort;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper.CategoryMapper;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryJpaAdapter implements CategoryPersistencePort {

    private final CategoryJpaRepository categoryJpaRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryJpaRepository.findById(id)
                .map(categoryMapper::toDomain);
    }
}
