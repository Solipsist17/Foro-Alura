package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.RegistrarCursoDTO;
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
class TopicoRepositoryTest {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Debería retornar vacío cuando no hay ningún tópico")
    void findAllWithFetchEscenario1() {
        // given
        Pageable paginacion = PageRequest.of(0, 10);

        // when
        var topicos = topicoRepository.findAllWithFetch(paginacion);

        // then
        assertThat(topicos).isNotNull();
        assertThat(topicos.getTotalElements()).isEqualTo(0);
    }

    @Test
    @DisplayName("Debería retornar todos los tópicos con el autor y curso relacionados")
    void findAllWithFetchEscenario2() {
        // given
        var autor = registrarAutor("nombre", "email@gmail.com", "contraseña");
        var curso = registrarCurso("categoria", "nombre");
        registrarTopico("titulo", "mensaje", autor.getId(), curso.getId());

        Pageable paginacion = PageRequest.of(0, 10);

        // when
        var topicos = topicoRepository.findAllWithFetch(paginacion);

        // then
        assertThat(topicos).isNotNull();
        assertThat(topicos.getTotalElements()).isNotEqualTo(0); // contiene al menos un topico

        topicos.forEach(topico -> {
            assertThat(topico.getAutor()).isNotNull();
            assertThat(topico.getCurso()).isNotNull();
        });
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

    private RegistrarCursoDTO datosCurso(String nombre, String categoria) {
        return new RegistrarCursoDTO(nombre, categoria);
    }

    private RegisterUsuarioDTO datosUsuario(String nombre, String email, String contrasena) {
        return new RegisterUsuarioDTO(nombre, email, contrasena);
    }

    private RegistrarTopicoDTO datosTopico(String titulo, String mensaje, Long idAutor, Long idCurso) {
        return new RegistrarTopicoDTO(titulo, mensaje, idAutor, idCurso);
    }
}