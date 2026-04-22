package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivoMeditacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/objetivoMeditacion")
public class ObjetivoMeditacionViewController {

    @Autowired
    private ObjetivoMeditacionService objService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("objetivo", new ObjetivoMeditacion());
        model.addAttribute("objetivos", objService.getAllObjetivosMeditacion());
        return "objetivoMeditacion";
    }

    @PostMapping("/guardar")
    public String guardar(ObjetivoMeditacion objetivo) {
        objService.saveObjetivoMeditacion(objetivo);
        return "redirect:/objetivoMeditacion";
    }

    @GetMapping("/editar/{id}")
        public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("objetivo", objService.getObjetivosMeditacionById(id));
        model.addAttribute("objetivos", objService.getAllObjetivosMeditacion());
        return "objetivoMeditacion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        objService.deleteObjetivoMeditacion(id);
        return "redirect:/objetivoMeditacion";
    }


}
