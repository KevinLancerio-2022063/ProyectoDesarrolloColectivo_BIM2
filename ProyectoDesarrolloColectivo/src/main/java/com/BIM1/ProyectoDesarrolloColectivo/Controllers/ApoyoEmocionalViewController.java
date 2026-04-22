package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ApoyoEmocionalService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ApoyoEmocionalViewController {

    @Autowired
    private ApoyoEmocionalService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/apoyoEmocional")
    public String mostrarApoyo(Model model){
        List<ApoyoEmocional> list = service.getAllApoyoEmovional();
        model.addAttribute("listaApoyos",list);
        return "ApoyoEmocional";
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

    @GetMapping("/agregarApoyo")
    public String agregarApoyoEmocional(Model model){
        model.addAttribute("apoyo",new ApoyoEmocional());
        model.addAttribute("usuario",usuarioService.getAllUsuarios());
        return "agregarApoyo";
    }

    @PostMapping("/guardarApoyoCreado")
    public String guardarApoyoCreado(@ModelAttribute ApoyoEmocional apoyoEmocional){
        service.saveApoyoEmocional(apoyoEmocional);
        return "redirect:/apoyoEmocional";
    }

    @GetMapping("/editarApoyo/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        ApoyoEmocional apoyo = service.getById(id);
        model.addAttribute("apoyo", apoyo);
        return "editarCliente";
    }

    @PutMapping("/guardarcliente")
    public String guardarCliente(@ModelAttribute ApoyoEmocional apoyoEmocional) {

        System.out.println("ENTRO A ACTUALIZAR");
        System.out.println(apoyoEmocional.getIdApoyoEmocional());

        service.updateApoyoEmocional(apoyoEmocional.getIdApoyoEmocional(),apoyoEmocional);

        return "redirect:/cliente";
    }
}
