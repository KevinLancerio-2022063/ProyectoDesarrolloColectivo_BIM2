package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaEjercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RachaEjercicioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/rachaEjercicio")
public class RachaEjercicioViewController {

    private final RachaEjercicioService rachaEjercicioService;

    public RachaEjercicioViewController(RachaEjercicioService rachaEjercicioService) {
        this.rachaEjercicioService = rachaEjercicioService;
    }

    @GetMapping
    public String mostrarVista(HttpSession session, Model model) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");

        // Protección: si no hay sesión
        if (usuarioId == null) {
            return "redirect:/login";
        }

        model.addAttribute(
                "rachas",
                rachaEjercicioService.getRachasByUsuario(usuarioId)
        );

        return "racha-ejercicio";
    }

    @PostMapping
    public String guardarRacha(
            @RequestParam String fechaRacha,
            HttpSession session,
            Model model) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");

        if (usuarioId == null) {
            return "redirect:/login";
        }

        LocalDate fecha = LocalDate.parse(fechaRacha);
        LocalDate hoy = LocalDate.now();

        // Validación fecha pasada
        if (fecha.isBefore(hoy)) {
            model.addAttribute("error", "La fecha no puede ser pasada");
            model.addAttribute(
                    "rachas",
                    rachaEjercicioService.getRachasByUsuario(usuarioId)
            );
            return "racha-ejercicio";
        }

        // Validación duplicado
        List<RachaEjercicio> historial =
                rachaEjercicioService.getRachasByUsuario(usuarioId);

        for (RachaEjercicio r : historial) {
            if (r.getFecha() != null && r.getFecha().equals(fecha)) {
                model.addAttribute(
                        "error",
                        "Ya registraste actividad para el día " + fecha
                );
                model.addAttribute("rachas", historial);
                return "racha-ejercicio";
            }
        }

        rachaEjercicioService.addRacha(usuarioId, fecha);

        return "redirect:/rachaEjercicio";
    }
}
