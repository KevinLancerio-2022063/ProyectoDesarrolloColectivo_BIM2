package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RutinaValidator {

    public void validarRutina(Rutina rutina){

        List<String> diasValidos = Arrays.asList("lunes", "martes", "miércoles", "jueves","viernes", "sábado", "sabado", "sabados", "sábados", "domingo", "todos los días", "todos los dias");
        String[] diasIngresados = rutina.getDias_semana().split(",");

        for(String dia : diasIngresados){
            String diaVacio = dia.trim().toLowerCase();

            if(!diasValidos.contains(diaVacio)){
                throw new Exception("el dia no es valido, por favor use lunes, martes, miércoles, jueves, viernes, sábado, domingo o Todos los dias");
            }
        }
    }
}
