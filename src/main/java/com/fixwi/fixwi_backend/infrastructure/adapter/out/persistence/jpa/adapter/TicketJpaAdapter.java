package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper.TicketMapper;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository.TicketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketJpaAdapter implements TicketPersistencePort {

    private final TicketJpaRepository ticketJpaRepository;
    private final TicketMapper ticketMapper;

    @Override
    public Ticket saveTicket(Ticket ticket) {
        // Mapeo el objeto de dominio a la entidad JPA
        var ticketEntity = ticketMapper.toEntity(ticket);

        // Guardo la entidad en la bd
        var savedEntity = ticketJpaRepository.save(ticketEntity);

        // Mapeo la entidad guardada (ID y fechas actualizadas) de vuelta al dominio
        return ticketMapper.toDomain(savedEntity);
    }
}