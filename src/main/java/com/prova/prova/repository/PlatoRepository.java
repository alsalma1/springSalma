package com.prova.prova.repository;

import com.prova.prova.model.Plato;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Long> {
    Plato findByTitulo(String titulo);

    void deleteById(Long id);

    List<Plato> findByEstadop(boolean estadop, Sort sort);
    @Modifying
    @Query("DELETE FROM Plato p WHERE p.imagen LIKE '.png'")
    void deleteByImagen();
    void deleteByImagen(String imagen);

    void deleteByImagenEndingWith(String imageExtension);

}
