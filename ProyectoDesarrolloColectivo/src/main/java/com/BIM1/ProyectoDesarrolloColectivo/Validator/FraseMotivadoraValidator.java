package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.FraseMotivadoraRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import org.springframework.stereotype.Component;

@Component
public class FraseMotivadoraValidator {
    public final FraseMotivadoraRepository fraseMotivadoraRepository;

    public FraseMotivadoraValidator(FraseMotivadoraRepository fraseMotivadoraRepository) {
        this.fraseMotivadoraRepository = fraseMotivadoraRepository;
    }

    public void FraseMotivadoraValidaciones(FraseMotivadora fraseMotivadora){
        if(fraseMotivadoraRepository.existsByTextoAndAutor(fraseMotivadora.getTexto(),fraseMotivadora.getAutor())){
            throw new CustomException("Ya existe una frase con los mismos datos");
        }

        if(fraseMotivadora.getAutor().trim().length() > 50){
            throw new CustomException("El nombre del autor es demasiado largo, solo se permiten 50 digitos");
        }
    }

    public void FraseMotivadoraValidacionesId(Integer id){
        if(!fraseMotivadoraRepository.existsById(id)){
            throw new CustomException("Frase no encontrada");
        }
    }
}
