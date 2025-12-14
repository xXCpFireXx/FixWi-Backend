package com.fixwi.fixwi_backend.domain.ports.in.auth;

import com.fixwi.fixwi_backend.domain.model.User;

public interface SignupPort {
    User signIn(User user);
}
