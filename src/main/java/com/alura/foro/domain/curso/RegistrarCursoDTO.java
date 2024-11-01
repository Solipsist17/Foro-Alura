package com.alura.foro.domain.curso;

import jakarta.validation.constraints.NotBlank;

public record RegistrarCursoDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String categoria) {
}
