package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.model.Ticket;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.CreateTicketPort;
import com.fixwi.fixwi_backend.domain.ports.in.ticket.FindTicketPort;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request.TicketCreationRequest;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response.TicketCreationResponse;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.mapper.TicketWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
@Tag(name = "Gestión de Tickets", description = "Operaciones para crear y administrar tickets de soporte")
public class TicketController {

    private final CreateTicketPort createTicketPort;
    private final FindTicketPort findTicketPort;
    private final TicketWebMapper ticketWebMapper;

    @Operation(
            summary = "Crear un nuevo ticket",
            description = "Registra un incidente en el sistema. Requiere que el ID de usuario y el ID de categoría existan.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TicketCreationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud", content = @Content),

            // --- NUEVO: Error 401 ---
            @ApiResponse(responseCode = "401", description = "No autenticado (Token JWT no enviado, expirado o inválido)", content = @Content),

            // --- ACTUALIZADO: Error 403 (Solo permisos) ---
            @ApiResponse(responseCode = "403", description = "Prohibido (El usuario no tiene los permisos necesarios)", content = @Content),

            @ApiResponse(responseCode = "404", description = "Usuario o Categoría no encontrados", content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<TicketCreationResponse> createTicket(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Detalles del ticket a crear", required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"title\": \"Error en impresora\", \"description\": \"No conecta a la red wifi.\", \"categoryId\": 1, \"userId\": 1}"))
            )
            @Valid @RequestBody TicketCreationRequest request) {

        // Mapeo DTO de Request a modelo de dominio
        var ticketToCreate = ticketWebMapper.toDomain(request);

        var createdTicket = createTicketPort.createTicket(ticketToCreate);

        // Mapeo del modelo del dominio a DTO de Response
        var response = ticketWebMapper.toResponse(createdTicket);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // List all tickets paginated and filter (if needed) by status and category
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'TI')")
    public ResponseEntity<?> getAllTickets(Pageable pageable,
                                           @RequestParam(required = false)String status,
                                           @RequestParam(required = false)String category)
    {



        var authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(auth-> "USER".equals(auth.getAuthority()));



        Page<Ticket> page;
        if(isUser){
            String email = authentication.getName();
            page = findTicketPort.findAllForUser(pageable, status,category, email);
        }else {
            page = findTicketPort.findAll(pageable, status,category );
        }
        Page<TicketCreationResponse> response = page.map(ticketWebMapper::toResponse);
        return ResponseEntity.ok(response);
    }


    //find ticket by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        var eventResponse =  ticketWebMapper.toResponse(findTicketPort.findById(id));
        return ResponseEntity.ok(eventResponse);
    }

}
