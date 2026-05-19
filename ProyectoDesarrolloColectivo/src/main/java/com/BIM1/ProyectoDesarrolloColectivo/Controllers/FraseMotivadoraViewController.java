package com.BIM1.ProyectoDesarrolloColectivo.Controllers;

import com.BIM1.ProyectoDesarrolloColectivo.Repository.FraseMotivadoraRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Service.FraseMotivadoraService;

@Controller
public class FraseMotivadoraViewController {
    @Autowired
    private FraseMotivadoraService fraseMotivadoraService;

    @Autowired
    private FraseMotivadoraRepository repository;

    @GetMapping("/frasesMotivadoras")
    public String mostrarFraseMotivadora(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if (!rol.equals("ADMIN")) {
            return "redirect:/fraseMotivadora";
        }

        model.addAttribute("frases", fraseMotivadoraService.getAllFraseMotivadora());
        return "FraseMotivadora";
    }

    @GetMapping("/fraseMotivadora")
    public String fraseAlAZar(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if (rol.equals("ADMIN")) {

            model.addAttribute("frases", fraseMotivadoraService.getAllFraseMotivadora());
            return "FraseMotivadora";
        }

        long total = repository.count();

        if (total == 0) {
            model.addAttribute("frases", "No hay frases disponibles");
            return "FraseMotivadoraAdmin";
        }

        // Obtener fecha actual
        LocalDate hoy = LocalDate.now();

        // Convertir la fecha a número (por ejemplo: días desde epoch)
        long seed = hoy.toEpochDay();

        // Crear Random con semilla fija del día
        Random random = new Random(seed);

        int numeroAleatorio = random.nextInt((int) total);

        model.addAttribute("frases", fraseMotivadoraService.getById(numeroAleatorio));

        return "FraseMotivadoraUser";
    }

    @GetMapping("/agregarFrase")
    public String agregarFraseMotivadora(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if (!rol.equals("ADMIN")) {
            return "redirect:/fraseMotivadora";
        }

        model.addAttribute("frase", new FraseMotivadora());
        return "agregarFrase";
    }

    @PostMapping("/guardarFraseCreada")
    public String guardarFraseCreada(@ModelAttribute FraseMotivadora frase, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {
            fraseMotivadoraService.saveFraseMotivadora(frase);
            return "redirect:/frasesMotivadoras";
        } catch (Exception e) {
            System.out.println("🔥 ERROR CAPTURADO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/agregarFrase";
        }
    }

    @GetMapping("/editarFrase/{id}")
    public String editarFraseMotivadora(@PathVariable int id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        FraseMotivadora frase = fraseMotivadoraService.getById(id);
        model.addAttribute("frase", frase);
        return "editarFrase";
    }

    @PostMapping("/guardarFrase")
    public String guardarFrase(@ModelAttribute FraseMotivadora frase, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        try {
            FraseMotivadora original = fraseMotivadoraService.getById(frase.getIdFraseMotivadora());
            original.setTexto(frase.getTexto());
            original.setAutor(frase.getAutor());
            fraseMotivadoraService.updateFraseMotivadora(frase.getIdFraseMotivadora(), original);
            return "redirect:/frasesMotivadoras";
        } catch (Exception e) {
            System.out.println("🔥 ERROR CAPTURADO: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/editarFrase/" + frase.getIdFraseMotivadora();
        }
    }

    @GetMapping("/eliminar-frase/{id}")
    public String eliminarFraseMotivador(@PathVariable int id, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        fraseMotivadoraService.deleteFraseMotivadora(id);
        return "redirect:/frasesMotivadoras";
    }

    @GetMapping("/buscarFrase")
    public String buscarFrase(@RequestParam(required = false) Integer id, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        List<FraseMotivadora> listaFrases;
        try {
            if (id != null) {
                FraseMotivadora frase = fraseMotivadoraService.getById(id);
                listaFrases = List.of(frase);
            } else {
                listaFrases = fraseMotivadoraService.getAllFraseMotivadora();
            }
            model.addAttribute("frases", listaFrases);
        }catch (Exception e) {
            System.out.println("🔥 ERROR CAPTURADO: " + e.getMessage());
           redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("frases", List.of());
        }
        return "FraseMotivadora";
    }
}
