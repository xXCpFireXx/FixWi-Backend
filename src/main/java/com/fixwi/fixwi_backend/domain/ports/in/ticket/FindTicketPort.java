package com.fixwi.fixwi_backend.domain.ports.in.ticket;


import com.fixwi.fixwi_backend.domain.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindTicketPort {
    Page<Ticket> findAll(Pageable pageable,String status,String category);
    Page<Ticket> findAllForUser(Pageable pageable, String status, String category, String email);
    Ticket findById(Long id);

}
