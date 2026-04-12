package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.FraseMotivadoraRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.FraseMotivadoraValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraseMotivadoraServiceImplements implements FraseMotivadoraService{
    public  final FraseMotivadoraValidator fraseMotivadoraValidator;
    private final FraseMotivadoraRepository fraseMotivadoraRepository;

    public FraseMotivadoraServiceImplements(FraseMotivadoraValidator fraseMotivadoraValidator, FraseMotivadoraRepository fraseMotivadoraRepository) {
        this.fraseMotivadoraValidator = fraseMotivadoraValidator;
        this.fraseMotivadoraRepository = fraseMotivadoraRepository;
    }

    @Override
    public List<FraseMotivadora> getAllFraseMotivadora() {
        return fraseMotivadoraRepository.findAll();
    }

    @Override
    public FraseMotivadora getById(Integer id) {
        return fraseMotivadoraRepository.findById(id).orElse(null);
    }

    @Override
    public FraseMotivadora saveFraseMotivadora(FraseMotivadora fraseMotivadora) throws RuntimeException {
        fraseMotivadoraValidator.FraseMotivadoraValidaciones(fraseMotivadora);
        return fraseMotivadoraRepository.save(fraseMotivadora);
    }

    @Override
    public FraseMotivadora updateFraseMotivadora(Integer id, FraseMotivadora fraseMotivadora) {
        fraseMotivadoraValidator.FraseMotivadoraValidacionesId(id);
        fraseMotivadoraValidator.FraseMotivadoraValidaciones(fraseMotivadora);
        return fraseMotivadoraRepository.save(fraseMotivadora);
    }

    @Override
    public void deleteFraseMotivadora(Integer id) {
        fraseMotivadoraValidator.FraseMotivadoraValidacionesId(id);
        fraseMotivadoraRepository.deleteById(id);
    }
}
