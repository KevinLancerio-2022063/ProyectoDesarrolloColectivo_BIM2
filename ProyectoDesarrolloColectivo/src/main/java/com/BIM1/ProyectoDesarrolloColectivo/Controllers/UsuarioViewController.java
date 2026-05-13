package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public String listar(HttpSession session, Model model) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // El admin puede ver a todos los usuarios
        if ("ADMIN".equals(rol)) {

            model.addAttribute("usuarios", service.getAllUsuarios());

            model.addAttribute("confirmarAdmin", true);

        }

        // El usuario solo puede ver su propia tarjeta
        else {

            model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));

            model.addAttribute("confirmarAdmin", false);
        }

        model.addAttribute("usuario", new Usuario());

        return "usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {

            // El admin puede ver a todos los usuarios
            if ("ADMIN".equals(rol)) {

                model.addAttribute("usuarios", service.getAllUsuarios());

                model.addAttribute("confirmarAdmin", true);
            }

            // El usuario solo puedo ver su tarjeta de usuario
            else {

                model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));

                model.addAttribute("confirmarAdmin", false);
            }

            model.addAttribute("errores", result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList()
            );

            return "usuarios";
        }

        boolean nuevaCuenta = (usuario.getId_usuario() == null);

        // El usuario no puede editar a otros usuarios
        if (!"ADMIN".equals(rol)) {

            usuario.setId_usuario(usuarioId);
        }

        // Si el usuario es nuevo y no tiene rol se le asignará el rol USER
        if (usuario.getRol() == null || usuario.getRol().isBlank()) {

            usuario.setRol("USER");
        }

        try {

            service.saveUsuario(usuario);

        } catch (Exception e) {

            if ("ADMIN".equals(rol)) {

                model.addAttribute("usuarios", service.getAllUsuarios());

                model.addAttribute("confirmarAdmin", true);

            } else {

                model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));

                model.addAttribute("confirmarAdmin", false);
            }

            model.addAttribute("usuario", usuario);

            model.addAttribute("errores", List.of("El correo ya está registrado"));

            return "usuarios";
        }

        if (nuevaCuenta) {

            redirect.addFlashAttribute("success", "Tu cuenta se ha agregado correctamente " + usuario.getNombre_completo());

        } else {

            redirect.addFlashAttribute("success", usuario.getNombre_completo() + " se ha actualizado correctamente");
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, HttpSession session, Model model, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // El administrador puede ver a todos los usuarios
        if ("ADMIN".equals(rol)) {

            model.addAttribute("usuarios", service.getAllUsuarios());

            model.addAttribute("confirmarAdmin", true);

        }

        // El usuario solo puede ver su propia tarjeta de usuario
        else {

            model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));

            model.addAttribute("confirmarAdmin", false);
        }

        model.addAttribute("usuario", service.getUsuariosById(id));

        return "usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id, HttpSession session, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // Solo el admin puede eliminar usuarios
        if (!"ADMIN".equals(rol)) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para eliminar usuarios"));

            return "redirect:/usuarios";
        }

        Usuario usuario = service.getUsuariosById(id);

        service.deleteUsuario(id);

        redirect.addFlashAttribute("success", usuario.getNombre_completo() + " se ha eliminado correctamente");

        return "redirect:/usuarios";
    }

}

