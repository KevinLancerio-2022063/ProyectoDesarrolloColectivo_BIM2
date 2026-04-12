package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objetivos")
public class ObjetivosController {
    public final ObjetivosService objetivosService;

    public ObjetivosController(ObjetivosService objetivosService) {
        this.objetivosService = objetivosService;
    }

    @GetMapping
    public List<Objetivos> getAllObjetivos(){return objetivosService.getAllObjetivos();}

    @PostMapping
    public ResponseEntity<Object> createObjetivos(@Valid @RequestBody Objetivos objetivos){
        try {
            Objetivos createObjetivo = objetivosService.saveObjetivos(objetivos);
            return new ResponseEntity<>(createObjetivo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateObjetivos(@PathVariable Integer id,@Valid @RequestBody Objetivos objetivos){
        try {
            Objetivos updateObjetivo = objetivosService.updateObjetivos(id,objetivos);
            return new ResponseEntity<>(updateObjetivo,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteObjetivos(@PathVariable Integer id){
        try {
            objetivosService.deleteObjetivos(id);
            return ResponseEntity.ok("objetivo eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
