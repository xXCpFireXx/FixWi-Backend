package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.User;

import java.util.Optional;

public interface UserPersistencePort {
    Optional<User> findUserById(Long id);
}
