package com.alura.foro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;
    @OneToMany(mappedBy = "topico")
    private List<Respuesta> respuestas = new ArrayList<>();

    public Topico(String titulo, String mensaje, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.curso = curso;
    }

}
