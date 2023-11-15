package com.alura.foro.domain.respuesta;

import jakarta.validation.constraints.NotNull;

public record ActualizarRespuestaDTO(@NotNull Long id, String mensaje, Boolean solucion) {
}
