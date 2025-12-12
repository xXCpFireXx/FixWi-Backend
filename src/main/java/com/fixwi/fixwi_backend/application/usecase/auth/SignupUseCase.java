package com.fixwi.fixwi_backend.application.usecase.auth;

import com.fixwi.fixwi_backend.domain.exception.AlreadyExistsException;
import com.fixwi.fixwi_backend.domain.exception.BusinessRuleException;
import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.in.auth.SignupPort;
import com.fixwi.fixwi_backend.domain.ports.out.LoadUserPort;
import com.fixwi.fixwi_backend.domain.ports.out.SaveUserPort;
import com.fixwi.fixwi_backend.domain.ports.out.security.PasswordEncoderPort;

public class SignupUseCase implements SignupPort {

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;
    private final PasswordEncoderPort passwordEncoder;

    public SignupUseCase(SaveUserPort saveUserPort, LoadUserPort loadUserPort, PasswordEncoderPort passwordEncoder) {
        this.saveUserPort = saveUserPort;
        this.loadUserPort = loadUserPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User signIn(User user) {

        if (user.getRole() == null) {
            throw new BusinessRuleException("The user's role is required for registration.");
        }

        if (loadUserPort.existsByEmail(user.getEmail())) {
            throw new AlreadyExistsException("The email is already registered.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return saveUserPort.save(user);
    }
}
