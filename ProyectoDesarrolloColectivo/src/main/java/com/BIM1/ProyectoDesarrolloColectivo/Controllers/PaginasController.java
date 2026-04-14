package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaginasController {

    // Redirección inicial
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    // --- LOGIN ---
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {


        if ("admin@gmail.com".equals(usuario) && "1234".equals(password)) {
            session.setAttribute("usuarioLogueado", usuario);
            // Redirección al home futuro del otro compañero
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }


    @GetMapping("/register")
    public String mostrarRegistro() {
        return "register"; // templates/register.html
    }

    @PostMapping("/register")
    public String registrar() {

        return "redirect:/login";
    }



    @GetMapping("/rachaLectura")
    public String mostrarRachaLectura() {

        return "racha-lectura";
    }

    @GetMapping("/rachaEjercicio")
    public String mostrarRachaEjercicio() {
        return "racha-ejercicio";
    }
}