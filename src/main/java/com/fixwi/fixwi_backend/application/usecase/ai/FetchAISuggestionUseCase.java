package com.fixwi.fixwi_backend.application.usecase.ai;

import com.fixwi.fixwi_backend.domain.ports.in.ai.FetchAISuggestionPort;
import com.fixwi.fixwi_backend.domain.ports.out.AISuggestionPort;

import java.util.Optional;

public class FetchAISuggestionUseCase implements FetchAISuggestionPort {

    private final AISuggestionPort aiSuggestionPort;

    public FetchAISuggestionUseCase(AISuggestionPort aiSuggestionPort) {
        this.aiSuggestionPort = aiSuggestionPort;
    }

    @Override
    public Optional<String> fetchSuggestion(String categoryName, String description) {

        if (!"SOFTWARE".equalsIgnoreCase(categoryName)) {
            return Optional.empty();
        }

        if (description == null || description.trim().isEmpty()) {
            return Optional.empty();
        }
        return aiSuggestionPort.generateSuggestion(description);
    }
}