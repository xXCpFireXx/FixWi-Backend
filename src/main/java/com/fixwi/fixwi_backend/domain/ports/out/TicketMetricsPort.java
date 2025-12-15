package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.Status;

public interface TicketMetricsPort {
    Long countTotalTickets();
    Long countTicketsByStatus(Status status);
}
