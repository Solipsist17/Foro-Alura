package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.CursoRepository;
import com.alura.foro.domain.topico.validations.TopicValidator;
import com.alura.foro.domain.usuario.UsuarioRepository;
import com.alura.foro.infra.errors.exceptions.IntegrityValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final List<TopicValidator> topicValidators;

    @Autowired
    public TopicoService(TopicoRepository topicoRepository, CursoRepository cursoRepository,
                         UsuarioRepository usuarioRepository, List<TopicValidator> topicValidators) {
        this.topicoRepository = topicoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.topicValidators = topicValidators;
    }

    // no se pueden registrar topicos con autor o curso inexistentes, ni con titulo y mensaje repetido
    public Topico registrar(RegistrarTopicoDTO datos) {
        if (!usuarioRepository.existsById(datos.idAutor())) {
            throw new IntegrityValidation("Este id para el autor no fue encontrado");
        }
        if (!cursoRepository.existsById(datos.idCurso())) {
            throw new IntegrityValidation("Este id para el curso no fue encontrado");
        }

        topicValidators.forEach(v -> v.validar(datos));

        var usuario = usuarioRepository.getReferenceById(datos.idCurso());
        var curso = cursoRepository.getReferenceById(datos.idCurso());
        var topico = new Topico(datos.titulo(), datos.mensaje(), usuario, curso);

        topicoRepository.save(topico);

        return topico;
    }

    public Page<ListadoTopicoDTO> listar(Pageable paginacion) {
        return topicoRepository.findAllWithFetch(paginacion).map(topico -> new ListadoTopicoDTO(topico));
    }

    public ListadoTopicoDTO obtener(Long id) {
        return new ListadoTopicoDTO(topicoRepository.getReferenceById(id));
    }

    // no se pueden actualizar topicos con titulo y mensaje repetido
    public RespuestaTopicoDTO actualizar(ActualizarTopicoDTO datos) {
        var topico = topicoRepository.getReferenceById(datos.id());

        // si se cambia el título o mensaje entonces validaremos
        if (!datos.titulo().equals(topico.getTitulo()) || !datos.mensaje().equals(topico.getMensaje())) {
            topicValidators.forEach(v -> v.validar(datos));
        }
        topico.actualizarDatos(datos);

        return new RespuestaTopicoDTO(topico);
    }

    public void eliminar(Long id) {
        if (!topicoRepository.existsById(id)) {
            throw new EntityNotFoundException("Este id para el topico no fue encontrado");
        }

        var topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
    }

}
