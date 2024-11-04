package com.alura.foro.controllers;

import com.alura.foro.domain.usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(
            summary = "Lista los usuarios de la base de datos",
            description = "Operación GET de la entidad usuarios",
            tags = {"usuario", "get"}
    )
    public ResponseEntity<Page<ListadoUsuarioDTO>> consultar(@PageableDefault(size = 5, sort = "id", page=0) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(usuario -> new ListadoUsuarioDTO(usuario)));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Detalla un usuario de la base de datos mediante el id",
            description = "Operación GET de la entidad usuarios",
            tags = {"usuario", "get"}
    )
    public ResponseEntity<ListadoUsuarioDTO> consultarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(new ListadoUsuarioDTO(usuarioRepository.getReferenceById(id)));
    }

    @PostMapping
    @Operation(
            summary = "Crea un usuario nuevo en la base de datos",
            description = "Operación POST de la entidad usuarios",
            tags = {"usuario", "post"}
    )
    public ResponseEntity<RespuestaUsuarioDTO> registrar(@RequestBody RegisterUsuarioDTO registerUsuarioDTO,
                                                                UriComponentsBuilder uriComponentsBuilder) {
        String rawPasssword = registerUsuarioDTO.contrasena();
        String encodedPassword = encoderPassword(rawPasssword);

        System.out.println("Contraseña: " + rawPasssword);
        System.out.println("Contraseña hasheada: " + encodedPassword);

        Usuario usuario = new Usuario(null, registerUsuarioDTO.nombre(), registerUsuarioDTO.email(), encodedPassword);
        usuario = usuarioRepository.save(usuario);

        URI uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new RespuestaUsuarioDTO(usuario));
    }

    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza los usuarios de la base de datos",
            description = "Operación PUT de la entidad usuarios",
            tags = {"usuario", "put"}
    )
    public ResponseEntity<RespuestaUsuarioDTO> actualizar(@RequestBody ActualizarUsuarioDTO actualizarUsuarioDTO) {
        Usuario usuarioModificacion = usuarioRepository.getReferenceById(actualizarUsuarioDTO.id());

        String encodedPassword = encoderPassword(actualizarUsuarioDTO.contrasena());

        usuarioModificacion.actualizar(actualizarUsuarioDTO);
        usuarioModificacion.setContrasena(encodedPassword);

        return ResponseEntity.ok(new RespuestaUsuarioDTO(usuarioModificacion));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(
            summary = "Elimina un usuario de la base de datos",
            description = "Operación DELETE de la entidad usuarios",
            tags = {"usuario", "delete"}
    )
    public ResponseEntity<RespuestaUsuarioDTO> eliminar(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.getReferenceById(id);
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }

    public String encoderPassword(String rawPasssword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(rawPasssword);
    }

}
