package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivoMeditacionService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity.UsuarioAutenticado;
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

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;

    @Autowired
    private ObjetivoMeditacionService objeto;

    public ObjetivoMeditacionViewController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        if ("ADMIN".equals(rol)) {
            model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());
        } else {
            model.addAttribute("objetivos", objeto.getObjetivosByUsuario(usuarioId));
        }

        model.addAttribute("objetivo", new ObjetivoMeditacion());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "objetivoMeditacion";
    }

    @PostMapping("/guardar")
    public String guardar(ObjetivoMeditacion objetivo, Model model, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        try {
            if (!"ADMIN".equals(rol)) {
                objetivo.setFkIdUsuario(usuarioId);
            } else {
                if (!usuarioRepository.existsById(objetivo.getFkIdUsuario())) {
                    throw new IllegalArgumentException("No se encontró el usuario con id: " + objetivo.getFkIdUsuario());
                }
            }

            objeto.saveObjetivoMeditacion(objetivo);
            redirect.addFlashAttribute("success", "Objetivo guardado correctamente");
            return "redirect:/objetivoMeditacion";

        } catch (Exception ex) {

            model.addAttribute("objetivo", objetivo);
            if ("ADMIN".equals(rol)) {
                model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());
            } else {
                model.addAttribute("objetivos", objeto.getObjetivosByUsuario(usuarioId));
            }
            model.addAttribute("usuarios", usuarioService.getAllUsuarios());
            model.addAttribute("errores",  List.of(ex.getMessage()));
            return "objetivoMeditacion";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        ObjetivoMeditacion objetivo = objeto.getObjetivosMeditacionById(id);

        if (objetivo == null) {
            redirect.addFlashAttribute("errores", List.of("Objetivo no encontrado"));
            return "redirect:/objetivoMeditacion";
        }

        if (!"ADMIN".equals(rol) && !usuarioId.equals(objetivo.getFkIdUsuario())) {
            redirect.addFlashAttribute("errores", List.of("No tienes permiso para editar este objetivo"));
            return "redirect:/objetivoMeditacion";
        }

        if ("ADMIN".equals(rol)) {
            model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());
        } else {
            model.addAttribute("objetivos", objeto.getObjetivosByUsuario(usuarioId));
        }

        model.addAttribute("objetivo", objetivo);
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "objetivoMeditacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        ObjetivoMeditacion objetivo = objeto.getObjetivosMeditacionById(id);

        if (objetivo == null) {
            redirect.addFlashAttribute("errores", List.of("Objetivo no encontrado"));
            return "redirect:/objetivoMeditacion";
        }

        if (!"ADMIN".equals(rol) && !usuarioId.equals(objetivo.getFkIdUsuario())) {
            redirect.addFlashAttribute("errores", List.of("No tienes permiso para eliminar este objetivo"));
            return "redirect:/objetivoMeditacion";
        }

        objeto.deleteObjetivoMeditacion(id);
        redirect.addFlashAttribute("success", "Objetivo eliminado correctamente");
        return "redirect:/objetivoMeditacion";
    }
}