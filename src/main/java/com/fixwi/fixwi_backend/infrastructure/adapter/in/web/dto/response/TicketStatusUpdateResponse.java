package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response;

import com.fixwi.fixwi_backend.domain.model.Status;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class TicketStatusUpdateResponse {
    Long id;
    String title;
    String description;
    Status status;
    String categoryName;
    Long userId;
    LocalDateTime createDate;
    LocalDateTime updateDate;
}
