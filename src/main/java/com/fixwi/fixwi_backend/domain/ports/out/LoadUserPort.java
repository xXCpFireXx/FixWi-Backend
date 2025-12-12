package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadUserByEmail(String email);
    boolean existsByEmail(String email);
}
