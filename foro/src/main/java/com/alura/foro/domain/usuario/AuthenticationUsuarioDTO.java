package com.alura.foro.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationUsuarioDTO(@NotBlank String nombre, @NotBlank String contrasena) {
}
