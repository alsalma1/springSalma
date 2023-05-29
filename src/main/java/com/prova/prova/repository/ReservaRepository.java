package com.prova.prova.repository;

import com.prova.prova.model.Mesa;
import com.prova.prova.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    Reserva findByDni(String dni);

    List<Reserva> findByFecha(LocalDate fecha);



    @Query("SELECT m FROM Mesa m " +
            "WHERE m.capacidad = :capacidad " +
            "AND m.estado = true " +
            "AND ( " +
            "    m.id NOT IN (SELECT r.mesa FROM Reserva r) " +
            "    OR ( " +
            "        m.id IN (SELECT r.mesa FROM Reserva r WHERE r.fecha = :fecha AND r.hora != :hora) " +
            "    ) " +
            ")")
    List<Mesa> findMesasDisponibles(@Param("fecha") LocalDate fecha, @Param("hora") LocalTime hora, @Param("capacidad") int capacidad);

}