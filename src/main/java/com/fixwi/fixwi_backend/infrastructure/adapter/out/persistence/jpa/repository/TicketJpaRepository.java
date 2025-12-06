package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.repository;

import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketJpaRepository extends JpaRepository<TicketEntity,Long>, JpaSpecificationExecutor<TicketEntity> {
}
