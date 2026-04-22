package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterViewController {

    private final UsuarioService usuarioService;

    public RegisterViewController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mostrar formulario de registro
    @GetMapping
    public String mostrarRegister() {
        return "register";
    }

    // Procesar registro
    @PostMapping
    public String procesarRegister(
            @RequestParam String nombre,
            @RequestParam String usuario, // correo
            @RequestParam String password,
            @RequestParam String confirmarPassword,
            Model model) {

        if (!password.equals(confirmarPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "register";
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre_completo(nombre);
        nuevo.setCorreoUsuario(usuario);
        nuevo.setContraseña(password);

        try {
            usuarioService.saveUsuario(nuevo);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }


        return "redirect:/login";
    }
}

