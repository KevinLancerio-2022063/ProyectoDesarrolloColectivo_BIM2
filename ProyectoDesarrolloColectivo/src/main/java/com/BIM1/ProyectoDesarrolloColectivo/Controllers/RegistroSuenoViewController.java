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
@RequestMapping("/registroSuenos")
public class RegistroSuenoViewController {

    @Autowired
    private RegistroSuenoService regService;

    @GetMapping
    public String listarReg(Model model) {
        model.addAttribute("registro", new RegistroSueno());
        model.addAttribute("registros", regService.getAllRegistrosSuenos());
        return "registroSuenos";
    }

    @PostMapping("/guardarReg")
    public String guardarReg(RegistroSueno registro) {
        regService.saveRegistroSueno(registro);
        return "redirect:/registroSuenos";
    }

    @GetMapping("/editarReg/{id}")
    public String editarReg(@PathVariable Integer id, Model model) {
        model.addAttribute("registro", regService.getRegistrosSuenosById(id));
        model.addAttribute("registros", regService.getAllRegistrosSuenos());
        return "registroSuenos";
    }
    
    @GetMapping("/eliminarReg/{id}")
    public String eliminarReg(@PathVariable Integer id) {
        regService.deleteRegistroSueno(id);
        return "redirect:/registroSuenos";
    }

}
