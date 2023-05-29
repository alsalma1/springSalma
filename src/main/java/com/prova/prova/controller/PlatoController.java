package com.prova.prova.controller;

import com.prova.prova.model.Plato;
import com.prova.prova.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.MediaType;


@RestController
@CrossOrigin("http://localhost:3000")
public class PlatoController {
    @Autowired
    private PlatoRepository platoRepository;

    @PostMapping(value = "/crearplato", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> guardarPlato(@RequestParam("titulo") String titulo,
                                               @RequestParam("descripcion") String descripcion,
                                               @RequestParam("imagen") MultipartFile imagen,
                                               @RequestParam("precio") String precio,
                                               @RequestParam("estadop") boolean estadop,
                                               @RequestParam("especial") boolean especial) {
        if (imagen.isEmpty()) {
            return ResponseEntity.badRequest().body("Plato image is required");
        }
        try {
            String originalFileName = StringUtils.cleanPath(imagen.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "_" + originalFileName;
            Path uploadPath = Paths.get("../saborBadalona/public/img/"); // Path to save images
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            imagen.transferTo(filePath);

            // Save the plato to the database
            Plato plato = new Plato();
            plato.setTitulo(titulo);

            //Si existe un plato con el mismo titulo
            Plato platoExistente = platoRepository.findByTitulo(plato.getTitulo());
            if (platoExistente != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("false");
            }
            else{
                plato.setTitulo(titulo);
                plato.setDescripcion(descripcion);
                plato.setImagen(fileName);
                plato.setPrecio(Float.parseFloat(precio));
                plato.setEstadop(estadop);
                plato.setEspecial(especial);
                platoRepository.save(plato);
                return ResponseEntity.ok("Plato created successfully!");
           }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the plato");
        }
    }

    @GetMapping("/verplatos")
    List<Plato> getAllPlato(){
        return platoRepository.findAll();
    }

    @PutMapping("/actualizarestado/{id}")
    public ResponseEntity<?> actualizarEstado(@PathVariable("id") Long id, @RequestBody Map<String, Boolean> estadoMap) {
        Boolean nuevoEstado = estadoMap.get("estadop");

        Optional<Plato> platoOpt = platoRepository.findById(id);
        if (platoOpt.isPresent()) {
            Plato plato = platoOpt.get();
            plato.setEstadop(nuevoEstado);
            platoRepository.save(plato);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizarespecial/{id}")
    public ResponseEntity<?> actualizarEspecial(@PathVariable("id") Long id, @RequestBody Map<String, Boolean> estadoMap) {
        Boolean nuevoEspecial = estadoMap.get("especial");

        Optional<Plato> platoOp = platoRepository.findById(id);
        if (platoOp.isPresent()) {
            Plato plato = platoOp.get();
            plato.setEspecial(nuevoEspecial);
            platoRepository.save(plato);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/carta")
    List<Plato> obtenerPlatos() {
        Sort sort = Sort.by(Sort.Direction.ASC, "titulo");
        return platoRepository.findByEstadop(true, sort);
    }

    @GetMapping("/getPlatosById/{id}")
    public ResponseEntity<Plato> getPlatoById(@PathVariable("id") Long id) {
        Optional<Plato> platoOpt = platoRepository.findById(id);
        if (platoOpt.isPresent()) {
            Plato plato = platoOpt.get();
            return ResponseEntity.ok(plato);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> updatePlato(
            @PathVariable("id") Long id,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("imagen") MultipartFile imagen,
            @RequestParam("titulo") String titulo,
            @RequestParam("precio") Float precio,
            @RequestParam("estadop") boolean estadop,
            @RequestParam("especial") boolean especial

    ) {
        try {
            Plato plato = platoRepository.findById(id).orElse(null);
            if (plato == null) {
                return ResponseEntity.notFound().build();
            }

            plato.setDescripcion(descripcion);
            plato.setTitulo(titulo);
            plato.setPrecio(precio);
            plato.setEstadop(estadop);
            plato.setEspecial(especial);
            System.out.println(imagen);
            if (imagen != null && !imagen.isEmpty()) {
                try {
                    String originalFileName = StringUtils.cleanPath(imagen.getOriginalFilename());
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    String randomFileName = System.currentTimeMillis() + "_" + originalFileName;
                    Path uploadPath = Paths.get("../saborBadalona/public/img/");

                    if (!Files.exists(uploadPath)) {
                        System.out.println(uploadPath);
                        Files.createDirectories(uploadPath);
                    }
                    Path filePath = uploadPath.resolve(randomFileName);
                    imagen.transferTo(filePath);
                    String imageUrl = randomFileName;
                    plato.setImagen(imageUrl);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
                }
            }

            platoRepository.save(plato);

            return ResponseEntity.ok("Plato updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the plato");
        }
    }
    
    @PostMapping("/borrarFilas")
    public void deletePlato(@RequestBody Plato plato){
        platoRepository.deleteById(plato.getId());
    }

    @PostMapping("/borrarPlato")
    public void deleteAllPlatos() {
        platoRepository.deleteAll();    }
}