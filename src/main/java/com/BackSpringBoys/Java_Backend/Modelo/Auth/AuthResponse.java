package com.BackSpringBoys.Java_Backend.Modelo.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con el token JWT de autenticación")
public class AuthResponse {

    @Schema(description = "Token JWT generado para el usuario", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }


}