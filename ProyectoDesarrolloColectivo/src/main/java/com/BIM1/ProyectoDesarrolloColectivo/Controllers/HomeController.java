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
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/login")
    public String procesarLogin() {
        return "redirect:/register";
    }
    @GetMapping("/register")
    public String register() {
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
}
