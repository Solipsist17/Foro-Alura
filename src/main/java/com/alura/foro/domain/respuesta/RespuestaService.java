package com.alura.foro.domain.respuesta;

import com.alura.foro.domain.topico.TopicoRepository;
import com.alura.foro.domain.usuario.UsuarioRepository;
import com.alura.foro.infra.errors.exceptions.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RespuestaService(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository, UsuarioRepository usuarioRepository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public ResponseRespuestaDTO registrar(RegistrarRespuestaDTO datos, Long idTopico) {
        if (!topicoRepository.existsById(idTopico)) {
            throw new IntegrityValidation("Este tópico no fue encontrado");
        }
        if (!usuarioRepository.existsById(datos.idAutor())) {
            throw new IntegrityValidation("Este id para el autor no fue encontrado");
        }

        var topico = topicoRepository.getReferenceById(idTopico);
        var autor = usuarioRepository.getReferenceById(datos.idAutor());
        var respuesta = new Respuesta(datos.mensaje(), topico, autor);

        return new ResponseRespuestaDTO(respuestaRepository.save(respuesta));
    }

    public Page<ListadoRespuestaDTO> listar(Long idTopico, Pageable paginacion) {
        return respuestaRepository.findAllByTopicoID(idTopico, paginacion).map(respuesta -> new ListadoRespuestaDTO(respuesta));
    }

}
