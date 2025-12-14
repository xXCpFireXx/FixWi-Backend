package com.fixwi.fixwi_backend.infrastructure.security.service;


import com.fixwi.fixwi_backend.domain.exception.ResourceNotFoundException;
import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.out.LoadUserPort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final LoadUserPort loadUserPort;

    public UserDetailsServiceImpl(LoadUserPort loadUserPort) {
        this.loadUserPort = loadUserPort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws ResourceNotFoundException {
        User user = loadUserPort.loadUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}