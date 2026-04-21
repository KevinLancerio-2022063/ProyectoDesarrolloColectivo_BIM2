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
        }

        return "racha-lectura";
    }


    @PostMapping
    public String guardarRacha(
            @RequestParam Integer usuarioId,
            @RequestParam String fechaRacha) {

        LocalDate fecha = LocalDate.parse(fechaRacha);

        rachaLecturaService.addRacha(usuarioId, fecha);

        return "redirect:/rachaLectura?usuarioId=" + usuarioId;
    }
}
