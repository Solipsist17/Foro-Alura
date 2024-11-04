package com.alura.foro.infra.errors.exceptions;

public class IntegrityValidation extends RuntimeException {

    public IntegrityValidation(String message) {
        super(message);
    }

}
