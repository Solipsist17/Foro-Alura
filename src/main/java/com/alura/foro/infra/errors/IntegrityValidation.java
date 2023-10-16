package com.alura.foro.infra.errors;

public class IntegrityValidation extends RuntimeException {

    public IntegrityValidation(String message) {
        super(message);
    }

}
