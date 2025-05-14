package com.BackSpringBoys.Java_Backend.Modelo.Auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciales de acceso")
public class AuthRequest {

    @Schema(description = "Nombre de usuario", example = "admin")
    private String username;

    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;

    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
