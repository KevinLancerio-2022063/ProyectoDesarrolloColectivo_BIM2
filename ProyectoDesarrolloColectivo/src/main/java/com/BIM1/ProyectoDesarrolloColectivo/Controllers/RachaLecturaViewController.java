package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaLectura;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RachaLecturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rachaLectura")
public class RachaLecturaViewController {

    private final RachaLecturaService rachaLecturaService;

    public RachaLecturaViewController(RachaLecturaService rachaLecturaService) {
        this.rachaLecturaService = rachaLecturaService;
    }

    @GetMapping
    public String mostrarVista(@RequestParam(required = false) Integer usuarioId, Model model) {

        if (usuarioId != null) {
            List<RachaLectura> rachas = rachaLecturaService.getRachasByUsuario(usuarioId);
            model.addAttribute("rachas", rachas);
            model.addAttribute("usuarioId", usuarioId);
        }

        return "racha-lectura";
    }

    @PostMapping
    public String guardarRacha(
            @RequestParam Integer usuarioId,
            @RequestParam String fechaRacha,
            Model model) {

        LocalDate fecha = LocalDate.parse(fechaRacha);
        LocalDate hoy = LocalDate.now();

        // Validación para fecha pasada
        if (fecha.isBefore(hoy)) {
            model.addAttribute("error", "La fecha no puede ser pasada");
            model.addAttribute(
                    "rachas",
                    rachaLecturaService.getRachasByUsuario(usuarioId)
            );
            return "racha-lectura";
        }

        // Validación para racha duplicada en la misma fecha
        List<RachaLectura> historial = rachaLecturaService.getRachasByUsuario(usuarioId);
        if (historial != null) {
            for (RachaLectura r : historial) {
                if (r.getFecha() != null && r.getFecha().equals(fecha)) {
                    model.addAttribute(
                            "error",
                            "Ya registraste una racha de lectura para el día " + fecha
                    );
                    model.addAttribute("rachas", historial);
                    return "racha-lectura";
                }
            }
        }


        rachaLecturaService.addRacha(usuarioId, fecha);


        return "redirect:/rachaLectura?usuarioId=" + usuarioId;
    }
}
