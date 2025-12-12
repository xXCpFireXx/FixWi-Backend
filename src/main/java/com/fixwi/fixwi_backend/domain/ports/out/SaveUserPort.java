package com.fixwi.fixwi_backend.domain.ports.out;

import com.fixwi.fixwi_backend.domain.model.User;

public interface SaveUserPort {
    User save(User user);

}
