package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.ports.out.TicketPersistencePort;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.TicketEntity;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper.TicketMapper;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository.TicketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TicketJpaAdapter implements TicketPersistencePort {

    private final TicketJpaRepository ticketJpaRepository;
    private final TicketMapper ticketMapper;
    @Transactional
    @Override
    public Ticket saveTicket(Ticket ticket) {
        // Map the domain object to the JPA entity
        var ticketEntity = ticketMapper.toEntity(ticket);

        // Persist the entity in the database
        var savedEntity = ticketJpaRepository.save(ticketEntity);

        // Map the saved entity (with updated ID and timestamps) back to the domain model
        return ticketMapper.toDomain(savedEntity);
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable, String status, String category) {
        // Start with a "no-op" specification (equivalent to TRUE), so we can safely AND additional filters
        Specification<TicketEntity> specification = (root, query, cb) -> cb.conjunction();

        // Optional filter: if status is provided, restrict results to tickets with the given status
        if (status != null && !status.isEmpty()){
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if(category != null && !category.isEmpty()){
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("category").get("name")), "%" + category.toLowerCase() + "%"));
        }

        // Execute the query with the composed specification and pagination, then map entities to domain objects
        return ticketJpaRepository.findAll(specification,pageable).map(ticketMapper::toDomain);
    }

    @Override
    public Page<Ticket> findAllForUser(Pageable pageable, String status, String category, String email) {
        Specification<TicketEntity> specification = (root, query, cb) -> cb.conjunction();

        specification = specification.and((root, query, cb) ->
                cb.equal(cb.lower(root.get("user").get("email")), email.toLowerCase()));

        if (status != null && !status.isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if (category != null && !category.isEmpty()) {
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("category").get("name")), "%" + category.toLowerCase() + "%"));
        }

        return ticketJpaRepository.findAll(specification,pageable).map(ticketMapper::toDomain);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Ticket> findById(Long id) {
        return ticketJpaRepository.findById(id).map(ticketMapper::toDomain);
    }


}