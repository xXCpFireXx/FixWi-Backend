package com.fixwi.fixwi_backend.infrastructure.adapter.out.ai;

import com.fixwi.fixwi_backend.domain.ports.out.AISuggestionPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.*;

@Component
public class AISuggestionGeminiAdapter implements AISuggestionPort {

    private static final long AI_TIMEOUT_SECONDS = 3; // Timeout límite
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // Aquí inyectarías la configuración de la clave API, si usaras una librería cliente real
    @Value("${ai.suggestion.apiKey:default_key}")
    private String apiKey;

    @Override
    public Optional<String> generateSuggestion(String problemDescription) {

        // 1. Tarea que simula la llamada a la API del LLM
        Callable<String> aiTask = () -> {
            // ** Código real de llamada a la API de LLM iría aquí **

            // Simulación de respuesta exitosa y formateada como lista numerada (RF-07)
            Thread.sleep(1500); // Simula 1.5 segundos de latencia
            return String.format(
                    "1. Reinicie la aplicación: Cierre y vuelva a abrir el programa.\\n2. Limpie el caché: Borre los archivos temporales de la aplicación.\\n3. Reinstale: Desinstale y vuelva a instalar el software afectado."
            );
        };

        // 2. Ejecutar la tarea con Timeout (RF-07)
        Future<String> future = executor.submit(aiTask);
        try {
            String suggestion = future.get(AI_TIMEOUT_SECONDS, TimeUnit.SECONDS);

            // La sugerencia debe ser concisa y formateada (se usa \n para simular el formato de lista)
            return Optional.of(suggestion.replace("\\n", "\n"));

        } catch (TimeoutException e) {
            // Maneja el Timeout: permite la continuación sin sugerencia (RF-07)
            System.err.println("AI Suggestion service timed out.");
            return Optional.empty();
        } catch (Exception e) {
            // Maneja Fallos: permite la continuación sin sugerencia (RF-07)
            System.err.println("AI Suggestion service failed: " + e.getMessage());
            return Optional.empty();
        } finally {
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
    }
}