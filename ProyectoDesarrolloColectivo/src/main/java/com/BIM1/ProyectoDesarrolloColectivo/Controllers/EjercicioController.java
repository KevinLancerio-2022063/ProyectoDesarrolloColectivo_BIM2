package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EjercicioService;
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

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public String Listar(Model model){
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("ejerciciosFormu", new Ejercicio());
        return "ejercicio";
    }

    @PostMapping("/guardarEjercicio")
    public String guardarEjercicio(@Valid @ModelAttribute("ejerciciosFormu") Ejercicio ejercicio, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            return "ejercicio";
        }
        ejercicioService.saveEjercicio(ejercicio);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio fue añadido");
        return "ejercicio";
    }

    @GetMapping("/editarEjercicio{id}")
    public String editarEjercicio(@PathVariable Integer id, Model model){
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("ejerciciosFormu", ejercicioService.getEjercicioById(id));
        return "ejercicio";
    }

    @PostMapping("/eliminarEjercicio/{id}")
    public String eliminarEjercicio(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        ejercicioService.deleteEjercicio(id);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio fue eliminado");
        return "redirect:/ejercicio";
    }

    @GetMapping("/buscarEjercicio")
    public String buscarEjercicio(@RequestParam Integer id, Model model){
        Ejercicio ejercicio = ejercicioService.getEjercicioById(id);
        model.addAttribute("ejercicios", ejercicioService.getEjercicioById(id));
        model.addAttribute("ejerciciosFormu", ejercicio);
        return "ejercicio";

    }

    @PostMapping("/actualizarEjercicio/{id}")
    public String actualizarEjercicio(@PathVariable Integer id, @Valid @ModelAttribute ("ejerciciosFormu") Ejercicio ejercicio, Model model, BindingResult result, RedirectAttributes redirectAttributes){

        if(result.hasErrors()){
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            return "ejercicio";
        }
        ejercicioService.updateEjercicio(id, ejercicio);
        redirectAttributes.addFlashAttribute("exito", "el ejercicio se ha actualizado");
        return "redirect:/ejercicio";
    }

}
