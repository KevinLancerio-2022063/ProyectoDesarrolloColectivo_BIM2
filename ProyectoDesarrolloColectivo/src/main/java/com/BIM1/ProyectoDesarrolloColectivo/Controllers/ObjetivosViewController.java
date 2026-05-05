package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Service.FraseMotivadoraService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivosService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;

import java.util.List;

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
    public String guardarObjetivoCreado(@ModelAttribute Objetivos objetivo, RedirectAttributes redirectAttributes) {
        try {
            service.saveObjetivos(objetivo);
            return "redirect:/objetivos";
        } catch (CustomException e) {
            System.out.println("🔥 ERROR CAPTURADO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/agregarObjetivo";
        }
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
    public String guardarObjetivo(@ModelAttribute  Objetivos objetivo, RedirectAttributes redirectAttributes) {
        try{
            Objetivos original = service.getById(objetivo.getIdObjetivos());
            original.setTituloObjetivo(objetivo.getTituloObjetivo());
            original.setDescripcionObjetivo(objetivo.getDescripcionObjetivo());
            original.setEstadoObjetivo(objetivo.getEstadoObjetivo());
            original.setFechaObjetivo(objetivo.getFechaObjetivo());
            original.setUsuario(objetivo.getUsuario());
            original.setFraseMotivadora(objetivo.getFraseMotivadora());
            service.updateObjetivos(objetivo.getIdObjetivos(), original);
            return "redirect:/detalleObjetivos/" + objetivo.getIdObjetivos();
        } catch (CustomException e) {
            System.out.println(" ERROR CAPTURADO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editarObjetivo/" + objetivo.getIdObjetivos();
        }
    }

    @GetMapping("/eliminar-objetivo/{id}")
    public String eliminarObjetivo(@PathVariable int id){
        service.deleteObjetivos(id);
        return "redirect:/objetivos";
    }

    @GetMapping("/buscar")
    public String lisatrObjetivos(@RequestParam(required = false) Integer id, Model model) {
        List<Objetivos> listaObjetivos;

        try {
            if (id != null) {
                Objetivos buscar = service.getById(id); //  aquí ya puede lanzar excepción , dime que mas quieres que haga
                listaObjetivos = List.of(buscar);
            } else {
                listaObjetivos = service.getAllObjetivos();
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
    public String mostrarObjetivosAdmin(Model model) {

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
