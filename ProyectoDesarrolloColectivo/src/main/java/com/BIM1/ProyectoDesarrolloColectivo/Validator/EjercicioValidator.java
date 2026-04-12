package com.BIM1.ProyectoDesarrolloColectivo.Validator;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.RutinaRepository;
import org.springframework.stereotype.Component;

@Component
public class EjercicioValidator {
    private final RutinaRepository rutinaRepository;

    public EjercicioValidator(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    public void EjercicioValidaciones(Ejercicio ejercicio){

        if(!rutinaRepository.existsById(ejercicio.getFk_id_rutina())){
            throw new Exception("no existe una rutina con el ID" + ejercicio.getFk_id_rutina());
        }
    }

}
