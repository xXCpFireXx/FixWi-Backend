package com.fixwi.fixwi_backend.domain.ports.in.ticket;

import com.fixwi.fixwi_backend.domain.model.Ticket;

// Entry port for the Ticket Creation use case - interface for the service
public interface CreateTicketPort {

    Ticket createTicket(Ticket ticketToCreate);
}
