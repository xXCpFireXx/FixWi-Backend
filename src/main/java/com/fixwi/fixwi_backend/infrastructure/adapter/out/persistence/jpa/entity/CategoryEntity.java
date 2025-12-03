package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
