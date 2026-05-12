package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity.UsuarioAutenticado;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioViewController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String listar(Model model) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        if ("ADMIN".equals(rol)) {
            // El rol de ADMIN puede ver todos los usuarios
            model.addAttribute("usuarios", service.getAllUsuarios());
        } else {
            // El rol de USER solo puede ver su propia tarjeta
            model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));
        }

        model.addAttribute("usuario", new Usuario());
        model.addAttribute("confirmarAdmin", "ADMIN".equals(rol));
        return "usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        if (result.hasErrors()) {
            if ("ADMIN".equals(rol)) {
                model.addAttribute("usuarios", service.getAllUsuarios());

            } else {
                model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));
            }

            model.addAttribute("confirmarAdmin", "ADMIN".equals(rol));
            model.addAttribute("errores", result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList());

            return "usuarios";
        }

        boolean nuevaCuenta = (usuario.getId_usuario() == null);

        if (nuevaCuenta) {

            usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

            usuario.setRol("USER");

        } else {

            Usuario usuarioExistente = service.getUsuariosById(usuario.getId_usuario());

            // Mantenemos el rol original
            usuario.setRol(usuarioExistente.getRol());

            // Mantenemos la  contraseña anterior si no escribió nueva
            if (usuario.getContraseña() == null || usuario.getContraseña().isBlank()) {

                usuario.setContraseña(usuarioExistente.getContraseña());

            } else {
                usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

            }
        }

        service.saveUsuario(usuario);

        redirect.addFlashAttribute("success", nuevaCuenta ? "Cuenta agregada: " + usuario.getNombre_completo() : usuario.getNombre_completo() + " actualizado correctamente");

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        // El USER solo puede editar su propio perfil
        if (!"ADMIN".equals(rol) && !id.equals(usuarioId)) {
            redirect.addFlashAttribute("errores", List.of("No tienes permiso para editar este usuario"));
            return "redirect:/usuarios";
        }

        if ("ADMIN".equals(rol)) {
            model.addAttribute("usuarios", service.getAllUsuarios());

        } else {
            model.addAttribute("usuarios", List.of(service.getUsuariosById(usuarioId)));
        }

        model.addAttribute("usuario", service.getUsuariosById(id));
        model.addAttribute("confirmarAdmin", "ADMIN".equals(rol));
        return "usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();

        // Solo el ADMIN puede eliminar usuarios
        if (!"ADMIN".equals(rol)) {
            redirect.addFlashAttribute("errores", List.of("No tienes permiso para eliminar usuarios"));
            return "redirect:/usuarios";
        }

        Usuario usuario = service.getUsuariosById(id);
        service.deleteUsuario(id);
        redirect.addFlashAttribute("success", usuario.getNombre_completo() + " eliminado correctamente");
        return "redirect:/usuarios";
    }
}

