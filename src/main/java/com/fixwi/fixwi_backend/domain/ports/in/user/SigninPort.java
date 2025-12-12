package com.fixwi.fixwi_backend.domain.ports.in.user;

import com.fixwi.fixwi_backend.domain.model.User;

public interface SigninPort {
    User signIn(User user);
}
