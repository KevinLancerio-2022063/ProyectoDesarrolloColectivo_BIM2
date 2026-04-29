package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ApoyoEmocionalService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
    public String guardarApoyoCreado(@ModelAttribute ApoyoEmocional apoyoEmocional, RedirectAttributes redirectAttributes){
        try {
            service.saveApoyoEmocional(apoyoEmocional);
            return "redirect:/apoyoEmocional";
        } catch (CustomException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/agregarApoyo";
        }
    }

    @GetMapping("/editarApoyo/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model) {
        ApoyoEmocional apoyo = service.getById(id);
        model.addAttribute("apoyo", apoyo);
        model.addAttribute("usuario",usuarioService.getAllUsuarios());
        return "editarApoyo";
    }

    @PostMapping("/guardarApoyo")
    public String guardarApoyo(@ModelAttribute ApoyoEmocional apoyoEmocional, RedirectAttributes redirectAttributes) {
        try {
            ApoyoEmocional original = service.getById(apoyoEmocional.getIdApoyoEmocional());
            original.setTitulo(apoyoEmocional.getTitulo());
            original.setContenido(apoyoEmocional.getContenido());
            original.setCategoria(apoyoEmocional.getCategoria());
            original.setNivelAnimo(apoyoEmocional.getNivelAnimo());
            original.setUsuario(apoyoEmocional.getUsuario());
            service.updateApoyoEmocional(original.getIdApoyoEmocional(), apoyoEmocional);
            return "redirect:/detalleApoyo/" + apoyoEmocional.getIdApoyoEmocional();
        } catch (CustomException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editarApoyo/" + apoyoEmocional.getIdApoyoEmocional();
        }
    }

    @GetMapping("/apoyos")
    public String listarApoyos(@RequestParam(required = false) Integer id, Model model) {
        List<ApoyoEmocional> listaApoyos;

        try {
            if (id != null) {
                ApoyoEmocional apoyo = service.getById(id); // 🔥 aquí ya puede lanzar excepción
                listaApoyos = List.of(apoyo);
            } else {
                listaApoyos = service.getAllApoyoEmovional();
            }

            model.addAttribute("listaApoyos", listaApoyos);

        } catch (CustomException e) {
        System.out.println("🔥 ERROR CAPTURADO: " + e.getMessage());
        model.addAttribute("error", e.getMessage());
        model.addAttribute("listaApoyos", List.of()); // ← sin llamar al service
        }

        return "ApoyoEmocional";
    }

}
