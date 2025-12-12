package com.fixwi.fixwi_backend.domain.ports.out.security;

import com.coopcredit.credit_application_service.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);
}
