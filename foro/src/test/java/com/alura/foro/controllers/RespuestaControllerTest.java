package com.alura.foro.controllers;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.curso.RegistrarCursoDTO;
import com.alura.foro.domain.respuesta.RegistrarRespuestaDTO;
import com.alura.foro.domain.respuesta.ResponseRespuestaDTO;
import com.alura.foro.domain.respuesta.RespuestaService;
import com.alura.foro.domain.topico.RegistrarTopicoDTO;
import com.alura.foro.domain.topico.Topico;
import com.alura.foro.domain.usuario.RegisterUsuarioDTO;
import com.alura.foro.domain.usuario.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // realizar pruebas de integración de componentes internos, múltiples capas (Repository, Service, Controller)
@AutoConfigureMockMvc // configura el objeto MockMvc que simula el entorno de un servidor
@AutoConfigureJsonTesters // facilita el manejo de objetos JSON en entorno de prueba
@ActiveProfiles("test")
class RespuestaControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private EntityManager em;

    @Autowired
    private JacksonTester<RegistrarRespuestaDTO> registrarRespuestaDTOJacksonTester;
    @Autowired
    private JacksonTester<ResponseRespuestaDTO> responseRespuestaDTOJacksonTester;

    @MockBean
    private RespuestaService respuestaService;

    @Test
    @DisplayName("Debería retornar estado HTTP 400 (bad request) cuando los datos ingresados sean inválidos")
    @WithMockUser
    @Transactional // usarlo en pruebas de integración interna para asegurar las operaciones de persistencia
    //@Rollback
    void registrarEscenario1() throws Exception {
        // given
        var topico = registrarTopico("titulo", "mensaje");
                            // when
        var response = mvc.perform(MockMvcRequestBuilders.post("/topicos/{idTopico}/respuestas", topico.getId()))
                        .andReturn()
                        .getResponse();

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Debería retornar estado HTTP 201 (created) cuando los datos ingresados sean válidos")
    @WithMockUser
    @Transactional
    void registrarEscenario2() throws Exception {
        // given
        var topico = registrarTopico("titulo", "mensaje");
        var autor = registrarAutor("nombre", "email@gmail.com", "contraseña");
        var fecha = LocalDateTime.now();
        var datos = new ResponseRespuestaDTO(null, "mensaje", topico.getId(), autor.getId(), fecha, Boolean.FALSE);

        // when
        // definimos como responderá el service
        Mockito.when(respuestaService.registrar(ArgumentMatchers.any(), ArgumentMatchers.anyLong())).thenReturn(datos);

        var response = mvc.perform(MockMvcRequestBuilders.post("/topicos/{idTopico}/respuestas", topico.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(registrarRespuestaDTOJacksonTester.write(new RegistrarRespuestaDTO("mensaje", autor.getId())).getJson())
        ).andReturn().getResponse();

        // then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = responseRespuestaDTOJacksonTester.write(datos).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado); // ¿? json retornado = json esperado
    }

    private Topico registrarTopico(String titulo, String mensaje) {
        var autor = new Usuario(datosUsuario("nombre", "email@gmail.com", "contrasena"));
        em.persist(autor);

        var curso = new Curso(datosCurso("nombre", "categoria"));
        em.persist(curso);

        var topico = new Topico(datosTopico(titulo, mensaje, autor.getId(), curso.getId()));
        em.persist(topico);

        return topico;
    }

    private Usuario registrarAutor(String nombre, String email, String contrasena) {
        var usuario = new Usuario(datosUsuario(nombre, email, contrasena));
        em.persist(usuario);
        return usuario;
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