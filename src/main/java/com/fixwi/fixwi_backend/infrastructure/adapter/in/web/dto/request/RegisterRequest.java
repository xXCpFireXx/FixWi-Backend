package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterRequest(

        @NotBlank(message = "User's full name is required")
        String fullName,

        @NotBlank(message = "The email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "The password is required")
        String password,

        @NotNull(message = "User's role is required")
        String role
){}