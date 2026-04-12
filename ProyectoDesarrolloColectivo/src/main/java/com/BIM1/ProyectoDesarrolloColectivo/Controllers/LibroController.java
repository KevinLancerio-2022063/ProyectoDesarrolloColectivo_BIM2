package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import com.BIM1.ProyectoDesarrolloColectivo.Service.LibroService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libro")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }
    @GetMapping
    public List<Libro> getAListLibro(){
        return libroService.getAListLibro();
    }

    @PostMapping
    public ResponseEntity<Object> saveLibro(@Valid @RequestBody Libro libro){
            Libro libro1 = libroService.saveLibro(libro);
            return new ResponseEntity<>(libro1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateLibro(@PathVariable Integer id, @Valid @RequestBody Libro libro){
            Libro libro1 = libroService.updateLibro(id, libro);
            return new ResponseEntity<>(libro1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLibro(@PathVariable Integer id){
            libroService.deleteLibro(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getLibroById(@PathVariable Integer id){
            Libro libro = libroService.getLibroById(id);
            return ResponseEntity.ok(libro);

    }
}
