package com.alura.foro.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTituloAndMensaje(String titulo, String mensaje);

    @Query("SELECT t FROM Topico t JOIN FETCH t.autor JOIN FETCH t.curso") // forzamos el fetch en los atributos con carga LAZY
    Page<Topico> findAllWithFetch(Pageable pageable);

}
