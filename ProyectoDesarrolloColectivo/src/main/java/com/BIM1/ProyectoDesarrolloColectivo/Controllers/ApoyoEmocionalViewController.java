package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ApoyoEmocionalService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/apoyoEmocional")
    public String mostrarApoyo(Model model, HttpSession session){

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // ===== ADMIN =====
        if (rol.equals("ADMIN")) {

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

            model.addAttribute("listaApoyos", list);
            model.addAttribute("data", List.of(bien, maso, mal));
            model.addAttribute("labels", List.of("Bien", "Mas o menos", "Mal"));
            model.addAttribute("porcentajes", List.of(pBien, pMaso, pMal));
            model.addAttribute("categorias", categorias);
            model.addAttribute("countCat", countCat);
            model.addAttribute("maxCat", maxCat);

            return "ApoyoEmocionalAdmin";
        }

        // ===== USER =====
        List<ApoyoEmocional> list = service.getByIdUsuario(usuarioId);

        model.addAttribute("listaApoyos", list);

        return "ApoyoEmocional";
    }

    @GetMapping("/detalleApoyo/{id}")
    public String detalle(@PathVariable("id") Integer id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        ApoyoEmocional apoyo = service.getById(id);
        model.addAttribute("apoyo", apoyo);
        return "detalleApoyo";
    }

    @GetMapping("/eliminar-apoyo/{id}")
    public String eliminarApoyo(@PathVariable int id, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        service.deleteApoyoEmocional(id);
        return "redirect:/apoyoEmocional";
    }

    @GetMapping("/agregarApoyo")
    public String agregarApoyoEmocional(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("apoyo",new ApoyoEmocional());
        return "agregarApoyo";
    }

    @PostMapping("/guardarApoyoCreado")
    public String guardarApoyoCreado(@ModelAttribute ApoyoEmocional apoyoEmocional, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {
            Integer id_usuario = (Integer) session.getAttribute("usuarioId");

            Usuario usuario = new Usuario();
            usuario.setId_usuario(id_usuario);
            apoyoEmocional.setUsuario(usuario);

            service.saveApoyoEmocional(apoyoEmocional);
            return "redirect:/apoyoEmocional";
        } catch (CustomException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/agregarApoyo";
        }
    }

    @GetMapping("/editarApoyo/{id}")
    public String formularioEditar(@PathVariable Integer id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        ApoyoEmocional apoyo = service.getById(id);
        model.addAttribute("apoyo", apoyo);
        return "editarApoyo";
    }

    @PostMapping("/guardarApoyo")
    public String guardarApoyo(@ModelAttribute ApoyoEmocional apoyoEmocional, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {
            Integer id_usuario = (Integer) session.getAttribute("usuarioId");
            Usuario usuario = new Usuario();
            usuario.setId_usuario(id_usuario);

            ApoyoEmocional original = service.getById(apoyoEmocional.getIdApoyoEmocional());
            original.setTitulo(apoyoEmocional.getTitulo());
            original.setContenido(apoyoEmocional.getContenido());
            original.setCategoria(apoyoEmocional.getCategoria());
            original.setNivelAnimo(apoyoEmocional.getNivelAnimo());
            original.setUsuario(usuario); // ← usuario de sesión, no del form

            service.updateApoyoEmocional(original.getIdApoyoEmocional(), original); // ← pasar original, no apoyoEmocional
            return "redirect:/detalleApoyo/" + original.getIdApoyoEmocional();
        } catch (CustomException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editarApoyo/" + apoyoEmocional.getIdApoyoEmocional();
        }
    }

    @GetMapping("/apoyos")
    public String listarApoyos(@RequestParam(required = false) Integer id, Model model, HttpSession session) {

        List<ApoyoEmocional> listaApoyos;
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {
            if (id != null) {
                ApoyoEmocional apoyo = service.getById(id);

                // Verifica que el apoyo pertenezca al usuario en sesión
                // Si es ADMIN puede ver cualquiera, si es USER solo los suyos
                if ("ADMIN".equals(rol) || apoyo.getUsuario().getId_usuario().equals(usuarioId)) {
                    listaApoyos = List.of(apoyo);
                } else {
                    // El apoyo no le pertenece, se muestra lista vacía con mensaje
                    model.addAttribute("error", "No tienes permiso para ver este registro");
                    model.addAttribute("listaApoyos", List.of());
                    return "ApoyoEmocional";
                }

            } else {
                // Sin id: ADMIN ve todos, USER solo los suyos
                if ("ADMIN".equals(rol)) {
                    listaApoyos = service.getAllApoyoEmovional();
                } else {
                    listaApoyos = service.getByIdUsuario(usuarioId);
                }
            }

            model.addAttribute("listaApoyos", listaApoyos);

        } catch (CustomException e) {
            System.out.println("ERROR CAPTURADO: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaApoyos", List.of());
        }

        return "ApoyoEmocional";
    }

    @GetMapping("/apoyoEmocional-Admin")
    public String mostrarApoyoAdmin(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

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
