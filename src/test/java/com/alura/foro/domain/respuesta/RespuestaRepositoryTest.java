package com.alura.foro.domain.respuesta;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.RegistrarCursoDTO;
import com.alura.foro.domain.topico.RegistrarTopicoDTO;
import com.alura.foro.domain.topico.Topico;
import com.alura.foro.domain.usuario.RegisterUsuarioDTO;
import com.alura.foro.domain.usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class RespuestaRepositoryTest {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Debería retornar nulo cuando no hay ninguna respuesta relacionada a ese tópico")
    void findAllByTopicoIDEscenario1() {
        // given
        var autor = registrarAutor("nombre", "email@gmail.com", "contraseña");
        var curso = registrarCurso("categoria", "nombre");
        var topico = registrarTopico("titulo", "mensaje", autor.getId(), curso.getId());

        Pageable paginacion = PageRequest.of(0, 10);

        // when
        var respuestas = respuestaRepository.findAllByTopicoID(topico.getId(), paginacion);

        // then
        assertThat(respuestas).isNotNull(); // devuelve un objeto Page vacío

        assertThat(respuestas.getTotalElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("Debería retornar las repuestas relaciondas a ese tópico")
    void findAllByTopicoIDEscenario2() {
        // given
        var autor = registrarAutor("nombre", "email@gmail.com", "contraseña");
        var curso = registrarCurso("categoria", "nombre");
        var topico = registrarTopico("titulo", "mensaje", autor.getId(), curso.getId());
        var respuesta = registrarRespuesta("mensaje", autor.getId(), topico.getId());

        Pageable paginacion = PageRequest.of(0, 10);

        // when
        var respuestas = respuestaRepository.findAllByTopicoID(topico.getId(), paginacion);

        // then
        assertThat(respuestas).isNotNull(); // devuelve un objeto Page con las respuestas relacionadas

        assertThat(respuestas.getTotalElements()).isNotEqualTo(0); // contiene al menos 1 respuesta
    }

    private Usuario registrarAutor(String nombre, String email, String contrasena) {
        var usuario = new Usuario(datosUsuario(nombre, email, contrasena));
        em.persist(usuario);
        return usuario;
    }

    private Curso registrarCurso(String categoria, String nombre) {
        var curso = new Curso(datosCurso(nombre, categoria));
        em.persist(curso);
        return curso;
    }

    private Topico registrarTopico(String titulo, String mensaje, Long idAutor, Long idCurso) {
        var topico = new Topico(datosTopico(titulo, mensaje, idAutor, idCurso));
        em.persist(topico);
        return topico;
    }

    private Respuesta registrarRespuesta(String mensaje, Long idAutor, Long idTopico) {
        var topico = em.find(Topico.class, idTopico);
        var respuesta = new Respuesta(datosRespuesta(mensaje, idAutor));
        respuesta.setTopico(topico);

        // simular la llamada del controlador ¿?

        em.persist(respuesta);
        return respuesta;
    }

    private RegistrarCursoDTO datosCurso(String nombre, String categoria) {
        return new RegistrarCursoDTO(nombre, categoria);
    }

    private RegisterUsuarioDTO datosUsuario(String nombre, String email, String contrasena) {
        return new RegisterUsuarioDTO(nombre, email, contrasena);
    }

    private RegistrarTopicoDTO datosTopico(String titulo, String mensaje, Long idAutor, Long idCurso) {
        return new RegistrarTopicoDTO(titulo, mensaje, idAutor, idCurso);
    }

    private RegistrarRespuestaDTO datosRespuesta(String mensaje, Long idAutor) {
        return new RegistrarRespuestaDTO(mensaje, idAutor);
    }
}