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

}
