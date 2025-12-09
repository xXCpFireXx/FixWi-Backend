package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.ports.in.ai.FetchAISuggestionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/suggestions")
@RequiredArgsConstructor
public class AISuggestionController {

    private final FetchAISuggestionPort fetchAISuggestionPort;

    //Endpoint para obtener sugerencias de la IA
    @GetMapping
    public ResponseEntity<String> getAISuggestion(
            @RequestParam(name = "category") String categoryName,
            @RequestParam(name = "description") String description) {

        Optional<String> suggestion = fetchAISuggestionPort.fetchSuggestion(categoryName, description);

        if (suggestion.isPresent()) {
            return ResponseEntity.ok(suggestion.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}