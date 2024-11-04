package com.alura.foro.domain.usuario;

public record RespuestaUsuarioDTO(Long id, String nombre, String email, String contrasena) {
    public RespuestaUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena());
    }
}
