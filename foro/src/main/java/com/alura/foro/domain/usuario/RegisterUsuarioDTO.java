package com.alura.foro.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record RegisterUsuarioDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String email,
        @NotBlank
        String contrasena){
}
