package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "tickets")
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
