package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LibroValidator {

    public void libroValidaciones(Libro libro){


        if(libro.getCantidad_leido() > libro.getCantidad_pag()){
            throw new Exception("la cantidad leido no puede ser mayor a las cantidades de paginas que tiene el libro");
        }

        if(libro.getEstado().equals("terminado") && libro.getCantidad_leido() != libro.getCantidad_pag()){
            throw new Exception("las paginas leidas deben ser iguales al total del paginas del libro");
        }

        if(libro.getEstado().equals("pendiente") && libro.getCantidad_leido() > 0){
            throw new Exception("las paginas leidas deben de ser 0 por el estado en el que se encuentra");
        }

        List<String> validarEstados = Arrays.asList("pendiente", "leyendo", "terminado");
        if(!validarEstados.contains(libro.getEstado())){
            throw new Exception("el estado solo puede ser: pendiente, leyendo o terminado ");
        }
    }
}
