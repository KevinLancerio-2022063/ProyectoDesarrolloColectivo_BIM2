package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ApoyoEmocionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ApoyoEmocionalViewController {

    @Autowired
    private ApoyoEmocionalService service;

    @GetMapping("/apoyoEmocional")
    public String mostrarApoyo(Model model){
        List<ApoyoEmocional> list = service.getAllApoyoEmovional();
        model.addAttribute("listaApoyos",list);
        return "ApoyoEmocional";
    }

    @GetMapping("/agregarApoyo")
    public String mostrarAgregar(){
        return "agregraApoyo";
    }

    @GetMapping("/detalleApoyo/{id}")
    public String detalle(@PathVariable("id") Integer id, Model model) {
        ApoyoEmocional apoyo = service.getById(id);
        model.addAttribute("apoyo", apoyo);
        return "detalleApoyo";
    }

    @GetMapping("/eliminar-apoyo/{id}")
    public String eliminarApoyo(@PathVariable int id){
        service.deleteApoyoEmocional(id);
        return "redirect:/apoyoEmocional";
    }

}
