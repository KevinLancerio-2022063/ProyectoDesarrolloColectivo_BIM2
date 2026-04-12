package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroMeditacionService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registroMeditacion")
public class RegistroMeditacionController {
    private final RegistroMeditacionService registroMeditacionService;

    public RegistroMeditacionController(RegistroMeditacionService registroMeditacionService) {
        this.registroMeditacionService = registroMeditacionService;
    }

    @GetMapping
    public List<RegistroMeditacion> getAListRegistroMeditacion(){
        return registroMeditacionService.getAListRegistroMeditacion();
    }

    @PostMapping
    public ResponseEntity<Object> createRegistroMeditacion(@Valid @RequestBody RegistroMeditacion registroMeditacion){
        try {
            RegistroMeditacion registroMeditacion1 = registroMeditacionService.saveRegistroMeditacion(registroMeditacion);
            return new ResponseEntity<>(registroMeditacion1, HttpStatus.CREATED);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRegistroMeditacion(@PathVariable Integer id, @Valid @RequestBody RegistroMeditacion registroMeditacion) {
        try {
            RegistroMeditacion registroMeditacion1 = registroMeditacionService.updateRegistroMeditacion(id, registroMeditacion);
            return new ResponseEntity<>(registroMeditacion1, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRegistroMeditacion(@PathVariable Integer id){
        try {
            registroMeditacionService.deleteRegistroMeditacion(id);
            return ResponseEntity.noContent().build();
        }catch (ObjectNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRegistroMeditacionById(@PathVariable Integer id){
        try{
            RegistroMeditacion registroMeditacion = registroMeditacionService.getRegistroMeditacionById(id);
            return ResponseEntity.ok(registroMeditacion);
        }catch (ObjectNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}