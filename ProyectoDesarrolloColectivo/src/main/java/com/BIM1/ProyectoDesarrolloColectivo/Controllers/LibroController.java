package com.BIM1.ProyectoDesarrolloColectivo.Controllers;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import com.BIM1.ProyectoDesarrolloColectivo.Service.LibroService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/libro")
public class LibroController {
    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public String listarLibro(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("libros", libroService.getAListLibro());
        model.addAttribute("librosFormu", new Libro());
        return "libro";
    }

    @GetMapping("/editarLibro/{id}")
    public String editarLibro(@PathVariable Integer id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        model.addAttribute("libros", libroService.getAListLibro());
        model.addAttribute("librosFormu",libroService.getLibroById(id));
        return "libro";
    }

    @PostMapping("/guardarLibro")
    public String guardarLibro(@Valid @ModelAttribute("librosFormu") Libro libro, BindingResult result, RedirectAttributes redirectAttributes, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if(result.hasErrors()){
            model.addAttribute("libros", libroService.getAListLibro());
            return "libro";
        }
        libroService.saveLibro(libro);
        redirectAttributes.addFlashAttribute("exito", "el libro se ha guardado");
        return "redirect:/libro";
    }

    @GetMapping("/buscarLibro")
    public String buscarLibro(@RequestParam Integer id, Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        Libro libro = libroService.getLibroById(id);
        model.addAttribute("libros", libroService.getLibroById(id));
        model.addAttribute("librosFormu", libro);
        return "libro";
    }

    @PostMapping("/actualizarLibro/{id}")
    public String actualizarLibro(@PathVariable Integer id, @Valid @ModelAttribute("librosFormu") Libro libro, Model model, RedirectAttributes redirectAttributes, BindingResult result, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        if(result.hasErrors()){
            model.addAttribute("libros", libroService.getAListLibro());
            return "libro";
        }
        libroService.updateLibro(id, libro);
        redirectAttributes.addFlashAttribute("exito", "el libro se ha actualizado");
        return "redirect:/libro";
    }

    @PostMapping("/eliminarLibro/{id}")
    public String eliminarLibro(@PathVariable Integer id, RedirectAttributes redirectAttributes, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        libroService.deleteLibro(id);
        redirectAttributes.addFlashAttribute("exito", "el libro se a eliminado");
        return "redirect:/libro";

    }
    @GetMapping("/estadisticas")
    public String estadisticasLibros(Model model, HttpSession session) {

        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        String rol = (String) session.getAttribute("rol");

        // Validación de sesión
        // Si el id del usuario o el rol están vacios, lo mandará a la vista del login
        if (usuarioId == null || rol == null) {
            return "redirect:/login";
        }

        List<Libro> libros = libroService.getAListLibro();

        // Conteo por estado (Doughnut en chart.js)
        long pendiente = libros.stream().filter(l -> "pendiente".equals(l.getEstado())).count(); // esto basicamente va a ser un filtro en el que solo se van a mostrar los libros cuyo estado sea "pendiente" y los va a contar
        long leyendo   = libros.stream().filter(l -> "leyendo".equals(l.getEstado())).count(); // igual que el anterior, el stream va a ser la "tuberia" o "transporte" de datos para poder filtrar los libros cuyo estado sea leyendo y los va a contar
        long terminado = libros.stream().filter(l -> "terminado".equals(l.getEstado())).count(); // lo mismo con este, va a hacer el filtro con los libros que tenga el estado de "terminado" y los va a contar

        // Top 10 libros por páginas totales (Bar horizontal). Esto basicamente va a ser tambien un filtro pero el sorted va a ordenar de mayor a menor la cantiad de paginas y el .compare va a decidir que objeto en este caso las paginas van a estar antes o despues, despues el limit va a agarrar a los 10 primeros y el collect va a volver a una lista normal
        List<Libro> topPaginas = libros.stream().sorted((a, b) -> Integer.compare(b.getCantidad_pag(), a.getCantidad_pag())).limit(10).collect(Collectors.toList());

        // Progreso de lectura (%) de libros activos, ordenado desc (Bar). esto de igual forma tiene su filtro, va a jalar solo los libros que tengan paginas, y que su estado no sea pendiente despues con el sorted dice que va a ir de mayor a menor, luego de eso ordena el porcentaje de cada uno y los ordena de mayor a menor
        List<Libro> librosConProgreso = libros.stream().filter(l -> l.getCantidad_pag() > 0 && !"pendiente".equals(l.getEstado())).sorted((a, b) -> {
                    double progA = (double) a.getCantidad_leido() / a.getCantidad_pag();
                    double progB = (double) b.getCantidad_leido() / b.getCantidad_pag();
                    return Double.compare(progB, progA);
                })
                .limit(15)
                .collect(Collectors.toList());

        // Stats generales
        int totalPaginas = libros.stream().mapToInt(Libro::getCantidad_pag).sum(); //esto va a extraer un numero (cantidad de paginas) de cada libro usando el metodo de referencia "::" despues solo lo va a sumar la cantidad de paginas de cada libro
        int totalLeidas  = libros.stream().mapToInt(Libro::getCantidad_leido).sum();// lo mismo con esta linea de codigo anterior, va a agarrar las paginas leidas usando el metodo :: despues las va a sumar

        model.addAttribute("libros",            libros);
        model.addAttribute("cntPendiente",      pendiente);
        model.addAttribute("cntLeyendo",        leyendo);
        model.addAttribute("cntTerminado",      terminado);
        model.addAttribute("topPaginas",        topPaginas);
        model.addAttribute("librosConProgreso", librosConProgreso);
        model.addAttribute("totalPaginas",      totalPaginas);
        model.addAttribute("totalLeidas",       totalLeidas);
        model.addAttribute("totalLibros",       libros.size());

        return "LibroEstadisticas";
    }



}
