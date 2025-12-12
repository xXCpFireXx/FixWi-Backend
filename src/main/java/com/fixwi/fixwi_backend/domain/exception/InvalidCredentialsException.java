package com.fixwi.fixwi_backend.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Invalid credentials from ");
    }
}
