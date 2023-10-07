package com.alura.foro.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Topico {

    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;
    private Usuario autor;
    private Curso curso;
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(String titulo, String mensaje, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.curso = curso;
    }

}
