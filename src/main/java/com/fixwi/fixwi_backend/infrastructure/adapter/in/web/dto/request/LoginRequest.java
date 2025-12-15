package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "The email is required")
        @Email(message = "It must be a valid email")
        String email,

        @NotBlank(message = "The password is required")
        String password
) {}