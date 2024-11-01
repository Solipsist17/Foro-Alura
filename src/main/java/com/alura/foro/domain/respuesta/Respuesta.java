package com.alura.foro.domain.respuesta;

import com.alura.foro.domain.topico.Topico;
import com.alura.foro.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario autor;
    private Boolean solucion = false;

    public Respuesta(String mensaje, Topico topico, Usuario autor) {
        this.mensaje = mensaje;
        this.topico = topico;
        this.autor = autor;
    }

    public Respuesta(RegistrarRespuestaDTO dto) {
        this.mensaje = dto.mensaje();
        this.autor = new Usuario();
        this.autor.setId(dto.idAutor());
    }

    public void actualizarDatos(ActualizarRespuestaDTO datos) {
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.solucion() != null) {
            this.solucion = datos.solucion();
        }
    }
}
