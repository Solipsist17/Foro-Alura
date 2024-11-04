package com.alura.foro.domain.topico;

import java.time.LocalDateTime;

public record ListadoTopicoDTO(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, StatusTopico status, String autor, String curso) {
    public ListadoTopicoDTO(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre()
        );

    }

}
