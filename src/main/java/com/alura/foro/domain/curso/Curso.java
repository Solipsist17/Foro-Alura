package com.alura.foro.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String categoria;

    public Curso(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Curso(RegistrarCursoDTO dto) {
        this.nombre = dto.nombre();
        this.categoria = dto.categoria();
    }

    public Curso(Long id) {

    }
}
