package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Service.FraseMotivadoraService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraseMotivadora")
public class FraseMotivadoraController {
    private final FraseMotivadoraService fraseMotivadoraService;

    public FraseMotivadoraController(FraseMotivadoraService fraseMotivadoraService) {
        this.fraseMotivadoraService = fraseMotivadoraService;
    }

    @GetMapping
    public List<FraseMotivadora> getReadAll(){
        return fraseMotivadoraService.getAllFraseMotivadora();
    }

    @PostMapping
    public ResponseEntity<Object> createFraseMotivadora(@Valid @RequestBody FraseMotivadora fraseMotivadora){
        try {
            FraseMotivadora createFrase = fraseMotivadoraService.saveFraseMotivadora(fraseMotivadora);
            return new ResponseEntity<>(createFrase, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFraseMotivadora(@PathVariable Integer id, @Valid @RequestBody FraseMotivadora fraseMotivadora){
        try {
            FraseMotivadora updateFrase = fraseMotivadoraService.updateFraseMotivadora(id,fraseMotivadora);
            return new ResponseEntity<>(updateFrase,HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFraseMotivadora(@PathVariable Integer id){
        try {
            fraseMotivadoraService.deleteFraseMotivadora(id);
            return ResponseEntity.ok("Frase eliminada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
