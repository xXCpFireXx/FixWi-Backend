package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
