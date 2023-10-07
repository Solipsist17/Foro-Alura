package com.alura.foro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
    private Boolean solucion = false;

}
