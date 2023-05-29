package com.prova.prova.controller;

import com.prova.prova.model.Comentario;
import com.prova.prova.model.Mesa;
import com.prova.prova.model.Plato;
import com.prova.prova.repository.ComentarioRepository;
import com.prova.prova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class ComentarioController {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @GetMapping("/resenyas")
    List<Comentario> getAllComentarios(){
        return comentarioRepository.findAllOrderByDescId();
    }

    @PostMapping("/crearComentario")
    public ResponseEntity<String> crearComentario(@RequestBody Comentario comentario) {

        comentarioRepository.save(comentario);
        return ResponseEntity.ok("Reseña creada con exito");

    }
}
