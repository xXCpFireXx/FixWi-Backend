package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request;

import com.fixwi.fixwi_backend.domain.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class TicketStatusUpdateRequest {
    @NotNull(message = "Status is required")
    Status status;
}
