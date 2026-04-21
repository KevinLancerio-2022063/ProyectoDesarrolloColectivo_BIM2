package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaEjercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RachaEjercicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rachaEjercicio") // Ruta base: localhost:8080/rachaEjercicio
public class RachaEjercicioViewController {

    private final RachaEjercicioService rachaEjercicioService;

    public RachaEjercicioViewController(RachaEjercicioService rachaEjercicioService) {
        this.rachaEjercicioService = rachaEjercicioService;
    }

    @GetMapping
    public String mostrarVista(@RequestParam(required = false) Integer usuarioId, Model model) {
        if (usuarioId != null) {
            model.addAttribute("rachas", rachaEjercicioService.getRachasByUsuario(usuarioId));
            model.addAttribute("usuarioId", usuarioId);
        }
        return "racha-ejercicio";
    }


    @PostMapping
    public String guardarRacha(
            @RequestParam Integer usuarioId,
            @RequestParam String fechaRacha,
            Model model) {

        LocalDate fecha = LocalDate.parse(fechaRacha);
        LocalDate hoy = LocalDate.now();

        // Validación de fecha pasada
        if (fecha.isBefore(hoy)) {
            model.addAttribute("error", "La fecha no puede ser pasada");
            model.addAttribute("rachas", rachaEjercicioService.getRachasByUsuario(usuarioId));
            return "racha-ejercicio";
        }

        // Validación de duplicados
        List<RachaEjercicio> historial = rachaEjercicioService.getRachasByUsuario(usuarioId);
        if (historial != null) {
            for (RachaEjercicio r : historial) {
                if (r.getFecha() != null && r.getFecha().equals(fecha)) {
                    model.addAttribute("error", "Ya registraste actividad para el día " + fecha);
                    model.addAttribute("rachas", historial);
                    return "racha-ejercicio";
                }
            }
        }

        rachaEjercicioService.addRacha(usuarioId, fecha);


        return "redirect:/rachaEjercicio?usuarioId=" + usuarioId;
    }
}
