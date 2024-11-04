package com.alura.foro.domain.topico.validations;

import com.alura.foro.domain.topico.ActualizarTopicoDTO;
import com.alura.foro.domain.topico.RegistrarTopicoDTO;
import com.alura.foro.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepeatedTopic<T> implements TopicValidator<T> {
    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validar(T dto) {
        if (dto instanceof RegistrarTopicoDTO registrarTopicoDTO) {
            if (topicoRepository.existsByTituloAndMensaje(registrarTopicoDTO.titulo(), registrarTopicoDTO.mensaje())) {
                throw new ValidationException("Ya existe un tópico con ese título y mensaje");
            }
        } else if (dto instanceof ActualizarTopicoDTO actualizarTopicoDTO) {
            if (topicoRepository.existsByTituloAndMensaje(actualizarTopicoDTO.titulo(), actualizarTopicoDTO.mensaje())) {
                throw new ValidationException("Ya existe un tópico con ese título y mensaje");
            }
        }

    }
}
