package com.fixwi.fixwi_backend.domain.ports.in.ai;

import java.util.Optional;

public interface FetchAISuggestionPort {
    Optional<String> fetchSuggestion(String categoryName, String description);
}