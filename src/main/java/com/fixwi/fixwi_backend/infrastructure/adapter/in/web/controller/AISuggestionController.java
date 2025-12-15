package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.ports.in.ai.FetchAISuggestionPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/suggestions")
@RequiredArgsConstructor
@Tag(name = "Asistente IA", description = "Endpoints para obtener soporte automatizado mediante Inteligencia Artificial")
public class AISuggestionController {

    private final FetchAISuggestionPort fetchAISuggestionPort;

    @Operation(
            summary = "Obtener sugerencia de solución",
            description = "Analiza la categoría y descripción del problema para ofrecer una posible solución automática antes de crear un ticket.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sugerencia generada exitosamente",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Prueba reiniciando el servicio en el panel de control..."))),
            @ApiResponse(responseCode = "204", description = "No hay sugerencias disponibles para esta categoría o descripción", content = @Content),
            @ApiResponse(responseCode = "400", description = "Faltan parámetros obligatorios (category o description)", content = @Content),

            // --- NUEVO: Error 401 ---
            @ApiResponse(responseCode = "401", description = "No autenticado (Token JWT no enviado, expirado o inválido)", content = @Content),

            // --- ACTUALIZADO: Error 403 ---
            @ApiResponse(responseCode = "403", description = "Prohibido (El usuario no tiene los permisos necesarios)", content = @Content)
    })
    @GetMapping
    public ResponseEntity<String> getAISuggestion(
            @Parameter(description = "Nombre de la categoría (ej. SOFTWARE)", example = "SOFTWARE", required = true)
            @RequestParam(name = "category") String categoryName,

            @Parameter(description = "Descripción detallada del problema", example = "La aplicación se cierra al intentar exportar PDF", required = true)
            @RequestParam(name = "description") String description) {

        Optional<String> suggestion = fetchAISuggestionPort.fetchSuggestion(categoryName, description);

        if (suggestion.isPresent()) {
            return ResponseEntity.ok(suggestion.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}