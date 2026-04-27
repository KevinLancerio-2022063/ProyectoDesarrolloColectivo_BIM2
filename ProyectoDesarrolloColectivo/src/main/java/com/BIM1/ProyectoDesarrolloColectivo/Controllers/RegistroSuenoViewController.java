package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroSuenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registroSueno")
public class RegistroSuenoViewController {

    @Autowired
    private RegistroSuenoService regService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("registro", new RegistroSueno());
        model.addAttribute("registros", regService.getAllRegistrosSuenos());
        return "registroSueno";
    }

    @PostMapping("/guardar")
    public String guardar(RegistroSueno registro) {
        regService.saveRegistroSueno(registro);
        return "redirect:/registroSueno";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("registro", regService.getRegistrosSuenosById(id));
        model.addAttribute("registros", regService.getAllRegistrosSuenos());
        return "registroSueno";
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        regService.deleteRegistroSueno(id);
        return "redirect:/registroSueno";
    }

}
