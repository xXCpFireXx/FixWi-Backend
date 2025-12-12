package com.fixwi.fixwi_backend.domain.ports.in.auth;

public interface LoginPort {
    String login(String email, String password);
}
