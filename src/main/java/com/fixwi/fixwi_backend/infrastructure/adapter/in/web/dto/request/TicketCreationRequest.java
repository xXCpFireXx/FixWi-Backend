package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;  // Se utiliza el Value para DTOs inmutables

@Value // Genera constructor y getters para todos los campos finales
public class TicketCreationRequest {

    @NotBlank(message = "Title cannot be empty")
    String title;

    @NotBlank(message = "Description cannot be empty")
    String description;

    @NotNull(message = "Category ID is required")
    Long categoryId;

    @NotNull(message = "User ID is required")
    Long userId;

}