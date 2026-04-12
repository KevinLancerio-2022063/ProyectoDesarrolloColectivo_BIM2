package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.ObjetivosRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.ObjetivosValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjetivosServiceImplements implements ObjetivosService{
    public final ObjetivosRepository objetivosRepository;
    public final ObjetivosValidator objetivosValidator;

    public ObjetivosServiceImplements(ObjetivosRepository objetivosRepository, ObjetivosValidator objetivosValidator) {
        this.objetivosRepository = objetivosRepository;
        this.objetivosValidator = objetivosValidator;
    }

    @Override
    public List<Objetivos> getAllObjetivos() {
        return objetivosRepository.findAll() ;
    }

    @Override
    public Objetivos getById(Integer id) {
        return objetivosRepository.findById(id).orElse(null);
    }

    @Override
    public Objetivos saveObjetivos(Objetivos objetivos) throws RuntimeException {
        objetivosValidator.ObjetivosValidaciones(objetivos);
        return objetivosRepository.save(objetivos);
    }

    @Override
    public Objetivos updateObjetivos(Integer id, Objetivos objetivos) {
        objetivosValidator.ObjetivosValidaciones(objetivos);
        objetivosValidator.ObjetivosValidacionesId(id);
        return objetivosRepository.save(objetivos);
    }

    @Override
    public void deleteObjetivos(Integer id) {
        objetivosValidator.ObjetivosValidacionesId(id);
        objetivosRepository.deleteById(id);
    }
}
