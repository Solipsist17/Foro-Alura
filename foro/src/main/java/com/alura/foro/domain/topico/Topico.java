package com.alura.foro.domain.topico;

import com.alura.foro.domain.respuesta.Respuesta;
import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.usuario.Usuario;
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
@EqualsAndHashCode(of = "id")
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

    public Topico(String titulo, String mensaje, Usuario autor, Curso curso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.autor = autor;
        this.curso = curso;
    }

    public Topico(RegistrarTopicoDTO dto) {
        this.titulo = dto.titulo();
        this.mensaje = dto.mensaje();
        this.autor = new Usuario();
        this.curso = new Curso();
        this.autor.setId(dto.idAutor());
        this.curso.setId(dto.idCurso());
    }

    public void actualizarDatos(ActualizarTopicoDTO actualizarTopicoDTO) {
        if (actualizarTopicoDTO.titulo() != null) {
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null) {
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        if (actualizarTopicoDTO.status() != null) {
            this.status = actualizarTopicoDTO.status();
        }
    }
}
