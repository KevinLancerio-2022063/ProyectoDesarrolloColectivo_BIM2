package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RutinaService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutina")
public class RutinaController {
    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", new Rutina());
        return "rutina";
    }

    @GetMapping("/editarRutina/{id}")
    public String editarRutina(@PathVariable Integer id, Model model){
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", rutinaService.getRutinaById(id));
        return "rutina";
    }

    @GetMapping("/buscarRutina")
    public String buscarRutina(@RequestParam Integer id, Model model){
        Rutina rutina = rutinaService.getRutinaById(id);
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", rutina);
        return "rutina";
    }

}
