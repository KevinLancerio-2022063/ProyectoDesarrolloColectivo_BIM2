package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginViewController {

    private final UsuarioRepository usuarioRepository;

    public LoginViewController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Mostrar vista login
    @GetMapping
    public String mostrarLogin() {
        return "login";
    }

    // Procesar login
    @PostMapping
    public String procesarLogin(
            @RequestParam String usuario,   // correo
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Usuario encontrado = usuarioRepository.findByCorreoUsuario(
                usuario.trim().toLowerCase()
        );

        if (encontrado == null || !encontrado.getContraseña().equals(password)) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }


        session.setAttribute("usuarioId", encontrado.getId_usuario());
        session.setAttribute("usuarioNombre", encontrado.getNombre_completo());


        return "redirect:/fraseMotivadora";
    }
}