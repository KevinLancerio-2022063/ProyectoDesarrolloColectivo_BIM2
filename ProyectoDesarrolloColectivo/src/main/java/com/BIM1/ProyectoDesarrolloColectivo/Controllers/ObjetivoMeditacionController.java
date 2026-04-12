package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivoMeditacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objetivosMeditacion")
public class ObjetivoMeditacionController {

    private final ObjetivoMeditacionService objetivoMeditacionService;

    public ObjetivoMeditacionController(ObjetivoMeditacionService objetivoMeditacionService) {
        this.objetivoMeditacionService = objetivoMeditacionService;
    }

    @GetMapping
    public List<ObjetivoMeditacion> getAllObjetivosMeditacion() {
        return objetivoMeditacionService.getAllObjetivosMeditacion();
    }

    @PostMapping
    public ResponseEntity<Object> createObjetivoMeditacion (@Valid @RequestBody ObjetivoMeditacion objetivoMeditacion) {
        try {
            ObjetivoMeditacion createdObjetivosMeditacion = objetivoMeditacionService.saveObjetivoMeditacion(objetivoMeditacion);
            return new ResponseEntity<>(createdObjetivosMeditacion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getObjetivosMeditacionById(@PathVariable Integer id) {
        ObjetivoMeditacion objetivoMeditacion = objetivoMeditacionService.getObjetivosMeditacionById(id);

        if (objetivoMeditacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Objetivo Meditación no encontrado");
        }
        return ResponseEntity.ok(objetivoMeditacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateObjetivoMeditacion(@PathVariable Integer id, @RequestBody ObjetivoMeditacion objetivoMeditacion) {

        ObjetivoMeditacion actualizado = objetivoMeditacionService.updateObjetivoMeditacion(id, objetivoMeditacion);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteObjetivoMeditacion(@PathVariable Integer id) {

        ObjetivoMeditacion objetivoMeditacion = objetivoMeditacionService.getObjetivosMeditacionById(id);

        if (objetivoMeditacion == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Objetivo meditación no existe");
        }

        objetivoMeditacionService.deleteObjetivoMeditacion(id);
        return ResponseEntity.ok("Objetivo meditación eliminado correctamente");
    }

}
