package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaLectura;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RachaLecturaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/racha-lectura")
public class RachaLecturaController {

    private final RachaLecturaService rachaLecturaService;

    public RachaLecturaController(RachaLecturaService rachaLecturaService) { this.rachaLecturaService = rachaLecturaService;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<RachaLectura> getByUsuario(@PathVariable Integer idUsuario) {
        return rachaLecturaService.getRachasByUsuario(idUsuario);
    }


    @GetMapping("/usuario/{idUsuario}/entre/{inicio}/{fin}")
    public List<RachaLectura> getByUsuarioYRango( @PathVariable Integer idUsuario, @PathVariable String inicio, @PathVariable String fin) {
        LocalDate ini = LocalDate.parse(inicio);
        LocalDate fn  = LocalDate.parse(fin);
        return rachaLecturaService.getRachasByUsuarioAndRango(idUsuario, ini, fn);
    }


    @PostMapping("/agregar")
    public ResponseEntity<Object> agregarRacha(@jakarta.validation.Valid @RequestBody RachaLectura racha, org.springframework.validation.BindingResult bindingResult) {
        try { if (bindingResult.hasErrors()) {
                java.util.Map<String, String> errors = new java.util.HashMap<>();
                bindingResult.getFieldErrors()
                        .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
                return ResponseEntity.badRequest().body(errors);
            }
            RachaLectura nueva = rachaLecturaService.saveRacha(racha);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }





    @PostMapping("/usuario/{idUsuario}/fecha/{fecha}")
    public ResponseEntity<Object> crearRachaPorUsuario( @PathVariable Integer idUsuario, @PathVariable String fecha) {

        try {
            LocalDate fechaParseada;
            try {
                fechaParseada = LocalDate.parse(fecha);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Formato de fecha inválido. Usa YYYY-MM-DD.");
            }
            try { fechaParseada = LocalDate.parse(fecha);
            } catch (Exception e) { return ResponseEntity.badRequest().body("Formato de fecha inválido. Usa YYYY-MM-DD."); }

            LocalDate hoy = LocalDate.now();
            if (fechaParseada.isBefore(hoy)) {
                return ResponseEntity.badRequest().body("La fecha no puede ser pasada");
            }
            RachaLectura nueva = rachaLecturaService.addRacha(idUsuario, fechaParseada);
            return new ResponseEntity<>(nueva, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) { return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

