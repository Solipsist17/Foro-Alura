package com.alura.foro.domain.topico;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarTopicoDTO(
        @NotBlank
        String titulo, // unique
        @NotBlank
        String mensaje, // unique
        @NotNull
        @JsonAlias("autor_id")
        Long idAutor,
        @NotNull
        @JsonAlias("curso_id")
        Long idCurso)
        {
}
