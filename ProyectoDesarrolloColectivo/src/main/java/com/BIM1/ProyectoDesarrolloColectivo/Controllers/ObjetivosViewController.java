package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.FraseMotivadoraService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivosService;

import java.util.List;

@Controller
public class ObjetivosViewController {
    @Autowired
    private ObjetivosService service;


    @Autowired
    private FraseMotivadoraService frasesMotivadorasService;

    @GetMapping("/objetivos")
    public String mostrarObjetivos(Model model, HttpSession session){

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        // ===== ADMIN =====
        if (rol.equals("ADMIN")) {

            List<Objetivos> lista = service.getAllObjetivos();

            int completados = 0;
            int pendientes = 0;
            int enProgreso = 0;

            for (Objetivos obj : lista) {

                String estado = obj.getEstadoObjetivo();

                if (estado.equalsIgnoreCase("completado")) {
                    completados++;
                } else if (estado.equalsIgnoreCase("pendiente")) {
                    pendientes++;
                } else if (estado.equalsIgnoreCase("en progreso")) {
                    enProgreso++;
                }
            }

            int total = completados + pendientes + enProgreso;

            int pCompletados = total > 0 ? (completados * 100) / total : 0;
            int pPendientes = total > 0 ? (pendientes * 100) / total : 0;
            int pEnProgreso = total > 0 ? (enProgreso * 100) / total : 0;

            model.addAttribute("listaObjetivos", lista);
            model.addAttribute("labels", List.of("Completados", "Pendientes", "En Progreso"));
            model.addAttribute("data", List.of(completados, pendientes, enProgreso));
            model.addAttribute("porcentajes", List.of(pCompletados, pPendientes, pEnProgreso));

            return "ObjetivosAdmin";
        }

        // ===== USER =====
        model.addAttribute("listaObjetivos", service.getByIdUsuario(usuarioId));

        return "Objetivos";
    }


    @GetMapping("/detalleObjetivos/{id}")
    public String detalle(@PathVariable("id") Integer id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        Objetivos objetivo = service.getById(id);
        model.addAttribute("objetivo", objetivo);
        System.out.println("ENTRÓ AL CONTROLLER DETALLE OBJETIVO");
        return "detalleObjetivo";
    }

    @GetMapping("/agregarObjetivo")
    public String agregarObjetivo(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("objetivo", new Objetivos());
        model.addAttribute("frase",frasesMotivadorasService.getAllFraseMotivadora());
        return "agregarObjetivo";
    }

    @PostMapping("/guardarObjetivoCreado")
    public String guardarObjetivoCreado(@ModelAttribute Objetivos objetivo, RedirectAttributes redirectAttributes, HttpSession session) {

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
            objetivo.setUsuario(usuario);
            service.saveObjetivos(objetivo);
            return "redirect:/objetivos";
        } catch (CustomException e) {
            System.out.println(" ERROR CAPTURADO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/agregarObjetivo";
        }
    }

    @GetMapping("/editarObjetivo/{id}")
    public String editarObjetivo(@PathVariable int id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        Objetivos objetivo = service.getById(id);
        model.addAttribute("objetivo", objetivo);
        model.addAttribute("frase",frasesMotivadorasService.getAllFraseMotivadora());
        return "editarObjetivo";
    }

    @PostMapping("/guardarObjetivo")
    public String guardarObjetivo(@ModelAttribute  Objetivos objetivo, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try{
            Integer id_usuario = (Integer) session.getAttribute("usuarioId");
            Usuario usuario = new Usuario();
            usuario.setId_usuario(id_usuario);

            Objetivos original = service.getById(objetivo.getIdObjetivos());
            original.setTituloObjetivo(objetivo.getTituloObjetivo());
            original.setDescripcionObjetivo(objetivo.getDescripcionObjetivo());
            original.setEstadoObjetivo(objetivo.getEstadoObjetivo());
            original.setFechaObjetivo(objetivo.getFechaObjetivo());
            original.setFraseMotivadora(objetivo.getFraseMotivadora());
            original.setUsuario(usuario); // ← usuario de sesión, no del form
            service.updateObjetivos(objetivo.getIdObjetivos(), original);
            return "redirect:/detalleObjetivos/" + objetivo.getIdObjetivos();
        } catch (CustomException e) {
            System.out.println(" ERROR CAPTURADO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editarObjetivo/" + objetivo.getIdObjetivos();
        }
    }

    @GetMapping("/eliminar-objetivo/{id}")
    public String eliminarObjetivo(@PathVariable int id, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        service.deleteObjetivos(id);
        return "redirect:/objetivos";
    }

    @GetMapping("/buscar")
    public String lisatrObjetivos(@RequestParam(required = false) Integer id, Model model, HttpSession session) {

        List<Objetivos> listaObjetivos;
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {
            if (id != null) {
                Objetivos buscar = service.getById(id); //  aquí ya puede lanzar excepción
                // Verifica que el Objetivo pertenezca al usuario en sesión
                // Si es ADMIN puede ver cualquiera, si es USER solo los suyos
                if ("ADMIN".equals(rol)|| buscar.getUsuario().getId_usuario().equals(usuarioId)){
                    listaObjetivos = List.of(buscar);
                }else {
                    // El objetivo no le pertenece, se muestra lista vacía con mensaje
                    model.addAttribute("error", "No tienes permiso para ver este registro");
                    model.addAttribute("listaObjetivos", List.of());
                    return "Objetivos";
                }
            } else {
                // Sin id: ADMIN ve todos, USER solo los suyos
                if ("ADMIN".equals(rol)) {
                    listaObjetivos = service.getAllObjetivos();
                } else {
                    listaObjetivos = service.getByIdUsuario(usuarioId);
                }

            }

            model.addAttribute("listaObjetivos", listaObjetivos);

        } catch (CustomException e) {
            System.out.println(" ERROR CAPTURADO: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listaObjetivos", List.of()); // ← sin llamar al service , Listo para copiar y pegar
        }

        return "Objetivos";
    }

   @GetMapping("/objetivosAdmin")
    public String mostrarObjetivosAdmin(Model model, HttpSession session) {

       Integer usuarioId = (Integer) session.getAttribute("usuarioId");
       String rol = (String) session.getAttribute("rol");

       // Validación de sesión
       // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
       if (usuarioId == null || rol == null) {
           return "redirect:/login";
       }

        List<Objetivos> lista = service.getAllObjetivos();

        int completados = 0;
        int pendientes = 0;
        int enProgreso = 0;

        for (Objetivos obj : lista) {

            String estado = obj.getEstadoObjetivo();

            if (estado.equalsIgnoreCase("completado")) {
                completados++;
            } else if (estado.equalsIgnoreCase("pendiente")) {
                pendientes++;
            } else if (estado.equalsIgnoreCase("en progreso")) {
                enProgreso++;
            }
        }

        int total = completados + pendientes + enProgreso;

        int pCompletados = total > 0 ? (completados * 100) / total : 0;
        int pPendientes = total > 0 ? (pendientes * 100) / total : 0;
        int pEnProgreso = total > 0 ? (enProgreso * 100) / total : 0;
        model.addAttribute("listaObjetivos", lista);
        model.addAttribute("labels", List.of("Completados", "Pendientes", "En Progreso"));
        model.addAttribute("data", List.of(completados, pendientes, enProgreso));
        model.addAttribute("porcentajes", List.of(pCompletados, pPendientes, pEnProgreso));

        return "ObjetivosAdmin";
    }
}
