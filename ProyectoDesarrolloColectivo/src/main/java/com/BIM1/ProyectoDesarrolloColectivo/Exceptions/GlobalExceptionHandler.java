package com.BIM1.ProyectoDesarrolloColectivo.Exceptions;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.PerfilNutricional;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Service.EjercicioService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.LibroService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.PerfilNutricionalService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RutinaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final RutinaService rutinaService;
    private final EjercicioService ejercicioService;
    private final LibroService libroService;
    private final PerfilNutricionalService perfilNutricionalService;

    public GlobalExceptionHandler(RutinaService rutinaService, EjercicioService ejercicioService, LibroService libroService, PerfilNutricionalService perfilNutricionalService) {
        this.rutinaService = rutinaService;
        this.ejercicioService = ejercicioService;
        this.libroService = libroService;
        this.perfilNutricionalService = perfilNutricionalService;
    }

    @ExceptionHandler(Exception.class)
    public String manejarExcepcion(Exception ex, Model model) {
        String mensaje = ex.getMessage() != null ? ex.getMessage() : "Ha ocurrido un error inesperado";

        if (mensaje.contains("rutina") || mensaje.contains("dia") || mensaje.contains("semana")) {
            model.addAttribute("rutina", rutinaService.getAListRutina());
            model.addAttribute("rutinaFormu", new Rutina());
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            model.addAttribute("errores", List.of(mensaje));
            return "rutina";
        }

        if (mensaje.contains("libro") || mensaje.contains("pagina") || mensaje.contains("leido") || mensaje.contains("estado")) {
            model.addAttribute("libros", libroService.getAListLibro());
            model.addAttribute("librosFormu", new Libro());
            model.addAttribute("errores", List.of(mensaje));
            return "libro";
        }

        if (mensaje.contains("perfil") || mensaje.contains("peso") || mensaje.contains("altura")) {
            model.addAttribute("perfilNutricional", perfilNutricionalService.getAListPerfilNuticional());
            model.addAttribute("perfilNutricionalFormu", new PerfilNutricional());
            model.addAttribute("errores", List.of(mensaje));
            return "perfilNutricional";
        }

        if (mensaje.contains("ejercicio") || mensaje.contains("rutina con el ID")) {
            model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
            model.addAttribute("ejerciciosFormu", new Ejercicio());
            model.addAttribute("rutina", rutinaService.getAListRutina());
            model.addAttribute("errores", List.of(mensaje));
            return "ejercicios";
        }

        model.addAttribute("rutina", rutinaService.getAListRutina());
        model.addAttribute("rutinaFormu", new Rutina());
        model.addAttribute("ejercicios", ejercicioService.getAListEjercicio());
        model.addAttribute("errores", List.of(mensaje));
        return "rutina";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validarAnotaciones(MethodArgumentNotValidException ex) {
        List<String> mensajes = ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage()).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errores", mensajes));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> validarJsonYTipoDato(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "JSON inválido o tipo de dato incorrecto."));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> validarFk(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("Error", "Error en las llaves foraneas"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> validarId(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "el id no se encontró en la peticion"));
    }
}