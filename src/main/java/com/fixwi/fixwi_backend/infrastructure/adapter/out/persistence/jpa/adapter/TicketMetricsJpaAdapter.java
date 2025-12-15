package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fixwi.fixwi_backend.domain.model.Status;
import com.fixwi.fixwi_backend.domain.ports.out.TicketMetricsPort;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository.TicketJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TicketMetricsJpaAdapter implements TicketMetricsPort {

    private final TicketJpaRepository ticketJpaRepository;

    public TicketMetricsJpaAdapter(TicketJpaRepository ticketJpaRepository) {
        this.ticketJpaRepository = ticketJpaRepository;
    }

    @Override
    public Long countTotalTickets() {
        return ticketJpaRepository.count();
    }

    @Override
    public Long countTicketsByStatus(Status status) {
        return ticketJpaRepository.countByStatus(status);
    }
}
