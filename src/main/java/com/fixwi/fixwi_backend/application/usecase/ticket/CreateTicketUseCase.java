package com.fixwi.fixwi_backend.application.usecase.ticket;

import com.fixwi.fixwi_backend.domain.exception.ResourceNotFoundException;
import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.model.Status;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.CreateTicketPort;
import com.fixwi.fixwi_backend.domain.ports.out.CategoryPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import com.fixwi.fixwi_backend.domain.ports.out.UserPersistencePort;
import java.time.LocalDateTime;

public class CreateTicketUseCase implements CreateTicketPort {

    private final TicketPersistencePort ticketPersistencePort;
    private final UserPersistencePort userPersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;

    public CreateTicketUseCase(
            TicketPersistencePort ticketPersistencePort,
            UserPersistencePort userPersistencePort,
            CategoryPersistencePort categoryPersistencePort) {
        this.ticketPersistencePort = ticketPersistencePort;
        this.userPersistencePort = userPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Ticket createTicket(Ticket ticketToCreate) {

        if (ticketToCreate.getUser() == null || ticketToCreate.getUser().getId() == null) {
            throw new ResourceNotFoundException("Ticket must be associated with a creator user ID.");
        }

        var userCreator = userPersistencePort.findUserById(ticketToCreate.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + ticketToCreate.getUser().getId()));

        if (ticketToCreate.getCategory() == null || ticketToCreate.getCategory().getId() == null) {
            throw new ResourceNotFoundException("Ticket must have a Category ID.");
        }

        var category = categoryPersistencePort.findCategoryById(ticketToCreate.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + ticketToCreate.getCategory().getId()));

        ticketToCreate.setStatus(Status.OPEN);
        ticketToCreate.setUser(userCreator);
        ticketToCreate.setCategory(category);

        var now = LocalDateTime.now();
        ticketToCreate.setCreateDate(now);
        ticketToCreate.setUpdateDate(now);

        return ticketPersistencePort.saveTicket(ticketToCreate);
    }
}