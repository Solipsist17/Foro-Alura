package com.alura.foro.controllers;

import com.alura.foro.domain.topico.*;
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
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final TopicoService topicoService;

    @Autowired
    public TopicoController(TopicoService topicoService) {
        this.topicoService = topicoService;
    }

    @PostMapping
    @Operation(
        summary = "Crea un tópico nuevo en la base de datos",
        description = "Operación POST de la entidad topicos",
        tags = {"topico", "post"}
    )
    public ResponseEntity<RespuestaTopicoDTO> registrar(@RequestBody @Valid RegistrarTopicoDTO registrarTopicoDTO,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        RespuestaTopicoDTO respuestaTopicoDTO = topicoService.registrar(registrarTopicoDTO);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(respuestaTopicoDTO.id()).toUri();
        return ResponseEntity.created(uri).body(respuestaTopicoDTO); // status code 201 created
    }

    @GetMapping
    @Operation(
            summary = "Lista los tópicos de la base de datos",
            description = "Operación GET de la entidad tópicos",
            tags = {"topico", "get"}
    )
    public ResponseEntity<Page<ListadoTopicoDTO>> listar(@PageableDefault(size = 5, sort = "id", page = 0) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listar(paginacion)); // status code 200
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detalla un tópico de la base de datos mediante el id",
            description = "Operación GET de la entidad tópicos",
            tags = {"topico", "get"}
    )
    public ResponseEntity<ListadoTopicoDTO> detallar(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.obtener(id)); // status code 200
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza un tópico de la base de datos",
            description = "Operación PUT de la entidad tópicos",
            tags = {"topico", "put"}
    )
    public ResponseEntity<RespuestaTopicoDTO> actualizar(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO) {
        return ResponseEntity.ok(topicoService.actualizar(actualizarTopicoDTO)); // status code 200
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina un tópico de la base de datos",
            description = "Operación DELETE de la entidad tópicos",
            tags = {"topico", "delete"}
    )
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.noContent().build(); // status code 204
    }

}
