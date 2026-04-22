package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EntradaDiarioService;
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
    public String listar(Model model) {
        model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
        model.addAttribute("entradaDiarioFormu", new EntradaDiario());
        return "entradaDiario";
    }

    @PostMapping("/guardar")
    public String guardarEntradaDiario(
            @Valid @ModelAttribute("entradaDiarioFormu") EntradaDiario entradaDiario,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            return "entradaDiario";
        }

        try {
            entradaDiarioService.saveEntradaDiario(entradaDiario);
            redirectAttributes.addFlashAttribute("exito", "La entrada de diario fue añadida correctamente");
        } catch (IllegalArgumentException e) {
            model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            model.addAttribute("error", e.getMessage());
            return "entradaDiario";
        }

        return "redirect:/entradaDiario";
    }

    @GetMapping("/editar/{id}")
    public String editarEntradaDiario(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            model.addAttribute("entradaDiarioFormu", entradaDiarioService.getEntradaDiarioById(id));
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
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            return "entradaDiario";
        }

        try {
            entradaDiarioService.updateEntradaDiario(id, entradaDiario);
            redirectAttributes.addFlashAttribute("exito", "Entrada de diario actualizada correctamente");
        } catch (IllegalArgumentException | ObjectNotFoundException e) {
            model.addAttribute("entradaDiario", entradaDiarioService.getAListEntradaDiario());
            model.addAttribute("error", e.getMessage());
            return "entradaDiario";
        }

        return "redirect:/entradaDiario";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEntradaDiario(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            entradaDiarioService.deleteEntradaDiario(id);
            redirectAttributes.addFlashAttribute("exito", "Entrada de diario eliminada correctamente");
        } catch (ObjectNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "Entrada de diario no encontrada");
        }

        return "redirect:/entradaDiario";
    }
}