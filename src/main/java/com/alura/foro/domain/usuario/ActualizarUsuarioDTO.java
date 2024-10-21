package com.alura.foro.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarUsuarioDTO(
        @NotNull
        Long id,
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String contrasena) {
}
