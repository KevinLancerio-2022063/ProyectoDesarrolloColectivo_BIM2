package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroSuenoService;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("/registroSueno")
public class RegistroSuenoViewController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public RegistroSuenoViewController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private RegistroSuenoService service;

    @GetMapping
    public String listar(HttpSession session, Model model) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("registro", new RegistroSueno());

        // El ADMIN puede ver todos los registros
        if ("ADMIN".equals(rol)) {

            model.addAttribute("registros", service.getAllRegistrosSuenos());

            model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        } else {

            // El usuario solo puede ver sus propios registros
            model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));
        }

        return "registroSueno";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid RegistroSueno registro, BindingResult result, HttpSession session, Model model, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // El usuario solo puede guardar sus datos
        if (!"ADMIN".equals(rol)) {
            registro.setFkIdUsuario(usuarioId);
        }

        if (result.hasErrors()) {

            if ("ADMIN".equals(rol)) {
                model.addAttribute("registros", service.getAllRegistrosSuenos());

                model.addAttribute("usuarios", usuarioService.getAllUsuarios());

            } else {

                model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));
            }

            model.addAttribute("errores", result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList()
            );

            return "registroSueno";
        }

        try {

            if (!usuarioRepository.existsById(registro.getFkIdUsuario())) {
                throw new RuntimeException("Usuario no encontrado");
            }

        } catch (Exception ex) {

            model.addAttribute("registro", registro);

            if ("ADMIN".equals(rol)) {

                model.addAttribute("registros", service.getAllRegistrosSuenos());

                model.addAttribute("usuarios", usuarioService.getAllUsuarios());

            } else {

                model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));
            }

            model.addAttribute("errores", List.of(ex.getMessage()));

            return "registroSueno";
        }

        service.saveRegistroSueno(registro);

        redirect.addFlashAttribute("success", "Registro guardado correctamente");

        return "redirect:/registroSueno";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        RegistroSueno registro = service.getRegistrosSuenosById(id);

        // El usuario solo puede editar sus registros
        if (!"ADMIN".equals(rol) && !registro.getFkIdUsuario().equals(usuarioId)) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para editar este registro"));

            return "redirect:/registroSueno";
        }

        model.addAttribute("registro", registro);

        if ("ADMIN".equals(rol)) {

            model.addAttribute("usuarios", usuarioService.getAllUsuarios());

            model.addAttribute("registros", service.getAllRegistrosSuenos());

        } else {

            model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));
        }

        return "registroSueno";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, HttpSession session, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        RegistroSueno registro = service.getRegistrosSuenosById(id);

        // El usuario solo puede eliminar sus registros
        if (!"ADMIN".equals(rol) && !registro.getFkIdUsuario().equals(usuarioId)) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para eliminar este registro"));

            return "redirect:/registroSueno";
        }

        service.deleteRegistroSueno(id);

        redirect.addFlashAttribute("success", "Registro eliminado correctamente");

        return "redirect:/registroSueno";
    }
}