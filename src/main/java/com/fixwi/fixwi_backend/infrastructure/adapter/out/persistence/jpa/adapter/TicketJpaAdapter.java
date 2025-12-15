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
        // Mapeo el objeto de dominio a la entidad JPA
        var ticketEntity = ticketMapper.toEntity(ticket);

        // Guardo la entidad en la bd
        var savedEntity = ticketJpaRepository.save(ticketEntity);

        // Mapeo la entidad guardada (ID y fechas actualizadas) de vuelta al dominio
        return ticketMapper.toDomain(savedEntity);
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable, String status, String category) {
        Specification<TicketEntity> specification = (root, query, cb) -> cb.conjunction();
        if (status != null && !status.isEmpty()){
            specification = specification.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if(category != null && !category.isEmpty()){
            specification = specification.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("category").get("name")), "%" + category.toLowerCase() + "%"));
        }
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