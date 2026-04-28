package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Service.FraseMotivadoraService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivosService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;

@Controller
public class ObjetivosViewController {
    @Autowired
    private ObjetivosService service;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FraseMotivadoraService frasesMotivadorasService;

    @GetMapping("/objetivos")
    public String mostrarObjetivos(Model model){
        model.addAttribute("listaObjetivos",service.getAllObjetivos());
        return "Objetivos";
    }

    @GetMapping("/detalleObjetivos/{id}")
    public String detalle(@PathVariable("id") Integer id, Model model) {
        Objetivos objetivo = service.getById(id);
        model.addAttribute("objetivo", objetivo);
        System.out.println("ENTRÓ AL CONTROLLER DETALLE OBJETIVO");
        return "detalleObjetivo";
    }

    @GetMapping("/agregarObjetivo")
    public String agregarObjetivo(Model model) {
        model.addAttribute("objetivo", new Objetivos());
        model.addAttribute("usuario",usuarioService.getAllUsuarios());
        model.addAttribute("frase",frasesMotivadorasService.getAllFraseMotivadora());
        return "agregarObjetivo";
    }

    @PostMapping("/guardarObjetivoCreado")
    public String guardarObjetivoCreado(@ModelAttribute Objetivos objetivo) {
        service.saveObjetivos(objetivo);
        return "redirect:/objetivos";
    }

    @GetMapping("/editarObjetivo/{id}")
    public String editarObjetivo(@PathVariable int id, Model model) {
        Objetivos objetivo = service.getById(id);
        model.addAttribute("objetivo", objetivo);
        model.addAttribute("usuario",usuarioService.getAllUsuarios());
        model.addAttribute("frase",frasesMotivadorasService.getAllFraseMotivadora());
        return "editarObjetivo";
    }

    @PostMapping("/guardarObjetivo")
    public String guardarObjetivo(@ModelAttribute  Objetivos objetivo) {
        Objetivos original = service.getById(objetivo.getIdObjetivos());
        original.setTituloObjetivo(objetivo.getTituloObjetivo());
        original.setDescripcionObjetivo(objetivo.getDescripcionObjetivo());
        original.setEstadoObjetivo(objetivo.getEstadoObjetivo());
        original.setFechaObjetivo(objetivo.getFechaObjetivo());
        original.setUsuario(objetivo.getUsuario());
        original.setFraseMotivadora(objetivo.getFraseMotivadora());
        service.updateObjetivos(objetivo.getIdObjetivos(), original);
        return "redirect:/objetivos";
    }

    @GetMapping("/eliminar-objetivo/{id}")
    public String eliminarObjetivo(@PathVariable int id){
        service.deleteObjetivos(id);
        return "redirect:/objetivos";
    }
}
