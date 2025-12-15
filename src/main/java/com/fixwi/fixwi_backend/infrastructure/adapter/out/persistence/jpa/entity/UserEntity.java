package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity;

import com.fixwi.fixwi_backend.domain.model.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

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

    @Column(name = "full_name", nullable = false, length = 80)
    private String fullName;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    // contra encriptada
    @Column(nullable = false, length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, length = 20)
    private Role role;

    // Un usuario puede crear muchos tickets
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<TicketEntity> tickets;

}
