package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EjercicioService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RutinaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ejercicios")
public class EjercicioController {
    private final EjercicioService ejercicioService;
    private final RutinaService rutinaService;

    public EjercicioController(EjercicioService ejercicioService, RutinaService rutinaService) {
        this.ejercicioService = ejercicioService;
        this.rutinaService = rutinaService;
    }


    private List<Integer> rutinaIdsDelUsuario(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return List.of();
        return rutinaService.getRutinasByUsuario(usuario.getId_usuario()).stream().map(Rutina::getId_rutina).collect(Collectors.toList());
    }


    private List<Rutina> rutinasDelUsuario(HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) return List.of();
        return rutinaService.getRutinasByUsuario(usuario.getId_usuario());
    }

    private void addCommonAttributes(Model model, HttpSession session) {
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaIdsUsuario", rutinaIdsDelUsuario(session));
        model.addAttribute("rutinaUsuario", rutinasDelUsuario(session));
    }

    @GetMapping
    public String Listar(Model model, HttpSession session) {
        addCommonAttributes(model, session);
        model.addAttribute("ejerciciosFormu", new Ejercicio());
        return "ejercicios";
    }

    @PostMapping("/guardarEjercicio")
    public String guardarEjercicio(@Valid @ModelAttribute("ejerciciosFormu") Ejercicio ejercicio, BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {
        if (result.hasErrors()) {
            addCommonAttributes(model, session);
            return "ejercicios";
        }
        ejercicioService.saveEjercicio(ejercicio);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio fue añadido");
        return "redirect:/ejercicios";
    }

    @GetMapping("/editarEjercicio/{id}")
    public String editarEjercicio(@PathVariable Integer id, Model model, HttpSession session) {
        addCommonAttributes(model, session);
        model.addAttribute("ejerciciosFormu", ejercicioService.getEjercicioById(id));
        return "ejercicios";
    }

    @PostMapping("/eliminarEjercicio/{id}")
    public String eliminarEjercicio(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        ejercicioService.deleteEjercicio(id);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio fue eliminado");
        return "redirect:/ejercicios";
    }

    @GetMapping("/buscarEjercicio")
    public String buscarEjercicio(@RequestParam Integer id, Model model, HttpSession session) {
        Ejercicio ejercicio = ejercicioService.getEjercicioById(id);
        model.addAttribute("ejercicios", ejercicio);
        model.addAttribute("ejerciciosFormu", ejercicio);
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaIdsUsuario", rutinaIdsDelUsuario(session));
        model.addAttribute("rutinaUsuario", rutinasDelUsuario(session));
        return "ejercicios";
    }

    @PostMapping("/actualizarEjercicio/{id}")
    public String actualizarEjercicio(@PathVariable Integer id, @Valid @ModelAttribute("ejerciciosFormu") Ejercicio ejercicio, Model model, BindingResult result, RedirectAttributes redirectAttributes, HttpSession session) {
        if (result.hasErrors()) {
            addCommonAttributes(model, session);
            return "ejercicios";
        }
        ejercicioService.updateEjercicio(id, ejercicio);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio se ha actualizado");
        return "redirect:/ejercicios";
    }
}