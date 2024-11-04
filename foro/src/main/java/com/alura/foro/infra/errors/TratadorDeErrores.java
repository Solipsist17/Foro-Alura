package com.alura.foro.infra.errors;

import com.alura.foro.infra.errors.exceptions.IntegrityValidation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores =e.getFieldErrors().stream().map(error -> new ErrorValidationDTO(error)).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler({IntegrityValidation.class, ValidationException.class})
    public ResponseEntity tratarValidacionesDeNegocio(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record ErrorValidationDTO(String campo, String error) {
        public ErrorValidationDTO(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

}
