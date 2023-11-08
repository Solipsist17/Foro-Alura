package com.alura.foro.domain.respuesta;

import java.time.LocalDateTime;

public record ResponseRespuestaDTO(Long id, String mensaje, Long idTopico, Long idAutor, LocalDateTime fechaCreacion) {

    public ResponseRespuestaDTO(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(), respuesta.getAutor().getId(), respuesta.getFechaCreacion());
    }
}
