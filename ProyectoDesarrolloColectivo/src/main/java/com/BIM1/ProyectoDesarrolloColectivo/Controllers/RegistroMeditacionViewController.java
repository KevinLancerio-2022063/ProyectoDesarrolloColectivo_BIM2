package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroMeditacionService;
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

    public RegistroMeditacionViewController(RegistroMeditacionService registroMeditacionService) {
        this.registroMeditacionService = registroMeditacionService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
        model.addAttribute("registroMeditacionFormu", new RegistroMeditacion());
        return "registroMeditacion";
    }

    @PostMapping("/guardar")
    public String guardarRegistroMeditacion(
            @Valid @ModelAttribute("registroMeditacionFormu") RegistroMeditacion registroMeditacion,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            return "registroMeditacion";
        }

        try {
            registroMeditacionService.saveRegistroMeditacion(registroMeditacion);
            redirectAttributes.addFlashAttribute("exito", "El registro de meditación fue añadido correctamente");
        } catch (IllegalArgumentException e) {
            model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            model.addAttribute("error", e.getMessage());
            return "registroMeditacion";
        }

        return "redirect:/registroMeditacion";
    }

    @GetMapping("/editar/{id}")
    public String editarRegistroMeditacion(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            model.addAttribute("registroMeditacionFormu", registroMeditacionService.getRegistroMeditacionById(id));
            return "registroMeditacion";
        } catch (ObjectNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Registro de meditación no encontrado");
            return "redirect:/registroMeditacion";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarRegistroMeditacion(
            @PathVariable Integer id,
            @Valid @ModelAttribute("registroMeditacionFormu") RegistroMeditacion registroMeditacion,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            return "registroMeditacion";
        }

        try {
            registroMeditacionService.updateRegistroMeditacion(id, registroMeditacion);
            redirectAttributes.addFlashAttribute("exito", "Registro de meditación actualizado correctamente");
        } catch (IllegalArgumentException | ObjectNotFoundException e) {
            model.addAttribute("registroMeditacion", registroMeditacionService.getAListRegistroMeditacion());
            model.addAttribute("error", e.getMessage());
            return "registroMeditacion";
        }

        return "redirect:/registroMeditacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarRegistroMeditacion(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            registroMeditacionService.deleteRegistroMeditacion(id);
            redirectAttributes.addFlashAttribute("exito", "Registro de meditación eliminado correctamente");
        } catch (ObjectNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Registro de meditación no encontrado");
        }

        return "redirect:/registroMeditacion";
    }
}