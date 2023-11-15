package com.alura.foro.domain.respuesta;

import java.time.LocalDateTime;

public record ListadoRespuestaDTO(Long id, String mensaje, Long idTopico, Long idAutor, LocalDateTime fechaCreacion, Boolean solucion) {
    public ListadoRespuestaDTO(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getTopico().getId(), respuesta.getAutor().getId(), respuesta.getFechaCreacion(), respuesta.getSolucion());
    }
}
