package com.fixwi.fixwi_backend.domain.ports.in.ticket;

import com.fixwi.fixwi_backend.domain.model.Ticket;

// Puerto de entrada para el caso de uso de Creación de Tickets - interface para el service
public interface CreateTicketPort {

    Ticket createTicket(Ticket ticketToCreate);
}
