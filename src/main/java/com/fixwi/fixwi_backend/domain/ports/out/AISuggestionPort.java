package com.fixwi.fixwi_backend.domain.ports.out;

import java.util.Optional;

// Puerto de salida para la generacion de sugerencias con IA
// Define el contrato de comunicación con el LLM, incluyendo el manejo de errores/timeout.
public interface AISuggestionPort {

    /**
     * Genera una lista de pasos de solución para un problema
     * Debe incluir manejo de timeout (RF-07).
     * @param problemDescription Descripción del problema del ticket.
     * @return Un Optional<String> con la sugerencia formateada, o vacío si hay timeout/falla
     */
    Optional<String> generateSuggestion(String problemDescription);
}