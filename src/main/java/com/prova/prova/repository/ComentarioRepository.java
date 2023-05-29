package com.prova.prova.repository;

import com.prova.prova.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Comentario findByResenya(String resenya);

    @Query("SELECT c FROM Comentario c ORDER BY c.id DESC")
    List<Comentario> findAllOrderByDescId();
}