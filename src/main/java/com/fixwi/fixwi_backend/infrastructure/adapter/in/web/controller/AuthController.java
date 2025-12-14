package com.fixwi.fixwi_backend.infrastructure.adapter.in.web.controller;

import com.fixwi.fixwi_backend.domain.model.User;
import com.fixwi.fixwi_backend.domain.ports.in.auth.LoginPort;
import com.fixwi.fixwi_backend.domain.ports.in.auth.SignupPort;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request.LoginRequest;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.request.RegisterRequest;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response.AuthResponse;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.dto.response.UserResponse;
import com.fixwi.fixwi_backend.infrastructure.adapter.in.web.mapper.UserWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para el registro e inicio de sesión de usuarios")
public class AuthController {

    private final LoginPort loginPort;
    private final SignupPort signupPort;
    private final UserWebMapper userWebMapper;

    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario y retorna un token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "400", description = "Formato de credenciales inválido", content = @Content),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas (Email o password erróneos)", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenciales del usuario", required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"email\": \"admin@fixwi.com\", \"password\": \"admin123\"}"))
            )
            @Valid @RequestBody LoginRequest request) {

        String token = loginPort.login(request.email(), request.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(summary = "Registrar nuevo usuario", description = "Crea una cuenta nueva para usuarios o administradores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos de registro inválidos o faltantes", content = @Content),
            @ApiResponse(responseCode = "409", description = "El email ya está registrado", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos para el registro", required = true,
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"fullName\": \"Maria Lopez\", \"email\": \"maria@fixwi.com\", \"password\": \"securePass123\", \"role\": \"USER\"}"))
            )
            @Valid @RequestBody RegisterRequest request) {

        User userToCreate = userWebMapper.toDomain(request);
        User createdUser = signupPort.signIn(userToCreate);
        UserResponse response = userWebMapper.toResponse(createdUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}