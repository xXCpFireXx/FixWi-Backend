package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.ports.in.ticket.CreateTicketPort;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request.TicketCreationRequest;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response.TicketCreationResponse;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.mapper.TicketWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final CreateTicketPort createTicketPort;
    private final TicketWebMapper ticketWebMapper;

    @PostMapping
    public ResponseEntity<TicketCreationResponse> createTicket(@Valid @RequestBody TicketCreationRequest request) {

        // Mapeo DTO de Request a modelo de dominio
        var ticketToCreate = ticketWebMapper.toDomain(request);

        var createdTicket = createTicketPort.createTicket(ticketToCreate);

        // Mapeo del modelo del dominio a DTO de Response
        var response = ticketWebMapper.toResponse(createdTicket);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
