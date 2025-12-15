package com.fixwi.fixwi_backend.application.usecase.ticket;

import com.fixwi.fixwi_backend.domain.dto.TicketMetricsDTO;
import com.fixwi.fixwi_backend.domain.model.Status;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.GetMetricsTicketUseCase;
import com.fixwi.fixwi_backend.domain.ports.out.TicketMetricsPort;

public class TicketMetricsUseCase implements GetMetricsTicketUseCase {


    private final TicketMetricsPort ticketMetricsPort;

    public TicketMetricsUseCase(TicketMetricsPort ticketMetricsPort) {
        this.ticketMetricsPort = ticketMetricsPort;
    }

    @Override
    public TicketMetricsDTO getGeneralMetrics() {
        Long total = ticketMetricsPort.countTotalTickets();
        Long close = ticketMetricsPort.countTicketsByStatus(Status.CLOSE);
        Long open = ticketMetricsPort.countTicketsByStatus(Status.OPEN);
        Long inProgress = ticketMetricsPort.countTicketsByStatus(Status.IN_PROGRESS);
        return new TicketMetricsDTO(total,open,close,inProgress);
    }
}
