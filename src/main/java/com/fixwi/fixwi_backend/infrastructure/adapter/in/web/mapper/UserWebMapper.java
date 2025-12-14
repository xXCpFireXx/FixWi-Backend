package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.mapper;

import com.fixwi.fixwi_backend.domain.model.Role;
import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request.RegisterRequest;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response.UserResponse; // (Opcional, ver nota abajo)
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {

    public User toDomain(RegisterRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(request.password());

        try {
            user.setRole(Role.valueOf(request.role().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            user.setRole(Role.USER);
        }

        return user;
    }

    public UserResponse toResponse(User domain) {
        if (domain == null) {
            return null;
        }
        return new UserResponse(
                domain.getId(),
                domain.getFullName(),
                domain.getEmail(),
                domain.getRole()
        );
    }
}