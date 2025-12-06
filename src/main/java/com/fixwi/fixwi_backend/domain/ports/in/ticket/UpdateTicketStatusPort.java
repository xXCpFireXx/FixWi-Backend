package com.fixwi.fixwi_backend.domain.ports.in.ticket;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.model.Status;

public interface UpdateTicketStatusPort {
    Ticket updateStatus(Long ticketId, Status newStatus);
}
