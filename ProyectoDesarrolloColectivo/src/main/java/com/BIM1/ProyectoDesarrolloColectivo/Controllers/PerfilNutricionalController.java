package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.PerfilNutricional;
import com.BIM1.ProyectoDesarrolloColectivo.Service.PerfilNutricionalService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/perfilNutricional")
public class PerfilNutricionalController {
    private final PerfilNutricionalService perfilNutricionalService;

    public PerfilNutricionalController(PerfilNutricionalService perfilNutricionalService) {
        this.perfilNutricionalService = perfilNutricionalService;
    }

    @GetMapping
    public String listarPerfil(Model model){
        model.addAttribute("perfilNutricional", perfilNutricionalService.getAListPerfilNuticional());
        model.addAttribute("perfilNutricionalFormu", new PerfilNutricional());
        return "perfilNutricional";
    }

    @GetMapping("/editarPerfilNutricional/{id}")
    public String editarPerfil(@PathVariable Integer id, Model model){
        model.addAttribute("perfilNutricional", perfilNutricionalService.getAListPerfilNuticional());
        model.addAttribute("perfilNutricional", perfilNutricionalService.getPerfilNutricionalById(id));
        return "perfilNutricional";
    }

    @GetMapping("/buscarPerfilNutricional")
    public String buscarPerfil(@RequestParam Integer id, Model model){
        PerfilNutricional perfilNutricional = perfilNutricionalService.getPerfilNutricionalById(id);
        model.addAttribute("perfilNutricional", perfilNutricionalService.getAListPerfilNuticional());
        model.addAttribute("perfilNutricionalFormu", perfilNutricional);
        return "perfilNutricional";
    }

    @PostMapping("/guardarPerfilNutricional")
    public String guardarPerfil(@Valid @ModelAttribute("perfilNutricionalFormu") PerfilNutricional perfilNutricional, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("perfilNutricional", perfilNutricionalService.getAListPerfilNuticional());
            return "perfilNutricional";
        }
        perfilNutricionalService.savePerfilNutrcional(perfilNutricional);
        redirectAttributes.addFlashAttribute("exito", "el perfil nutricional ha sido guardado");
        return "redirect:/perfilNutricional";
    }

    @PostMapping("/eliminarPerfilNutricional/{id}")
    public String eliminarPerfil(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        perfilNutricionalService.deletePerfilNutricional(id);
        redirectAttributes.addFlashAttribute("exito", "el perfil se ha eliminado");
        return "perfilNutricional";
    }

    @PostMapping("/actualizarPerfilNutricional/{id}")
    public String actualizarPerfil(@PathVariable Integer id, @Valid @ModelAttribute("perfilNutricionalFormu") PerfilNutricional perfilNutricional, Model model, RedirectAttributes redirectAttributes, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute("perfilNutricional", perfilNutricionalService.getAListPerfilNuticional());
            return "perfilNutricional";
        }
        perfilNutricionalService.updatePerfilNutricional(id, perfilNutricional);
        redirectAttributes.addFlashAttribute("exito", "se ha actualizado el perfilNutricional");
        return "redirect:/perfilNutricional";
    }

}
