package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EntradaDiarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/entradaDiario")
public class EntradaDiarioViewController {

    private final EntradaDiarioService entradaDiarioService;

    public EntradaDiarioViewController(EntradaDiarioService entradaDiarioService) {
        this.entradaDiarioService = entradaDiarioService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // Admin puede ver todos los registros
        if (rol.equals("ADMIN")) {

            model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());

        } else {

            // User solo puede ver sus propios registros
            model.addAttribute("entradaDiario", entradaDiarioService.getEntradasByUsuario(usuarioId));
        }

        model.addAttribute("entradaDiarioFormu", new EntradaDiario());

        return "entradaDiario";
    }

    @PostMapping("/guardar")
    public String guardarEntradaDiario(
            @Valid @ModelAttribute("entradaDiarioFormu") EntradaDiario entradaDiario,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if (rol.equals("USER")) {
            entradaDiario.setFkIdUsuario(usuarioId);
        }

        if (result.hasErrors()) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            } else {

                model.addAttribute("entradaDiario", entradaDiarioService.getEntradasByUsuario(usuarioId));
            }

            return "entradaDiario";
        }

        try {

            entradaDiarioService.saveEntradaDiario(entradaDiario);

            redirectAttributes.addFlashAttribute("exito", "La entrada de diario fue añadida correctamente");

        } catch (IllegalArgumentException e) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            } else {

                model.addAttribute("entradaDiario", entradaDiarioService.getEntradasByUsuario(usuarioId));
            }

            model.addAttribute("error", e.getMessage());

            return "entradaDiario";
        }

        return "redirect:/entradaDiario";
    }

    @GetMapping("/editar/{id}")
    public String editarEntradaDiario(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            EntradaDiario entrada = entradaDiarioService.getEntradaDiarioById(id);

            if (rol.equals("USER") && !entrada.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este registro");

                return "redirect:/entradaDiario";
            }

            if (rol.equals("ADMIN")) {

                model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            } else {

                model.addAttribute("entradaDiario", entradaDiarioService.getEntradasByUsuario(usuarioId));
            }

            model.addAttribute("entradaDiarioFormu", entrada);

            return "entradaDiario";

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Entrada de diario no encontrada");

            return "redirect:/entradaDiario";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarEntradaDiario(
            @PathVariable Integer id,
            @Valid @ModelAttribute("entradaDiarioFormu") EntradaDiario entradaDiario,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            EntradaDiario entradaExistente = entradaDiarioService.getEntradaDiarioById(id);

            if (rol.equals("USER") && !entradaExistente.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para actualizar este registro");
                return "redirect:/entradaDiario";
            }

            if (rol.equals("USER")) {
                entradaDiario.setFkIdUsuario(usuarioId);
            }

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Entrada de diario no encontrada");

            return "redirect:/entradaDiario";
        }

        if (result.hasErrors()) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            } else {

                model.addAttribute("entradaDiario", entradaDiarioService.getEntradasByUsuario(usuarioId));
            }

            return "entradaDiario";
        }

        try {

            entradaDiarioService.updateEntradaDiario(id, entradaDiario);

            redirectAttributes.addFlashAttribute("exito", "Entrada de diario actualizada correctamente");

        } catch (IllegalArgumentException | ObjectNotFoundException e) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            } else {

                model.addAttribute("entradaDiario", entradaDiarioService.getEntradasByUsuario(usuarioId)
                );
            }

            model.addAttribute("error", e.getMessage());

            return "entradaDiario";
        }

        return "redirect:/entradaDiario";
    }

    @GetMapping("/confirmarEliminar/{id}")
    public String confirmarEliminar(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            EntradaDiario entrada = entradaDiarioService.getEntradaDiarioById(id);

            if (rol.equals("USER") && !entrada.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar este registro");
                return "redirect:/entradaDiario";
            }

            model.addAttribute("entrada", entrada);
            return "entradaDiarioEliminar";

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Entrada de diario no encontrada");
            return "redirect:/entradaDiario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEntradaDiario(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            EntradaDiario entrada = entradaDiarioService.getEntradaDiarioById(id);

            if (rol.equals("USER") && !entrada.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar este registro");
                return "redirect:/entradaDiario";
            }

            entradaDiarioService.deleteEntradaDiario(id);

            redirectAttributes.addFlashAttribute("exito", "Entrada de diario eliminada correctamente");

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Entrada de diario no encontrada");
        }

        return "redirect:/entradaDiario";
    }
}
