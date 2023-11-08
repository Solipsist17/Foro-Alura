package com.alura.foro.controllers;

import com.alura.foro.domain.respuesta.*;
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
public class RespuestaController {

    private final RespuestaRepository respuestaRepository;
    private final RespuestaService respuestaService;

    @Autowired
    public RespuestaController(RespuestaRepository respuestaRepository, RespuestaService respuestaService) {
        this.respuestaRepository = respuestaRepository;
        this.respuestaService = respuestaService;
    }

    @PostMapping
    public ResponseEntity<ResponseRespuestaDTO> registrar(@PathVariable Long idTopico, @RequestBody @Valid RegistrarRespuestaDTO registrarRespuestaDTO,
                                    UriComponentsBuilder uriComponentsBuilder) {
        ResponseRespuestaDTO responseRespuestaDTO = respuestaService.registrar(registrarRespuestaDTO, idTopico);
        //URI uri = uriComponentsBuilder.path("/respuestas/{id}").buildAndExpand(respuestaCreada.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseRespuestaDTO);    // status code 201
    }

    @GetMapping
    public ResponseEntity<Page<ListadoRespuestaDTO>> listar(@PathVariable Long idTopico,
                                                            @PageableDefault(size = 10, sort = "fechaCreacion", page = 0) Pageable paginacion){
        return ResponseEntity.ok(respuestaService.listar(idTopico, paginacion));
    }



}
