package com.fixwi.fixwi_backend.application.usecase.ticket;

import com.fixwi.fixwi_backend.domain.exception.ResourceNotFoundException;
import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.FindTicketPort;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class FindTicketUseCase implements FindTicketPort {

    private final TicketPersistencePort ticketPersistencePort;

    public FindTicketUseCase(TicketPersistencePort ticketPersistencePort) {
        this.ticketPersistencePort = ticketPersistencePort;
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable, String status, String category) {
        return ticketPersistencePort.findAll(pageable, status,category);
    }

    @Override
    public Page<Ticket> findAllForUser(Pageable pageable, String status, String category, String email) {
        return ticketPersistencePort.findAllForUser(pageable, status, category, email);
    }

    @Override
    public Ticket findById(Long id) {
        return ticketPersistencePort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket", id));
    }
}
