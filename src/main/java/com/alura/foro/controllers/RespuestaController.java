package com.alura.foro.controllers;

import com.alura.foro.domain.respuesta.*;
import com.alura.foro.domain.topico.RespuestaTopicoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/topicos/{idTopico}/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private final RespuestaRepository respuestaRepository;
    private final RespuestaService respuestaService;

    @Autowired
    public RespuestaController(RespuestaRepository respuestaRepository, RespuestaService respuestaService) {
        this.respuestaRepository = respuestaRepository;
        this.respuestaService = respuestaService;
    }

    @PostMapping
    @Operation(
            summary = "Crea una respuesta nueva correspondiente a un tópico en la base de datos",
            description = "Operación POST de la entidad respuestas",
            tags = {"respuesta", "post"}
    )
    public ResponseEntity<ResponseRespuestaDTO> registrar(@PathVariable Long idTopico, @RequestBody @Valid RegistrarRespuestaDTO registrarRespuestaDTO,
                                    UriComponentsBuilder uriComponentsBuilder) {
        ResponseRespuestaDTO responseRespuestaDTO = respuestaService.registrar(registrarRespuestaDTO, idTopico);
        //URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuestaCreada.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseRespuestaDTO);    // status code 201
    }

    @GetMapping
    @Operation(
            summary = "Lista las respuestas correspondientes a un tópico de la base de datos",
            description = "Operación GET de la entidad respuestas",
            tags = {"respuesta", "get"}
    )
    public ResponseEntity<Page<ListadoRespuestaDTO>> listar(@PathVariable Long idTopico,
                                                            @PageableDefault(size = 10, sort = "fechaCreacion", page = 0) Pageable paginacion){
        return ResponseEntity.ok(respuestaService.listar(idTopico, paginacion));
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza una respuesta correspondiente a un tópico de la base de datos",
            description = "Operación PUT de la entidad respuestas",
            tags = {"respuesta", "put"}
    )
    public ResponseEntity<ResponseRespuestaDTO> actualizar(@RequestBody @Valid ActualizarRespuestaDTO actualizarRespuestaDTO) {
        return ResponseEntity.ok(respuestaService.actualizar(actualizarRespuestaDTO)); // status code 200
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina una respuesta correspondiente a un tópico de la base de datos",
            description = "Operación DELETE de la entidad respuestas",
            tags = {"respuesta", "delete"}
    )
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        respuestaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
