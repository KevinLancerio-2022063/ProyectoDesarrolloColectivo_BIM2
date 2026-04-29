package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivoMeditacionService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String listar(Model model) {
        model.addAttribute("objetivo", new ObjetivoMeditacion());
        model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "objetivoMeditacion";
    }

    @PostMapping("/guardar")
    public String guardar(ObjetivoMeditacion objetivo, Model model, RedirectAttributes redirect) {

        try {
            if (!usuarioRepository.existsById(objetivo.getFk_id_usuario())){
                throw new IllegalArgumentException("No se ha encontrado un objetivo meditación con el id: "+ objetivo.getFk_id_usuario());
            }
            objeto.saveObjetivoMeditacion(objetivo);

            redirect.addFlashAttribute("success", "Objetivo guardado correctamente");
            return "redirect:/objetivoMeditacion";

        } catch (Exception ex) {

            model.addAttribute("objetivo", objetivo);
            model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());
            model.addAttribute("usuarios", usuarioService.getAllUsuarios());
            model.addAttribute("errores", List.of(ex.getMessage()));

            return "objetivoMeditacion";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        model.addAttribute("objetivo", objeto.getObjetivosMeditacionById(id));
        model.addAttribute("objetivos", objeto.getAllObjetivosMeditacion());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        return "objetivoMeditacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {

        objeto.deleteObjetivoMeditacion(id);
        redirect.addFlashAttribute("success", "Objetivo eliminado correctamente");

        return "redirect:/objetivoMeditacion";
    }
}
