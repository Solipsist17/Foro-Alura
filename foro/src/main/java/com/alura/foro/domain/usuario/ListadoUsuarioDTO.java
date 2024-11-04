package com.alura.foro.domain.usuario;

public record ListadoUsuarioDTO(Long id, String nombre, String email, String contrasena) {
    public ListadoUsuarioDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getContrasena());
    }
}
