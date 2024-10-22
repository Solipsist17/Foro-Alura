package com.alura.foro.controllers;

import com.alura.foro.domain.usuario.AuthenticationUsuarioDTO;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.infra.security.JwtTokenDTO;
import com.alura.foro.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Maneja las solicitudes de autenticación de usuarios
 */

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService  tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(
            summary = "Autenticar al usuario",
            description = "Operación POST al endpoint /login",
            tags = {"autenticacion", "post"}
    )
    public ResponseEntity autenticarUsuario(@RequestBody @Valid AuthenticationUsuarioDTO authenticationUsuarioDTO) {
        // creamos un token
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            authenticationUsuarioDTO.nombre(),
            authenticationUsuarioDTO.contrasena()
        );

        var usuarioAutenticado = authenticationManager.authenticate(authToken);

        // generamos el jwt
        var jwtToken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

        return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
    }

}
