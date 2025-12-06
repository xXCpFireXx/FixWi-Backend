package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.User;

import java.util.Optional;

// este es para la persistencia del usuario
// Aca obtenemos  el usuario que crea el ticket

public interface UserPersistencePort {

    Optional<User> findUserById(Long id);
}