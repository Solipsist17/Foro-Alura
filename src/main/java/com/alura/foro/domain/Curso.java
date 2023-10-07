package com.alura.foro.domain;

public class Curso {

    private Long id;
    private String nombre;
    private String categoria;

    public Curso(String nombre, String categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
    }

}
