package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ApoyoEmocionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apoyoEmocional")
public class ApoyoEmocionalController {
    private final ApoyoEmocionalService apoyoEmocionalService;

    public ApoyoEmocionalController(ApoyoEmocionalService apoyoEmocionalService) {
        this.apoyoEmocionalService = apoyoEmocionalService;
    }

    @GetMapping
    public List<ApoyoEmocional> getAllApoyoEmocional(){return apoyoEmocionalService.getAllFraseMotivadora();}

    @PostMapping
    public ResponseEntity<Object> createApoyoEmocional(@Valid @RequestBody ApoyoEmocional apoyoEmocional){
        try {
            ApoyoEmocional createApoyo = apoyoEmocionalService.saveFraseMotivadora(apoyoEmocional);
            return new ResponseEntity<>(createApoyo, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateApoyoEmocional(@PathVariable Integer id,@Valid @RequestBody ApoyoEmocional apoyoEmocional){
        try {
            ApoyoEmocional updateApoyo = apoyoEmocionalService.updateFraseMotivadora(id, apoyoEmocional);
            return new ResponseEntity<>(updateApoyo,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApoyoEmocional(@PathVariable Integer id,ApoyoEmocional apoyoEmocional){
        try {
            apoyoEmocionalService.deleteFraseMotivadora(id);
            return ResponseEntity.ok("Datos eliminados correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
