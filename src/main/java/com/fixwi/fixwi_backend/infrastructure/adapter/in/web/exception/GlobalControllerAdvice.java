package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.exception;

import com.fixwi.fixwi_backend.domain.exception.AlreadyExistsException;
import com.fixwi.fixwi_backend.domain.exception.BusinessRuleException;
import com.fixwi.fixwi_backend.domain.exception.InvalidCredentialsException;
import com.fixwi.fixwi_backend.domain.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
@Slf4j // Habilita el logging estructurado
public class GlobalControllerAdvice {

    // --- 1. Business (Domain) Error Handling ---

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage(), "resource-not-found", request);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ProblemDetail handleAlreadyExists(AlreadyExistsException ex, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.CONFLICT, ex.getMessage(), "resource-already-exists", request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ProblemDetail handleBusinessRule(BusinessRuleException ex, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage(), "business-rule-violation", request);
    }

// --- 2. Security and Auth Handling ---

    @ExceptionHandler({InvalidCredentialsException.class, BadCredentialsException.class})
    public ProblemDetail handleInvalidCredentials(Exception ex, HttpServletRequest request) {
        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas", "authentication-error", request);
    }

    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public ProblemDetail handleAccessDenied(Exception ex, HttpServletRequest request) {
        // Log for debugging (optional)
        log.warn("Access Denied captured: {}", ex.getClass().getName());

        return buildProblemDetail(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción", "access-denied", request);
    }

// --- 3. Validation Handling (@Valid) ---

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ProblemDetail problemDetail = buildProblemDetail(HttpStatus.BAD_REQUEST, "Error en los datos enviados", "validation-error", request);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }

// --- 4. Global Fallback (Uncontrolled Errors) ---

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error [Class: {}]: ", ex.getClass().getName(), ex);
        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno inesperado", "internal-server-error", request);
    }

// --- Private Builder Method for standardizing the response ---

    private ProblemDetail buildProblemDetail(HttpStatus status, String detail, String typeSuffix, HttpServletRequest request) {
        // Generate a unique tracing ID (Trace ID)
        String traceId = UUID.randomUUID().toString();

        // Structured Log (you will see it in the console)
        log.error("Captured error [TraceID: {}] - Status: {} - Error: {}", traceId, status.value(), detail);

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);

        // Fill in standard RFC 7807 fields
        problem.setTitle(status.getReasonPhrase());
        problem.setType(URI.create("https://fixwi.com/errors/" + typeSuffix));
        problem.setInstance(URI.create(request.getRequestURI()));

        // Additional custom fields
        problem.setProperty("timestamp", LocalDateTime.now());
        problem.setProperty("traceId", traceId); // Important for support

        return problem;
    }
}