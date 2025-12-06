package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.Ticket;

import java.util.Optional;

// puerto de salida, es la persistencia del ticket
public interface TicketPersistencePort {

    // esta linea guarda un ticket nuevo o existente en la bd
    Ticket saveTicket(Ticket ticket);

    Optional<Ticket> findTicketById(Long id);
}