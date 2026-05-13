package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivoMeditacionService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/objetivoMeditacion")
public class ObjetivoMeditacionViewController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    public ObjetivoMeditacionViewController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @Autowired
    private ObjetivoMeditacionService objeto;

    @GetMapping
    public String listar(HttpSession session, Model model) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("objetivo", new ObjetivoMeditacion());

        // El ADMIN puede ver todos los objetivos
        if ("ADMIN".equals(rol)) {

            model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());

            model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        } else {

            // El usuario solo puede ver sus propios objetivos
            model.addAttribute("objetivos", objeto.getObjetivosByUsuario(usuarioId));
        }

        return "objetivoMeditacion";
    }

    @PostMapping("/guardar")
    public String guardar(ObjetivoMeditacion objetivo, HttpSession session, Model model, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // EL usuario solo puede guardar su propio objetivo
        if (!"ADMIN".equals(rol)) {
            objetivo.setFkIdUsuario(usuarioId);
        }

        try {

            if (!usuarioRepository.existsById(objetivo.getFkIdUsuario())) {
                throw new IllegalArgumentException(
                        "Usuario no encontrado"
                );
            }

            objeto.saveObjetivoMeditacion(objetivo);

            redirect.addFlashAttribute("success", "Objetivo guardado correctamente");

            return "redirect:/objetivoMeditacion";

        } catch (Exception ex) {

            model.addAttribute("objetivo", objetivo);

            if ("ADMIN".equals(rol)) {

                model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());

                model.addAttribute("usuarios", usuarioService.getAllUsuarios());

            } else {

                model.addAttribute("objetivos", objeto.getObjetivosByUsuario(usuarioId));
            }

            model.addAttribute("errores", List.of(ex.getMessage()));

            return "objetivoMeditacion";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, HttpSession session, Model model, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        ObjetivoMeditacion objetivo = objeto.getObjetivosMeditacionById(id);

        // Solo el usuario puede editar sus propios registros
        if (!"ADMIN".equals(rol) && !objetivo.getFkIdUsuario().equals(usuarioId)) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para editar este objetivo"));

            return "redirect:/objetivoMeditacion";
        }

        model.addAttribute("objetivo", objetivo);

        if ("ADMIN".equals(rol)) {

            model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());

            model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        } else {

            model.addAttribute("objetivos", objeto.getObjetivosByUsuario(usuarioId));
        }

        return "objetivoMeditacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, HttpSession session, RedirectAttributes redirect) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        ObjetivoMeditacion objetivo = objeto.getObjetivosMeditacionById(id);

        // El usuario solo puede eliminar sus registros
        if (!"ADMIN".equals(rol) && !objetivo.getFkIdUsuario().equals(usuarioId)) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para eliminar este objetivo"));

            return "redirect:/objetivoMeditacion";
        }

        objeto.deleteObjetivoMeditacion(id);

        redirect.addFlashAttribute("success", "Objetivo eliminado correctamente");

        return "redirect:/objetivoMeditacion";
    }
}