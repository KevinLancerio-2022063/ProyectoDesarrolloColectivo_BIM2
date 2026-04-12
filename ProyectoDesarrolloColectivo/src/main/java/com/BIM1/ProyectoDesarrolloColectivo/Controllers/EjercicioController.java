package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EjercicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {
    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public List<Ejercicio> getAlistEjercicio(){
        return ejercicioService.getAListEjercicio();
    }

    @PostMapping
    public ResponseEntity<Object> createEjercicio(@Valid @RequestBody Ejercicio ejercicio){
            Ejercicio ejercicio1 = ejercicioService.saveEjercicio(ejercicio);
            return new ResponseEntity<>(ejercicio1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEjercicio(@PathVariable Integer id, @Valid @RequestBody Ejercicio ejercicio) {
            Ejercicio ejercicio1 = ejercicioService.updateEjercicio(id, ejercicio);
            return new ResponseEntity<>(ejercicio1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEjercicio(@PathVariable Integer id){
            ejercicioService.deleteEjercicio(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEjercicioById(@PathVariable Integer id){
            Ejercicio ejercicio = ejercicioService.getEjercicioById(id);
             return ResponseEntity.ok(ejercicio);
    }
}
