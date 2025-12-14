package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository;

import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.TicketEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketJpaRepository extends JpaRepository<TicketEntity,Long>, JpaSpecificationExecutor<TicketEntity> {

    @Override
    @EntityGraph(attributePaths = {"user", "category"})
    Page<TicketEntity> findAll(Specification<TicketEntity> spec, Pageable pageable);

}
