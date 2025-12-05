package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.out.UserPersistencePort;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper.UserMapper;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findUserById(Long id) {
        return userJpaRepository.findById(id)
                // Si se encuentra la entidad mapeara a un objeto de dominio
                .map(userMapper::toDomain);
    }
}