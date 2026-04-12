package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EntradaDiarioService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/entradaDiario")
public class EntradaDiarioController {
    private final com.BIM1.ProyectoDesarrolloColectivo.Service.EntradaDiarioService entradaDiarioService;
    public EntradaDiarioController(com.BIM1.ProyectoDesarrolloColectivo.Service.EntradaDiarioService entradaDiarioService) {
        this.entradaDiarioService = entradaDiarioService;
    }
    @GetMapping
    public List<EntradaDiario> getAListEntradaDiario(){
        return entradaDiarioService.getAListEntradaDiario();
    }

    @PostMapping
    public ResponseEntity<Object> createEntradaDiario(@Valid @RequestBody EntradaDiario entradaDiario){
        try {
            EntradaDiario entradaDiario1 = entradaDiarioService.saveEntradaDiario(entradaDiario);
            return new ResponseEntity<>(entradaDiario1, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEntradaDiario(@PathVariable Integer id, @Valid @RequestBody EntradaDiario entradaDiario) {
        try {
            EntradaDiario entradaDiario1 = entradaDiarioService.updateEntradaDiario(id, entradaDiario);
            return new ResponseEntity<>(entradaDiario1, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEntradaDiario(@PathVariable Integer id){
        try {
            entradaDiarioService.deleteEntradaDiario(id);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEntradaDiarioById(@PathVariable Integer id){
        try{
            EntradaDiario entradaDiario = entradaDiarioService.getEntradaDiarioById(id);
            return ResponseEntity.ok(entradaDiario);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}