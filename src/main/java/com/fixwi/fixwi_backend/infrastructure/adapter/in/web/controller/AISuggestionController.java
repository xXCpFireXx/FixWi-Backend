package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.ports.in.ai.FetchAISuggestionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/suggestions") // Nuevo endpoint específico para la IA
@RequiredArgsConstructor
public class AISuggestionController {

    private final FetchAISuggestionPort fetchAISuggestionPort;

    /**
     * Endpoint para obtener sugerencias de la IA.
     * @return 200 OK con la sugerencia (String) o 204 No Content si no aplica o falla (timeout).
     */
    @GetMapping
    public ResponseEntity<String> getAISuggestion(
            @RequestParam(name = "category") String categoryName,
            @RequestParam(name = "description") String description) {

        // 1. Invocar el Caso de Uso (Use Case)
        Optional<String> suggestion = fetchAISuggestionPort.fetchSuggestion(categoryName, description);

        // 2. Manejar la respuesta
        if (suggestion.isPresent()) {
            // Devuelve la sugerencia (HTTP 200 OK)
            return ResponseEntity.ok(suggestion.get());
        } else {
            // 204 No Content: Indica que la sugerencia no existe (por timeout o categoría incorrecta)
            return ResponseEntity.noContent().build();
        }
    }
}