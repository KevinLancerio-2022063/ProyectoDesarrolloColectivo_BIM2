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
public class EjercicioViewController {

    private final EjercicioService ejercicioService;

    public EjercicioViewController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public String Listar(Model model){
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("ejerciciosFormu", new Ejercicio());
        return "clientes";
    }

    @PostMapping("/guardarEjercicio")
    public String guardarEjercicio(@Valid @ModelAttribute("ejerciciosFormu") Ejercicio ejercicio, BindingResult result, RedirectAttributes redirectAttributes, Model model){
       if(result.hasErrors()){
           model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
           return "ejercicios";
       }
       ejercicioService.saveEjercicio(ejercicio);
       redirectAttributes.addFlashAttribute("exito", "el ejercicio fue añadido");
       return "ejercicios";
    }

    @GetMapping("/editarEjercicio")
    public String editarEjercicio(@PathVariable Integer id, Model model){
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("ejerciciosFormu", ejercicioService.getEjercicioById(id));
        return "ejercicios";
    }
}
