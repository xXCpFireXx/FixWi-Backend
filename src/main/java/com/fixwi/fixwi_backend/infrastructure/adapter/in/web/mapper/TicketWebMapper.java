package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.mapper;

import com.fixwi.fixwi_backend.domain.model.Category;
import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request.TicketCreationRequest;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response.TicketCreationResponse;
import org.springframework.stereotype.Component;

// Mapper para convertir el DTO de entrada al Modelo de Dominio
@Component
public class TicketWebMapper {

    public Ticket toDomain(TicketCreationRequest request) {
        if (request == null) {
            return null;
        }

        var ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());

        // Se adjuntan solo los IDs de las entidades relacionadas
        // (El Use Case será responsable de buscar los objetos completos)
        ticket.setUser(new User());
        ticket.getUser().setId(request.getUserId());

        ticket.setCategory(new Category());
        ticket.getCategory().setId(request.getCategoryId());

        return ticket;
    }

    public TicketCreationResponse toResponse(Ticket domain) {
        if (domain == null) {
            return null;
        }

        return new TicketCreationResponse(
                domain.getId(),
                domain.getTitle(),
                domain.getDescription(),
                domain.getStatus(),
                domain.getCategory() != null ? domain.getCategory().getName() : null,
                domain.getUser() != null ? domain.getUser().getId() : null,
                domain.getCreateDate()
        );
    }
}
