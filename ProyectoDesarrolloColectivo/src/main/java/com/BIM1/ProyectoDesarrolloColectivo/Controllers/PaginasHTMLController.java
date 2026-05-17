package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasHTMLController {

    @GetMapping("/ObjetivoMeditacion")
    public String MostrarObjetivoMeditacion() {
        return "ObjetivoMeditacion";
    }

    @GetMapping("/Usuario")
    public String Mostrarusuario() {
        return "Usuario";
    }

    @GetMapping("/RegistroSueno")
    public String MostrarRegistroSueno() {
        return "RegistroSueno";
    }
}
