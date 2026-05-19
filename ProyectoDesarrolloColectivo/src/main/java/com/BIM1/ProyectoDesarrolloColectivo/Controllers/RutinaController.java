package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EjercicioService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RutinaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/rutina")
public class RutinaController {
    private final RutinaService rutinaService;
    private final EjercicioService ejercicioService;

    public RutinaController(RutinaService rutinaService, EjercicioService ejercicioService) {
        this.rutinaService = rutinaService;
        this.ejercicioService = ejercicioService;
    }

    @GetMapping
    public String listar(Model model, HttpSession session){

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", new Rutina());
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        return "rutina";
    }

    @GetMapping("/editarRutina/{id}")
    public String editarRutina(@PathVariable Integer id, Model model, HttpSession session){

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("rutinaFormu", rutinaService.getRutinaById(id));
        return "rutina";
    }

    @GetMapping("/buscarRutina")
    public String buscarRutina(@RequestParam Integer id, Model model, HttpSession session){

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        Rutina rutina = rutinaService.getRutinaById(id);
        model.addAttribute("rutina", rutinaService.getRutinaById(id));
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("rutinaFormu", rutina);
        return "rutina";
    }

     @PostMapping("/actualizarRutina/{id}")
    public String actualizarRutina(@PathVariable Integer id, @Valid @ModelAttribute("rutinaFormu")Rutina rutina,BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpSession session){

         Integer usuarioId = (Integer) session.getAttribute("usuarioId");
         String rol = (String) session.getAttribute("rol");

         // Validación de sesión
         // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
         if (usuarioId == null || rol == null) {
             return "redirect:/login";
         }

        if(result.hasErrors()){
            model.addAttribute("rutina", rutinaService.getAListRutina());
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            return "rutina";
        }
        rutinaService.updateRutina(id, rutina);
        redirectAttributes.addFlashAttribute("exito", "la rutina se ha actualizado");
        return "redirect:/rutina";
     }

     @PostMapping("/guardarRutina")
    public String guardarRutina(@Valid @ModelAttribute("rutinaFormu")Rutina rutina, BindingResult result, Model model, RedirectAttributes redirectAttributes, HttpSession session){

         Integer usuarioId = (Integer) session.getAttribute("usuarioId");
         String rol = (String) session.getAttribute("rol");

         // Validación de sesión
         // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
         if (usuarioId == null || rol == null) {
             return "redirect:/login";
         }

        if (result.hasErrors()){
            model.addAttribute("rutina", rutinaService.getAListRutina());
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            return "rutina";
        }
        rutinaService.saveRutina(rutina);
        redirectAttributes.addFlashAttribute("exito", "la rutina se ha guardado");
        return "redirect:/rutina";
     }

     @PostMapping("/eliminarRutina/{id}")
    public String eliminarRutina(@PathVariable Integer id, RedirectAttributes redirectAttributes, HttpSession session){

         Integer usuarioId = (Integer) session.getAttribute("usuarioId");
         String rol = (String) session.getAttribute("rol");

         // Validación de sesión
         // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
         if (usuarioId == null || rol == null) {
             return "redirect:/login";
         }

        rutinaService.deleteRutina(id);
        redirectAttributes.addFlashAttribute("exito", "la rutina se ha eliminado");
        return "redirect:/rutina";
     }


}
