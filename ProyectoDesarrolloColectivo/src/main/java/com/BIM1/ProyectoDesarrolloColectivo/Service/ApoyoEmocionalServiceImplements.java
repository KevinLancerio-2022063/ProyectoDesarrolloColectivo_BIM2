package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.ApoyoEmocionalRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.ApoyoEmocionalValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApoyoEmocionalServiceImplements implements ApoyoEmocionalService{
    private final ApoyoEmocionalRepository apoyoEmocionalRepository;
    private final ApoyoEmocionalValidator apoyoEmocionalValidator;

    public ApoyoEmocionalServiceImplements(ApoyoEmocionalRepository apoyoEmocionalRepository, ApoyoEmocionalValidator apoyoEmocionalValidator) {
        this.apoyoEmocionalRepository = apoyoEmocionalRepository;
        this.apoyoEmocionalValidator = apoyoEmocionalValidator;
    }

    @Override
    public List<ApoyoEmocional> getAllFraseMotivadora() {
        return apoyoEmocionalRepository.findAll();
    }

    @Override
    public ApoyoEmocional getById(Integer id) {
        return apoyoEmocionalRepository.findById(id).orElse(null);
    }

    @Override
    public ApoyoEmocional saveFraseMotivadora(ApoyoEmocional apoyoEmocional) throws RuntimeException {
        apoyoEmocionalValidator.ApoyoEmocionalValidacion(apoyoEmocional);
        return apoyoEmocionalRepository.save(apoyoEmocional);
    }

    @Override
    public ApoyoEmocional updateFraseMotivadora(Integer id, ApoyoEmocional apoyoEmocional) {
        apoyoEmocionalValidator.ApoyoEmocionalValidacion(apoyoEmocional);
        apoyoEmocionalValidator.ApoyoEmocionalValidacionId(id);
        return apoyoEmocionalRepository.save(apoyoEmocional);
    }

    @Override
    public void deleteFraseMotivadora(Integer id) {
        apoyoEmocionalValidator.ApoyoEmocionalValidacionId(id);
        apoyoEmocionalRepository.deleteById(id);
    }
}
