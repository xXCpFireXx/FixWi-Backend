package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

// output port, ticket persistence
public interface TicketPersistencePort {

    // this line saves a new or existing ticket in the database
    Ticket saveTicket(Ticket ticket);
    //GET
    Page<Ticket> findAll(Pageable pageable, String status, String category);
    Page<Ticket> findAllForUser(Pageable pageable, String status, String category, String email);
    Optional<Ticket> findById(Long id);

}