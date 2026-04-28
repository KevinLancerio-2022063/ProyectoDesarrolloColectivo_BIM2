package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EjercicioService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RutinaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/ejercicios")
public class EjercicioController {
    private final EjercicioService ejercicioService;
    private final RutinaService rutinaService;

    public EjercicioController(EjercicioService ejercicioService, RutinaService rutinaService) {
        this.ejercicioService = ejercicioService;
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public String Listar(Model model){
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("ejerciciosFormu", new Ejercicio());
        model.addAttribute("rutina", rutinaService.getAListRutina());
        return "ejercicios";
    }

    @PostMapping("/guardarEjercicio")
    public String guardarEjercicio(@Valid @ModelAttribute("ejerciciosFormu") Ejercicio ejercicio, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            model.addAttribute("rutina", rutinaService.getAListRutina());
            return "ejercicios";
        }
        ejercicioService.saveEjercicio(ejercicio);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio fue añadido");
        return "redirect:/ejercicios";
    }

    @GetMapping("/editarEjercicio{id}")
    public String editarEjercicio(@PathVariable Integer id, Model model){
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("ejerciciosFormu", ejercicioService.getEjercicioById(id));
        model.addAttribute("rutina", rutinaService.getAListRutina());
        return "ejercicios";
    }

    @PostMapping("/eliminarEjercicio/{id}")
    public String eliminarEjercicio(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        ejercicioService.deleteEjercicio(id);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio fue eliminado");
        return "redirect:/ejercicios";
    }

    @GetMapping("/buscarEjercicio")
    public String buscarEjercicio(@RequestParam Integer id, Model model){
        Ejercicio ejercicio = ejercicioService.getEjercicioById(id);
        model.addAttribute("ejercicios", ejercicioService.getEjercicioById(id));
        model.addAttribute("ejerciciosFormu", ejercicio);
        model.addAttribute("rutina", rutinaService.getAListRutina());
        return "ejercicios";

    }

    @PostMapping("/actualizarEjercicio/{id}")
    public String actualizarEjercicio(@PathVariable Integer id, @Valid @ModelAttribute ("ejerciciosFormu") Ejercicio ejercicio, Model model, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            model.addAttribute("rutina", rutinaService.getAListRutina());
            return "ejercicios";
        }
        ejercicioService.updateEjercicio(id, ejercicio);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio se ha actualizado");
        return "redirect:/ejercicios";
    }

}
