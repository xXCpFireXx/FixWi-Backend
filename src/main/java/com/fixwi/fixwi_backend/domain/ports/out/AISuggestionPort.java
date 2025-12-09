package com.fixwi.fixwi_backend.domain.ports.out;

import java.util.Optional;

// Puerto de salida para la generacion de sugerencias con IA
// Define el contrato de comunicación con el LLM, incluyendo el manejo de errores/timeout
public interface AISuggestionPort {

    //Genera una lista de pasos de solución para un problema
    Optional<String> generateSuggestion(String problemDescription);
}