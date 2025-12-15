package com.fixwi.fixwi_backend.domain.ports.in.ticket;

import com.fixwi.fixwi_backend.domain.dto.TicketMetricsDTO;

public interface GetMetricsTicketUseCase {
    TicketMetricsDTO getGeneralMetrics();
}
