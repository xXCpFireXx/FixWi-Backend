package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.out.LoadUserPort;
import com.fixwi.fixwi_backend.domain.ports.out.SaveUserPort;
import com.fixwi.fixwi_backend.domain.ports.out.UserPersistencePort;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.UserEntity;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper.UserMapper;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPersistencePort, LoadUserPort, SaveUserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findUserById(Long id) {
        return userJpaRepository.findById(id)
                // Si se encuentra la entidad mapeara a un objeto de dominio
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> loadUserByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return userMapper.toDomain(saved);
    }
}