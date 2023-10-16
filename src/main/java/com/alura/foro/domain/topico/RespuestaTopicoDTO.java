package com.alura.foro.domain.topico;

public record RespuestaTopicoDTO(String titulo, String mensaje, Long idAutor, Long idCurso) {

        public RespuestaTopicoDTO(Topico topico) {
                this(topico.getTitulo(), topico.getMensaje(), topico.getAutor().getId(), topico.getCurso().getId());
        }
}
