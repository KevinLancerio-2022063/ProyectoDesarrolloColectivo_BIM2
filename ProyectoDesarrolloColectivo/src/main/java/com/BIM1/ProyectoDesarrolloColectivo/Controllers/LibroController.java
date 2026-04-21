package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import com.BIM1.ProyectoDesarrolloColectivo.Service.LibroService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
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

    @GetMapping("/buscarLibro")
    public String buscarLibro(@RequestParam Integer id, Model model){
        Libro libro = libroService.getLibroById(id);
        model.addAttribute("libros", libroService.getAListLibro());
        model.addAttribute("librosFormu", libro);
        return "libro";
    }

    @PostMapping("/actualizarLibro/{id}")
    public String actualizarLibro(@PathVariable Integer id, @Valid @ModelAttribute("librosFormu") Libro libro, Model model, RedirectAttributes redirectAttributes, BindingResult result ){
        if(result.hasErrors()){
            model.addAttribute("libros", libroService.getAListLibro());
            return "libro";
        }
        libroService.updateLibro(id, libro);
        redirectAttributes.addFlashAttribute("exito", "el libro se ha actualizado");
        return "redirect:/libro";
    }

    @PostMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        libroService.deleteLibro(id);
        redirectAttributes.addFlashAttribute("exito", "el libro se a eliminado");
        return "redirect:/libro";

    }


}
