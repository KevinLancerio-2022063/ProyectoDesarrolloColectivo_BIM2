package com.BIM1.ProyectoDesarrolloColectivo.Service;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.EjercicioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.EjercicioValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EjercicioServiceImplements implements EjercicioService{
    private final EjercicioRepository ejercicioRepository;
    private final EjercicioValidator ejercicioValidator;

    public EjercicioServiceImplements(EjercicioRepository ejercicioRepository, EjercicioValidator ejercicioValidator) {
        this.ejercicioRepository = ejercicioRepository;
        this.ejercicioValidator = ejercicioValidator;
    }

    @Override
    public List<Ejercicio> getAListEjercicio() {
        return ejercicioRepository.findAll();
    }

    @Override
    public Ejercicio getEjercicioById(Integer id) {
        Ejercicio ejercicio = ejercicioRepository.findById(id).orElse(null);
        if(ejercicio == null){
            throw new IllegalArgumentException();
        }else {
            return ejercicioRepository.findById(id).orElse(null);
        }
    }

    @Override
    public Ejercicio saveEjercicio(Ejercicio ejercicio) throws RuntimeException {
        ejercicioValidator.EjercicioValidaciones(ejercicio);
        return ejercicioRepository.save(ejercicio);
    }

    @Override
    public Ejercicio updateEjercicio(Integer id, Ejercicio ejercicio) {
        Ejercicio ejercicios = ejercicioRepository.findById(id).orElse(null);
        if(ejercicios != null){
            ejercicioValidator.EjercicioValidaciones(ejercicio);
            ejercicios.setNombre_ejercicio(ejercicio.getNombre_ejercicio());
            ejercicios.setSeries_ejercicio(ejercicio.getSeries_ejercicio());
            ejercicios.setRepeticiones_ejercicio(ejercicio.getRepeticiones_ejercicio());
            ejercicios.setTiempo_ejercicio(ejercicio.getTiempo_ejercicio());
            ejercicios.setDescanso_ejercicio(ejercicio.getDescanso_ejercicio());
            ejercicios.setFk_id_rutina(ejercicio.getFk_id_rutina()); 
        }else{
            throw new Exception("el id del ejercicio no existe");
        }
        return ejercicioRepository.save(ejercicios);
    }

    @Override
    public void deleteEjercicio(Integer id) {
        Ejercicio ejercicios = ejercicioRepository.findById(id).orElse(null);
        if(ejercicios == null){
            throw new Exception("el id del ejercicio no existe");
        }
        ejercicioRepository.deleteById(id);

    }
}
