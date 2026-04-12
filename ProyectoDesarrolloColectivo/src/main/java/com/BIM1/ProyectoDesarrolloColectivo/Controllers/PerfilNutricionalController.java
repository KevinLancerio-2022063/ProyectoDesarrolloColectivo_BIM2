package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.PerfilNutricional;
import com.BIM1.ProyectoDesarrolloColectivo.Service.PerfilNutricionalService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfilNutricional")
public class PerfilNutricionalController {
    private final PerfilNutricionalService perfilNutricionalService;

    public PerfilNutricionalController(PerfilNutricionalService perfilNutricionalService) {
        this.perfilNutricionalService = perfilNutricionalService;
    }

    @GetMapping
    public List<PerfilNutricional> getAlistPerfilNutricional(){
        return perfilNutricionalService.getAListPerfilNuticional();
    }

    @PostMapping
    public ResponseEntity<Object> savePerfilNutricional(@Valid @RequestBody PerfilNutricional perfilNutricional){
            PerfilNutricional perfilNutricional1 = perfilNutricionalService.savePerfilNutrcional(perfilNutricional);
            return new ResponseEntity<>(perfilNutricional1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerfilNutricional(@PathVariable Integer id, @Valid @RequestBody PerfilNutricional perfilNutricional){
            PerfilNutricional perfilNutricional1 = perfilNutricionalService.updatePerfilNutricional(id, perfilNutricional);
            return new ResponseEntity<>(perfilNutricional1, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePerfilNutricional(@PathVariable Integer id){
            perfilNutricionalService.deletePerfilNutricional(id);
            return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPerfilNutricionalById(@PathVariable Integer id){
            PerfilNutricional perfilNutricional = perfilNutricionalService.getPerfilNutricionalById(id);
            return ResponseEntity.ok(perfilNutricional);
    }
}
