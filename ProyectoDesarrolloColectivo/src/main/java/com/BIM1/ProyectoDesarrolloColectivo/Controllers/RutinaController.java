package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RutinaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/rutina")
public class RutinaController {
    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", new Rutina());
        return "rutina";
    }

    @GetMapping("/editarRutina/{id}")
    public String editarRutina(@PathVariable Integer id, Model model){
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", rutinaService.getRutinaById(id));
        return "rutina";
    }

    @GetMapping("/buscarRutina")
    public String buscarRutina(@RequestParam Integer id, Model model){
        Rutina rutina = rutinaService.getRutinaById(id);
        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", rutina);
        return "rutina";
    }

     @PostMapping("/actualizarRutina/{id}")
    public String actualizarRutina(@PathVariable Integer id, @Valid @ModelAttribute("rutinaFormu")Rutina rutina, Model model, BindingResult result, RedirectAttributes redirectAttributes){
        if(result.hasErrors()){
            model.addAttribute("rutina", rutinaService.getAListRutina());
            return "rutina";
        }
        rutinaService.updateRutina(id, rutina);
        redirectAttributes.addFlashAttribute("exito", "la rutina se ha actualizado");
        return "redirect:/rutina";
     }

}
