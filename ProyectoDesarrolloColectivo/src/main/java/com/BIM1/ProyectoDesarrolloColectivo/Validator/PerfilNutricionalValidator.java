package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.PerfilNutricional;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PerfilNutricionalValidator {

    public void PerfilNutricionalValidaciones(PerfilNutricional perfil) {

        List<String> generosValidos = Arrays.asList("Femenino", "Masculino");
        if (!generosValidos.contains(perfil.getGenero())) {
            throw new Exception("El género debe ser 'Masculino' o 'Femenino'.");
        }

        List<String> nivelesValidos = Arrays.asList("bajo", "medio", "alto");
        if (!nivelesValidos.contains(perfil.getNivel_actividad())) {
            throw new Exception("El nivel de actividad debe ser 'bajo', 'medio' o 'alto'.");
        }

    }
}
