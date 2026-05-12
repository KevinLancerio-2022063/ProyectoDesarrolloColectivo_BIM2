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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        model.addAttribute("listaUsuarios",usuarioService.getAllUsuarios());
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
        model.addAttribute("listaUsuarios",usuarioService.getAllUsuarios());
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

    @GetMapping("/apoyoEmocional-Admin")
    public String mostrarApoyoAdmin(Model model){
        List<ApoyoEmocional> list = service.getAllApoyoEmovional();

        int mal = 0;
        int maso = 0;
        int bien = 0;

        Map<String, Integer> conteoCategorias = new HashMap<>();

        for (ApoyoEmocional ap : list) {
            String cat = ap.getCategoria();

            if (cat != null) {
                conteoCategorias.put(cat, conteoCategorias.getOrDefault(cat, 0) + 1);
            }
        }

        List<String> categorias = new ArrayList<>(conteoCategorias.keySet());
        List<Integer> countCat = categorias.stream()
                .map(conteoCategorias::get)
                .toList();

        int maxCat = countCat.stream().max(Integer::compare).orElse(1);

        for (ApoyoEmocional ap : list){
            String nivelAnimo = ap.getNivelAnimo();
            if (nivelAnimo.equalsIgnoreCase("mal")){
                mal++;
            } else if (nivelAnimo.equalsIgnoreCase("mas o menos")){
                maso++;
            } else if (nivelAnimo.equalsIgnoreCase("bien")){
                bien++;
            }
            
        }

        int total = bien + maso + mal;

        int pBien = total > 0 ? (bien * 100) / total : 0;
        int pMaso = total > 0 ? (maso * 100) / total : 0;
        int pMal = total > 0 ? (mal * 100) / total : 0;

        model.addAttribute("listaApoyos",list);
        model.addAttribute("data", List.of(bien, maso, mal));
        model.addAttribute("labels", List.of("Bien", "Mas o menos", "Mal"));
        model.addAttribute("porcentajes", List.of(pBien, pMaso, pMal));
        model.addAttribute("categorias", categorias);
        model.addAttribute("countCat", countCat);
        model.addAttribute("maxCat", maxCat);
        return "ApoyoEmocionalAdmin";
    }
}
