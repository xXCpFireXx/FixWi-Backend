package com.fixwi.fixwi_backend.domain.ports.out.security;


import com.fixwi.fixwi_backend.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);
}
