package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
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

    @GetMapping("/agregarFrase")
    public String agregarFraseMotivadora(Model model) {
        model.addAttribute("frase", new FraseMotivadora());
        return "agregarFrase";
    }

    @PostMapping("/guardarFraseCreada")
    public String guardarFraseCreada(@ModelAttribute FraseMotivadora frase) {
        fraseMotivadoraService.saveFraseMotivadora(frase);
        return "redirect:/frasesMotivadoras";
    }

    @GetMapping("/editarFrase/{id}")
    public String editarFraseMotivadora(@PathVariable int id, Model model) {
        FraseMotivadora frase = fraseMotivadoraService.getById(id);
        model.addAttribute("frase", frase);
        return "editarFrase";
    }

    @PostMapping("/guardarFrase")
    public String guardarFrase(@ModelAttribute FraseMotivadora frase) {
        FraseMotivadora original = fraseMotivadoraService.getById(frase.getIdFraseMotivadora());
        original.setTexto(frase.getTexto());
        original.setAutor(frase.getAutor());
        fraseMotivadoraService.updateFraseMotivadora(frase.getIdFraseMotivadora(), original);
        return "redirect:/frasesMotivadoras";
    }

    @GetMapping("/eliminar-frase/{id}")
    public String eliminarFraseMotivador(@PathVariable int id){
        fraseMotivadoraService.deleteFraseMotivadora(id);
        return "redirect:/frasesMotivadoras";
    }
}
