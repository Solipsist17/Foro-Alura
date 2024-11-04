package com.alura.foro.domain.respuesta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarRespuestaDTO(
        @NotBlank
        String mensaje,
        /*@NotNull
        @JsonAlias("topico_id")
        Long idTopico,*/
        @NotNull
        @JsonAlias("autor_id")
        Long idAutor) {
}
