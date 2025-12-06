package com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.mapper;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.infrastructure.adapter.out.persistence.jpa.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // Inyecta los mappers por constructor
public class TicketMapper {

    private final UserMapper userMapper;
    private final CategoryMapper categoryMapper;

    public TicketEntity toEntity(Ticket domain) {
        if (domain == null) {
            return null;
        }

        // Mapeamos las entidades relacionadas (User y Category)
        return TicketEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .createDate(domain.getCreateDate())
                .updateDate(domain.getUpdateDate())
                // Las relaciones se mapean de Domain Model a Entity
                .user(userMapper.toEntity(domain.getUser()))
                .category(categoryMapper.toEntity(domain.getCategory()))
                .build();
    }

    public Ticket toDomain(TicketEntity entity) {
        if (entity == null) {
            return null;
        }

        // Usamos el constructor existente del modelo de dominio (Ticket.java)
        return new Ticket(
                entity.getId(),
                // Las relaciones se mapean de Entity a Domain Model
                userMapper.toDomain(entity.getUser()),
                entity.getDescription(),
                entity.getTitle(),
                entity.getStatus(),
                categoryMapper.toDomain(entity.getCategory()),
                entity.getCreateDate(),
                entity.getUpdateDate()
        );
    }
}