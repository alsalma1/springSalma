package com.prova.prova.controller;

import com.prova.prova.model.Mesa;
import com.prova.prova.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
public class MesaController {
    @Autowired
    private MesaRepository mesaRepository;

    @PostMapping("/crearMesa")
    public ResponseEntity<String> crearMesa(@RequestBody Mesa mesa) {
        try {
            Mesa mesaExistente = mesaRepository.findByNumero(mesa.getNumero());
            if (mesaExistente != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("false");
            }
            mesaRepository.save(mesa);
            System.out.println("El valor de estadoP es: " + mesa.isEstado());
            return ResponseEntity.ok("Mesa creada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la mesa: " + e.getMessage());
        }
    }
    @GetMapping("/verMesas")
    List<Mesa> getAllMesa(){
        return mesaRepository.findAll();
    }

    @PutMapping("/actualizarestadoMesa/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable("id") Long id, @RequestBody Map<String, Boolean> estadoMap) {
        Boolean nuevoEstado = estadoMap.get("estado");

        Optional<Mesa> platoOpt = mesaRepository.findById(id);
        if (platoOpt.isPresent()) {
            Mesa mesa = platoOpt.get();
            mesa.setEstado(nuevoEstado);
            mesaRepository.save(mesa);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/verMesa/{id}")
    public ResponseEntity<?> getMesaById(@PathVariable("id") Long id) {
        Optional<Mesa> mesaOpt = mesaRepository.findById(id);
        if (mesaOpt.isPresent()) {
            Mesa mesa = mesaOpt.get();
            return ResponseEntity.ok(mesa);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/editarMesa")
    public ResponseEntity<String> editarMesa(@RequestBody Mesa mesa) {
        try {
            Optional<Mesa> mesaOptional = mesaRepository.findById(mesa.getId());
            if (mesaOptional.isPresent()) {
                Mesa mesaExistente = mesaOptional.get();
                mesaExistente.setNumero(mesa.getNumero());
                mesaExistente.setCapacidad(mesa.getCapacidad());
                mesaExistente.setComentario(mesa.getComentario());
                mesaExistente.setEstado(mesa.isEstado());

                mesaRepository.save(mesaExistente);

                System.out.println("El valor de estadoP es: " + mesaExistente.isEstado());
                return ResponseEntity.ok("Mesa editada con éxito");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al editar la mesa: " + e.getMessage());
        }
    }

}