package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroSuenoService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity.UsuarioAutenticado;
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

    @Autowired
    private UsuarioAutenticado usuarioAutenticado;

    @Autowired
    private RegistroSuenoService service;

    public RegistroSuenoViewController(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        if ("ADMIN".equals(rol)) {
            model.addAttribute("registros", service.getAllRegistrosSuenos());

        } else {
            model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));

        }

        model.addAttribute("registro", new RegistroSueno());
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        return "registroSueno";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid RegistroSueno registro, BindingResult result, Model model, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        // Asignamos el rol de USER automáticamente
        if (!"ADMIN".equals(rol)) {

            registro.setFkIdUsuario(usuarioId);
        }

        if (result.hasErrors()) {

            if ("ADMIN".equals(rol)) {model.addAttribute("registros", service.getAllRegistrosSuenos());

            } else {
                model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));
            }

            model.addAttribute("usuarios", usuarioService.getAllUsuarios());
            model.addAttribute("errores", result.getFieldErrors().stream().map(e -> e.getDefaultMessage()).toList());

            return "registroSueno";
        }

        try {

            if ("ADMIN".equals(rol)) {

                if (!usuarioRepository.existsById(registro.getFkIdUsuario())) {
                    throw new RuntimeException("Usuario no encontrado");
                }
            }

        } catch (RuntimeException ex) {

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
    public String editar(@PathVariable Integer id, Model model, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        RegistroSueno registro = service.getRegistrosSuenosById(id);

        if (registro == null) {

            redirect.addFlashAttribute("errores", List.of("Registro no encontrado"));

            return "redirect:/registroSueno";
        }

        if (!"ADMIN".equals(rol) && !usuarioId.equals(registro.getFkIdUsuario())) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para editar este registro"));

            return "redirect:/registroSueno";
        }

        if ("ADMIN".equals(rol)) {
            model.addAttribute("registros", service.getAllRegistrosSuenos());

        } else {
            model.addAttribute("registros", service.getRegistrosByUsuario(usuarioId));

        }

        model.addAttribute("registro", registro);
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());

        return "registroSueno";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirect) {

        String rol = usuarioAutenticado.getRol();
        Integer usuarioId = usuarioAutenticado.getId();

        RegistroSueno registro = service.getRegistrosSuenosById(id);

        if (registro == null) {

            redirect.addFlashAttribute("errores", List.of("Registro no encontrado"));

            return "redirect:/registroSueno";
        }

        if (!"ADMIN".equals(rol) && !usuarioId.equals(registro.getFkIdUsuario())) {

            redirect.addFlashAttribute("errores", List.of("No tienes permiso para eliminar este registro"));

            return "redirect:/registroSueno";
        }

        service.deleteRegistroSueno(id);

        redirect.addFlashAttribute("success", "Registro eliminado correctamente");

        return "redirect:/registroSueno";
    }
}