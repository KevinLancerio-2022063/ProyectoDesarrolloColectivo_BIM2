package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaLectura;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RachaLecturaService;
import jakarta.servlet.http.HttpSession;
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
    public String mostrarVista(
            @RequestParam(value = "usuarioIdBuscar", required = false) Integer usuarioIdBuscar,
            HttpSession session,
            Model model) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }


        if ("ADMIN".equals(rol)) {

            if (usuarioIdBuscar != null) {
                model.addAttribute(
                        "rachas",
                        rachaLecturaService.getRachasByUsuario(usuarioIdBuscar)
                );
                model.addAttribute("usuarioBuscado", usuarioIdBuscar);
            } else {
                model.addAttribute(
                        "rachas",
                        rachaLecturaService.getAllRachas()
                );
            }

        }

        else {
            model.addAttribute(
                    "rachas",
                    rachaLecturaService.getRachasByUsuario(usuarioId)
            );
        }

        return "racha-lectura";
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
                    rachaLecturaService.getRachasByUsuario(usuarioId)
            );
            return "racha-lectura";
        }

        // Validación duplicado
        List<RachaLectura> historial =
                rachaLecturaService.getRachasByUsuario(usuarioId);

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

        rachaLecturaService.addRacha(usuarioId, fecha);

        return "redirect:/rachaLectura";
    }
}
