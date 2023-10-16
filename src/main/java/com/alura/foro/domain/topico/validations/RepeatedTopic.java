package com.alura.foro.domain.topico.validations;

import com.alura.foro.domain.topico.RegistrarTopicoDTO;
import com.alura.foro.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepeatedTopic implements TopicValidator {
    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(RegistrarTopicoDTO registrarTopicoDTO) {
        System.out.println("Ejecutando validacion");
        if (topicoRepository.existsByTituloAndMensaje(registrarTopicoDTO.titulo(), registrarTopicoDTO.mensaje())) {
            throw new ValidationException("Ya existe un tópico con ese título y mensaje");
        }
    }
}
