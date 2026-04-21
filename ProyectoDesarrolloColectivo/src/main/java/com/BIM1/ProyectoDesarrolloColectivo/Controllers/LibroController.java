package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import com.BIM1.ProyectoDesarrolloColectivo.Service.LibroService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/libro")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public String listarLibro(Model model){
        model.addAttribute("libros", libroService.getAListLibro());
        model.addAttribute("librosFormu", new Libro());
        return "libro";
    }

    @GetMapping("/editarLibro/{id}")
    public String editarLibro(@PathVariable Integer id, Model model){
        model.addAttribute("libros", libroService.getAListLibro());
        model.addAttribute("librosformu",libroService.getLibroById(id));
        return "libro";
    }

    @PostMapping("/guardarLibro")
    public String guardarLibro(@Valid @ModelAttribute("librosFormu") Libro libro, BindingResult result, RedirectAttributes redirectAttributes, Model model){
        if(result.hasErrors()){
            model.addAttribute("libros", libroService.getAListLibro());
            return "libro";
        }
        libroService.saveLibro(libro);
        redirectAttributes.addFlashAttribute("exito", "el libro se ha guardado");
        return "redirect:/libro";
    }

}
