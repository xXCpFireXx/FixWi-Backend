package com.fixwi.fixwi_backend.application.usecase.auth;

import com.fixwi.fixwi_backend.domain.exception.InvalidCredentialsException;
import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.in.auth.LoginPort;
import com.fixwi.fixwi_backend.domain.ports.out.LoadUserPort;
import com.fixwi.fixwi_backend.domain.ports.out.security.PasswordEncoderPort;
import com.fixwi.fixwi_backend.domain.ports.out.security.TokenProviderPort;

public class LoginUseCase implements LoginPort {

    private final LoadUserPort loadUserPort;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProviderPort tokenProvider;

    public LoginUseCase(PasswordEncoderPort passwordEncoder, TokenProviderPort tokenProvider, LoadUserPort loadUserPort) {
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.loadUserPort = loadUserPort;
    }

    @Override
    public String login(String email, String password) {
        User userLogin = loadUserPort.loadUserByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, userLogin.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return tokenProvider.generateToken(userLogin);
    }
}
