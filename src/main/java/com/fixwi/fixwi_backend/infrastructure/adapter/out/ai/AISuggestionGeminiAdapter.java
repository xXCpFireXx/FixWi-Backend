package com.fixwi.fixwi_backend.infrastructure.adapter.out.ai;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.fixwi.fixwi_backend.domain.ports.out.AISuggestionPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.*;

@Component
public class AISuggestionGeminiAdapter implements AISuggestionPort {

    private static final long AI_TIMEOUT_SECONDS = 15; // Timeout límite
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Value("${ai.suggestion.apiKey}")
    private String apiKey;

    private final Client geminiClient;
    private static final String MODEL_NAME = "gemini-2.5-flash";

    public AISuggestionGeminiAdapter(@Value("${ai.suggestion.apiKey}") String apiKey) {
        this.geminiClient = Client.builder().apiKey(apiKey).build();
    }

    private String getLocalFallbackSuggestion() {
        return "1. [FALLBACK] Verifique si su software está actualizado.\n2. [FALLBACK] Reinicie el entorno de desarrollo (IDE).\n3. [FALLBACK] Revise que no haya conflictos de librerías en su PATH.";
    }

    @Override
    public Optional<String> generateSuggestion(String problemDescription) {

        Callable<String> aiTask = () -> {

            String prompt = String.format(
                    "Eres un asistente de soporte de TI. Analiza el siguiente problema de software y genera una lista CONCISA y NUMERADA (de 3 a 5 pasos) de posibles pasos de solución para un desarrollador. Problema: %s",
                    problemDescription
            );

            GenerateContentResponse response = geminiClient.models.generateContent(
                    MODEL_NAME,
                    prompt,
                    null
            );
            return response.text();
        };

        Future<String> future = executor.submit(aiTask);
        try {
            // respuesta con el límite de tiempo
            String suggestion = future.get(AI_TIMEOUT_SECONDS, TimeUnit.SECONDS);
            return Optional.of(suggestion);

        } catch (TimeoutException e) {
            System.err.println("AI Suggestion service timed out. Allowing ticket submission.");
            return Optional.empty();
        } catch (Exception e) {
            System.err.println("AI Suggestion service failed: " + e.getMessage());
            return Optional.empty();
        } finally {
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
    }
}