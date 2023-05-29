package com.prova.prova.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prova.prova.model.Mesa;
import com.prova.prova.model.Plato;
import com.prova.prova.model.Reserva;
import com.prova.prova.repository.MesaRepository;
import com.prova.prova.repository.PlatoRepository;
import com.prova.prova.repository.ReservaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.FetchType;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Calendar;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import java.util.*;

import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin("http://localhost:3000")

public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @GetMapping("/verreservas")
    List <Reserva> getAllReserva() { return reservaRepository.findAll(); }


    @PostMapping("/buscarMesas")
    public ResponseEntity<List<Mesa>> getAllMesas(@RequestBody Map<String, Object> requestBody) {
        int capacidad = (int) requestBody.get("capacidad");
        String fechaObjeto = requestBody.get("fecha").toString();
        String fechaSinHora = fechaObjeto.substring(0, fechaObjeto.indexOf("T"));

        LocalDate fecha = LocalDate.parse((String) fechaSinHora);
        LocalDate fechaMasUnDia = fecha.plusDays(1);
        LocalTime hora = LocalTime.parse((String) requestBody.get("hora"));

        List<Mesa> mesasDisponibles = reservaRepository.findMesasDisponibles(fechaMasUnDia, hora, capacidad);

        return ResponseEntity.ok(mesasDisponibles);
    }

    @PostMapping("/realizarReserva")
    public ResponseEntity<String> Reservar(@RequestBody Reserva reserva){
        try{
            Long mesaId = reserva.getMesa();
            Mesa mesa = mesaRepository.findById(mesaId).orElse(null);

            if (mesa != null) {
                System.out.println("FECHA: "+reserva.getFecha());
                System.out.println("HORA: "+reserva.getHora());
                System.out.println("DNI: " + reserva.getDni());
                System.out.println("NOMBRE: " + reserva.getNombre());
                System.out.println("MESA: " + mesa.getId());

                // Obtener la fecha actual
                LocalDate fechaRe = reserva.getFecha();

                // Mostrar la fecha
                System.out.println("Fecha: " + fechaRe);

                // Agregar un día a la fecha
                LocalDate fechaMasUnDia = fechaRe.plusDays(1);

                // Mostrar la fecha con un día adicional
                System.out.println("Fecha" + fechaMasUnDia);
                reserva.setFecha(fechaMasUnDia);

                LocalDate fecha = fechaMasUnDia;
                LocalTime hora = reserva.getHora();
                String dni = reserva.getDni();
                String nombre = reserva.getNombre();
                Long idMesa = reserva.getMesa();
                reservaRepository.save(reserva);

            } else {
                System.out.println("No se encontró la mesa con ID: " + mesaId);
            }
            return ResponseEntity.ok("true");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar la reserva: " + e.getMessage());
        }

    }
}

