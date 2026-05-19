package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroMeditacionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registroMeditacion")
public class RegistroMeditacionViewController {

    private final RegistroMeditacionService registroMeditacionService;

    public RegistroMeditacionViewController(
            RegistroMeditacionService registroMeditacionService
    ) {
        this.registroMeditacionService = registroMeditacionService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // El Admin puede ver todos los registros
        if (rol.equals("ADMIN")) {

            model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
        } else {

            // El usuario solo puede ver sus registros
            model.addAttribute("registroMeditacion", registroMeditacionService.getRegistroMeditacionByUsuario(usuarioId));
        }

        model.addAttribute("registroMeditacionFormu", new RegistroMeditacion());

        return "registroMeditacion";
    }

    @PostMapping("/guardar")
    public String guardarRegistroMeditacion(
            @Valid @ModelAttribute("registroMeditacionFormu")
            RegistroMeditacion registroMeditacion,

            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if (rol.equals("USER")) {
            registroMeditacion.setFkIdUsuario(usuarioId);
        }

        if (result.hasErrors()) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            } else {

                model.addAttribute("registroMeditacion", registroMeditacionService.getRegistroMeditacionByUsuario(usuarioId));
            }

            return "registroMeditacion";
        }

        try {

            registroMeditacionService.saveRegistroMeditacion(
                    registroMeditacion
            );

            redirectAttributes.addFlashAttribute("exito", "El registro de meditación fue añadido correctamente");

        } catch (IllegalArgumentException e) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            } else {

                model.addAttribute("registroMeditacion", registroMeditacionService.getRegistroMeditacionByUsuario(usuarioId));
            }

            model.addAttribute("error", e.getMessage());

            return "registroMeditacion";
        }

        return "redirect:/registroMeditacion";
    }

    @GetMapping("/editar/{id}")
    public String editarRegistroMeditacion(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            RegistroMeditacion meditacion = registroMeditacionService.getRegistroMeditacionById(id);

            if (rol.equals("USER") && !meditacion.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para editar este registro");
                return "redirect:/registroMeditacion";
            }

            if (rol.equals("ADMIN")) {

                model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            } else {

                model.addAttribute("registroMeditacion", registroMeditacionService.getRegistroMeditacionByUsuario(usuarioId));
            }

            model.addAttribute("registroMeditacionFormu", meditacion);
            return "registroMeditacion";

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Registro de meditación no encontrado");
            return "redirect:/registroMeditacion";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarRegistroMeditacion(
            @PathVariable Integer id,

            @Valid @ModelAttribute("registroMeditacionFormu")
            RegistroMeditacion registroMeditacion,

            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            RegistroMeditacion existente = registroMeditacionService.getRegistroMeditacionById(id);

            if (rol.equals("USER") && !existente.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para actualizar este registro");
                return "redirect:/registroMeditacion";
            }

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Registro de meditación no encontrado");
            return "redirect:/registroMeditacion";
        }

        if (rol.equals("USER")) {
            registroMeditacion.setFkIdUsuario(usuarioId);
        }

        if (result.hasErrors()) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            } else {

                model.addAttribute("registroMeditacion", registroMeditacionService.getRegistroMeditacionByUsuario(usuarioId));
            }

            return "registroMeditacion";
        }

        try {

            registroMeditacionService.updateRegistroMeditacion(id, registroMeditacion);

            redirectAttributes.addFlashAttribute("exito", "Registro de meditación actualizado correctamente");

        } catch (IllegalArgumentException | ObjectNotFoundException e) {

            if (rol.equals("ADMIN")) {

                model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            } else {

                model.addAttribute("registroMeditacion", registroMeditacionService.getRegistroMeditacionByUsuario(usuarioId));
            }

            model.addAttribute("error", e.getMessage());

            return "registroMeditacion";
        }

        return "redirect:/registroMeditacion";
    }

    @GetMapping("/confirmarEliminar/{id}")
    public String confirmarEliminar(
            @PathVariable Integer id,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            RegistroMeditacion meditacion =
                    registroMeditacionService.getRegistroMeditacionById(id);

            if (rol.equals("USER") && !meditacion.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar este registro");
                return "redirect:/registroMeditacion";
            }

            model.addAttribute("meditacion", meditacion);
            return "registroMeditacionEliminar";

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Registro de meditación no encontrado");
            return "redirect:/registroMeditacion";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarRegistroMeditacion(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {

            RegistroMeditacion meditacion = registroMeditacionService.getRegistroMeditacionById(id);

            if (rol.equals("USER") &&
                    !meditacion.getFkIdUsuario().equals(usuarioId)) {

                redirectAttributes.addFlashAttribute("error", "No tienes permiso para eliminar este registro");
                return "redirect:/registroMeditacion";
            }

            registroMeditacionService.deleteRegistroMeditacion(id);

            redirectAttributes.addFlashAttribute("exito", "Registro de meditación eliminado correctamente");

        } catch (ObjectNotFoundException e) {

            redirectAttributes.addFlashAttribute("error", "Registro de meditación no encontrado");
        }

        return "redirect:/registroMeditacion";
    }
}