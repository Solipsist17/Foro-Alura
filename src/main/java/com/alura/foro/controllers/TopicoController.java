package com.alura.foro.controllers;

import com.alura.foro.domain.topico.*;
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
public class TopicoController {

    private final TopicoService topicoService;
    private final TopicoRepository topicoRepository;

    @Autowired
    public TopicoController(TopicoService topicoService, TopicoRepository topicoRepository) {
        this.topicoService = topicoService;
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    public ResponseEntity<RespuestaTopicoDTO> registrar(@RequestBody @Valid RegistrarTopicoDTO registrarTopicoDTO,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Topico topicoCreado = topicoService.registrar(registrarTopicoDTO);
        RespuestaTopicoDTO respuestaTopicoDTO = new RespuestaTopicoDTO(topicoCreado);
        URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topicoCreado.getId()).toUri();
        return ResponseEntity.created(uri).body(respuestaTopicoDTO); // status code 201 created
    }

    @GetMapping
    public ResponseEntity<Page<ListadoTopicoDTO>> listar(@PageableDefault(size = 5, sort = "id", page = 0) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listar(paginacion)); // status code 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListadoTopicoDTO> detallar(@PathVariable Long id) {
        return ResponseEntity.ok(topicoService.obtener(id)); // status code 200
    }

    @PutMapping
    @Transactional
    public ResponseEntity<RespuestaTopicoDTO> actualizar(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO) {
        return ResponseEntity.ok(topicoService.actualizar(actualizarTopicoDTO)); // status code 200
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> eliminar(@PathVariable Long id) {
        topicoService.eliminar(id);
        return ResponseEntity.ok().build(); // status code 204
    }

}
