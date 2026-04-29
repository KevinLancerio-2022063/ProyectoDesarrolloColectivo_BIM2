package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroSuenoService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/registroSueno")
public class RegistroSuenoViewController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public RegistroSuenoViewController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private RegistroSuenoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("registro", new RegistroSueno());
        model.addAttribute("registros", service.getAllRegistrosSuenos());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        return "registroSueno";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid RegistroSueno registro, BindingResult result, Model model, RedirectAttributes redirect) {

        if (result.hasErrors()) {
            model.addAttribute("registros", service.getAllRegistrosSuenos());
            model.addAttribute("usuarios", usuarioService.getAllUsuarios());
            model.addAttribute("errores", result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList());

            return "registroSueno";
        }

        try {
            if (!usuarioRepository.existsById(registro.getFk_id_usuario())) {
                throw new RuntimeException("Usuario no encontrado");
            }

        } catch (IllegalArgumentException ex) {

            model.addAttribute("registro", registro);
            model.addAttribute("registros", service.getAllRegistrosSuenos());
            model.addAttribute("usuarios", usuarioService.getAllUsuarios());
            model.addAttribute("errores", List.of(ex.getMessage()));

            return "registroSueno";
        }

        service.saveRegistroSueno(registro);

        redirect.addFlashAttribute("success", "Registro guardado correctamente");

        return "redirect:/registroSueno";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("registro", service.getRegistrosSuenosById(id));
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        model.addAttribute("registros", service.getAllRegistrosSuenos());

        return "registroSueno";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {
        service.deleteRegistroSueno(id);
        redirect.addFlashAttribute("success", "El registro Sueño se ha eliminado correctamente");
        return "redirect:/registroSueno";
    }

}
