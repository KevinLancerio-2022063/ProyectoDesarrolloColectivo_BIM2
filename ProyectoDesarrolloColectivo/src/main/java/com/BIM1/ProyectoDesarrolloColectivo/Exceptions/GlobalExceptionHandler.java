package com.BIM1.ProyectoDesarrolloColectivo.Exceptions;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Service.ObjetivoMeditacionService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.RegistroSuenoService;
import com.BIM1.ProyectoDesarrolloColectivo.Service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

// El @ControllerAdvice sirve para centralizar el manejo de excepciones y la compartición de modelos en toda la aplicación
@ControllerAdvice
public class GlobalExceptionHandler {

    // Inyectamos el servicio para volver a cargar los datos
    private final UsuarioService service;
    private final RegistroSuenoService registroSuenoService;
    private final ObjetivoMeditacionService objetivoMeditacionService;

    public GlobalExceptionHandler(UsuarioService service, RegistroSuenoService registroSuenoService, ObjetivoMeditacionService objetivoMeditacionService) {
        this.service = service;
        this.registroSuenoService = registroSuenoService;
        this.objetivoMeditacionService = objetivoMeditacionService;
    }

    // Con esto capturamos cualquier excepción no controlada
    @ExceptionHandler(Exception.class)
    public String validarErrorUsuario(Exception ex, Model model){

        //Cargamos los datos necesarios para que la vista funcione
        model.addAttribute("usuario", new Usuario()); // Carga los datos para el formulario
        model.addAttribute("usuarios", service.getAllUsuarios()); // Carga los datos para la tabla/lista
        model.addAttribute("errores", List.of(ex.getMessage())); // Carga los datos para mostrar el mensaje al usuario

        return "usuarios";
    }

    // Validar errores de Registro Sueño
    @ExceptionHandler(RuntimeException.class)
    public String ValidarErrorRegistroSueno(RuntimeException ex, Model model){

        model.addAttribute("registro", new RegistroSueno());
        model.addAttribute("registros", registroSuenoService.getAllRegistrosSuenos());
        model.addAttribute("errores", List.of(ex.getMessage()));

        return "registroSueno";
    }

    // Validar errores de Objetivo Meditación
    @ExceptionHandler(IllegalArgumentException.class)
    public String validarErrorObjetivoMeditacion(IllegalArgumentException ex, Model model){

        model.addAttribute("objetivo", new ObjetivoMeditacion());
        model.addAttribute("objetivos", objetivoMeditacionService.getAllObjetivosMeditacion());
        model.addAttribute("errores", List.of(ex.getMessage()));

        return "objetivoMeditacion";
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

}