package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/logn")
    public String login() {
        return "login";
    }
    @PostMapping("/loin")
    public String procesarLogn() {
        return "redirect:/register";
    }
    @GetMapping("/regiser")
    public String regiter() {
        return "register";
    }
    @GetMapping("/rutinas")
    public String rutinas() {
        return "entradaDiario";
    }
    @GetMapping("/bienestar")
    public String bienestar() {
        return "RegistroMeditacion";
    }
    @GetMapping("/nutricion")
    public String nutricion() {
        return "entradaDiario";
    }

    @GetMapping("/home")
    public String home() {return "Home";}
}
