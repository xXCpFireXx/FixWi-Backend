package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response;

import com.fixwi.fixwi_backend.domain.model.Role;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role
){}