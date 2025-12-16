package com.fixwi.fixwi_backend.domain.ports.out;

import java.util.Optional;

// Output port for generating suggestions with AI
// Defines the communication contract with the LLM, including error/timeout handling
public interface AISuggestionPort {

    //Generates a list of solution steps for a problem
    Optional<String> generateSuggestion(String problemDescription);
}