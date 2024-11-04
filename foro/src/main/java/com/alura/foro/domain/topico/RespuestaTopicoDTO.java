package com.alura.foro.domain.topico;

public record RespuestaTopicoDTO(Long id, String titulo, String mensaje, Long idAutor, Long idCurso) {

        public RespuestaTopicoDTO(Topico topico) {
                this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getAutor().getId(), topico.getCurso().getId());
        }
}
