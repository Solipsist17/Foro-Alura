package com.alura.foro.domain;

import java.time.LocalDateTime;

public class Respuesta {

    private Long id;
    private String mensaje;
    private Topico topico;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private Usuario autor;
    private Boolean solucion = false;

}
