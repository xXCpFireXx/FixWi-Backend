package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.Category;

import java.util.Optional;

public interface CategoryPersistencePort {

    Optional<Category> findCategoryById(Long id);
}
