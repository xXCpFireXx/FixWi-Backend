package com.fixwi.fixwi_backend.application.usecase.ticket;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.model.Status;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.UpdateTicketStatusPort;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class UpdateTicketStatusUseCase implements UpdateTicketStatusPort {

    private final TicketPersistencePort ticketPersistencePort;

    @Override
    public Ticket updateStatus(Long ticketId, Status newStatus) {
        var ticketOpt = ticketPersistencePort.findById(ticketId);

        if (ticketOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket not found: " + ticketId);
        }

        var ticket = ticketOpt.get();

        if (ticket.getStatus() == newStatus) {
            return ticket;
        }

        ticket.setStatus(newStatus);
        ticket.setUpdateDate(LocalDateTime.now());

        return ticketPersistencePort.saveTicket(ticket);
    }
}