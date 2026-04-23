package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.BIM1.ProyectoDesarrolloColectivo.Service.FraseMotivadoraService;

@Controller
public class FraseMotivadoraViewController {
    @Autowired
    private FraseMotivadoraService fraseMotivadoraService;

    @GetMapping("/frasesMotivadoras")
    public String mostrarFraseMotivadora(org.springframework.ui.Model model) {
        model.addAttribute("frases", fraseMotivadoraService.getAllFraseMotivadora());
        return "FraseMotivadora";
    }

    @GetMapping("/eliminar-frase/{id}")
    public String eliminarFraseMotivador(@PathVariable int id){
        fraseMotivadoraService.deleteFraseMotivadora(id);
        return "redirect:/frasesMotivadoras";
    }
}
