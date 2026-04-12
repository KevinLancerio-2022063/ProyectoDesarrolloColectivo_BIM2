package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroSuenoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrosSuenos")
public class RegistroSuenoController {

    private final RegistroSuenoService registroSuenoService;

    public RegistroSuenoController(RegistroSuenoService registroSuenoService) {
        this.registroSuenoService = registroSuenoService;
    }

    @GetMapping
    public List<RegistroSueno> getAllRegistrosSuenos() {
        return registroSuenoService.getAllRegistrosSuenos();
    }

    @PostMapping
    public ResponseEntity<Object> createRegistrosSueno(@Valid @RequestBody RegistroSueno registroSueno) {
        try {
            RegistroSueno createdRegistroSueno = registroSuenoService.saveRegistroSueno(registroSueno);
            return new ResponseEntity<>(createdRegistroSueno, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>getRegistrosSuenosById(@PathVariable Integer id) {
        RegistroSueno registroSueno = registroSuenoService.getRegistrosSuenosById(id);

        if (registroSueno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro Sueño no encontrado");
        }
        return ResponseEntity.ok(registroSueno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRegistroSueno(@PathVariable Integer id, @RequestBody RegistroSueno registroSueno) {

        RegistroSueno actualizado = registroSuenoService.updateRegistroSueno(id, registroSueno);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistroSueno(@PathVariable Integer id) {

        RegistroSueno registroSueno = registroSuenoService.getRegistrosSuenosById(id);

        if (registroSueno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Registro sueño no existe");
        }

        registroSuenoService.deleteRegistroSueno(id);
        return ResponseEntity.ok("Registro sueño eliminado correctamente");
    }

}
