package com.BIM1.ProyectoDesarrolloColectivo.Exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> validarCampos(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error:", ex.getMessage()));
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