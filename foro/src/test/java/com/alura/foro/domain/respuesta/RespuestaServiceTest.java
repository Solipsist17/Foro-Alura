package com.alura.foro.domain.respuesta;

import com.alura.foro.domain.topico.Topico;
import com.alura.foro.domain.topico.TopicoRepository;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.domain.usuario.UsuarioRepository;
import com.alura.foro.infra.errors.exceptions.IntegrityValidation;
import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RespuestaServiceTest {

    @Mock
    private TopicoRepository topicoRepository;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private RespuestaRepository respuestaRepository;
    @InjectMocks // le inyecta los mocks simulados
    private RespuestaService respuestaService;


    @Test
    @DisplayName("Debería retornar una ResponseRespuestaDTO cuando los datos ingresados son válidos")
    void registrarEscenario1() {
        // given - precondition or setup
        Long idTopico = 1L;
        RegistrarRespuestaDTO datos = new RegistrarRespuestaDTO("mensaje", 1L);
        Topico topico = new Topico();
        Usuario autor = new Usuario();
        Respuesta respuesta = new Respuesta("mensaje", topico, autor);

        Mockito.when(topicoRepository.existsById(idTopico)).thenReturn(true);
        Mockito.when(usuarioRepository.existsById(datos.idAutor())).thenReturn(true);

        Mockito.when(topicoRepository.getReferenceById(idTopico)).thenReturn(topico);
        Mockito.when(usuarioRepository.getReferenceById(datos.idAutor())).thenReturn(autor);

        Mockito.when(respuestaRepository.save(ArgumentMatchers.any(Respuesta.class))).thenReturn(respuesta);

        // when - action o behaviour that we are going to test
        ResponseRespuestaDTO result = respuestaService.registrar(datos, idTopico);

        // then - verify the output
        assertNotNull(result);
        verify(topicoRepository, times(1)).existsById(idTopico);
        verify(usuarioRepository, times(1)).existsById(datos.idAutor());
        verify(topicoRepository, times(1)).getReferenceById(idTopico);
        verify(usuarioRepository, times(1)).getReferenceById(datos.idAutor());
        verify(respuestaRepository, times(1)).save(ArgumentMatchers.any(Respuesta.class));
    }

    @Test
    @DisplayName("Datos válidos y tópico no encontrado")
    void registrarEscenario2() {
        // given
        Long idTopico = 1L;
        RegistrarRespuestaDTO datos = new RegistrarRespuestaDTO("mensaje", 1L);

        Mockito.when(topicoRepository.existsById(idTopico)).thenReturn(false);

        // when & then
        assertThrows(IntegrityValidation.class, () -> {
           respuestaService.registrar(datos, idTopico);
        });

        // que topicoRespository.existsById() se haya llamado al menos 1 vez con el argumento idTopico
        verify(topicoRepository, times(1)).existsById(idTopico);
        // que usuarioRepository.existsById() no se haya llamado con cualquier argumento
        verify(usuarioRepository, never()).existsById(anyLong());
    }

    @Test
    @DisplayName("Datos válidos y usuario no encontrado")
    public void registrarEscenario3() {
        // given
        Long idTopico = 1L;
        RegistrarRespuestaDTO datos = new RegistrarRespuestaDTO("mensaje", 1L);

        when(topicoRepository.existsById(idTopico)).thenReturn(true);
        when(usuarioRepository.existsById(datos.idAutor())).thenReturn(false);

        // when & then
        assertThrows(IntegrityValidation.class, () -> {
            respuestaService.registrar(datos, idTopico);
        });

        verify(topicoRepository, times(1)).existsById(idTopico);
        verify(usuarioRepository, times(1)).existsById(datos.idAutor());
    }

}