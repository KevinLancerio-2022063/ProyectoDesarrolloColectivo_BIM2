package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.PerfilNutricional;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.PerfilNutricionalRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.PerfilNutricionalValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilNutricionalServiceImplements implements PerfilNutricionalService{
    private final PerfilNutricionalRepository perfilNutricionalRepository;
    private final PerfilNutricionalValidator perfilNutricionalValidator;

    public PerfilNutricionalServiceImplements(PerfilNutricionalRepository perfilNutricionalRepository, PerfilNutricionalValidator perfilNutricionalValidator) {
        this.perfilNutricionalRepository = perfilNutricionalRepository;
        this.perfilNutricionalValidator = perfilNutricionalValidator;
    }


    @Override
    public List<PerfilNutricional> getAListPerfilNuticional() {
        return perfilNutricionalRepository.findAll();
    }

    @Override
    public PerfilNutricional getPerfilNutricionalById(Integer id) {
        PerfilNutricional perfilNutricional = perfilNutricionalRepository.findById(id).orElse(null);
        if(perfilNutricional == null){
            throw new IllegalArgumentException();
        }else{
            return perfilNutricionalRepository.findById(id).orElse(null);
        }
    }

    @Override
    public PerfilNutricional savePerfilNutrcional(PerfilNutricional perfilNutricional) throws RuntimeException {
        perfilNutricionalValidator.PerfilNutricionalValidaciones(perfilNutricional);
        return perfilNutricionalRepository.save(perfilNutricional);
    }

    @Override
    public PerfilNutricional updatePerfilNutricional(Integer id, PerfilNutricional perfilNutricional) {
        PerfilNutricional perfilNutricional1 = perfilNutricionalRepository.findById(id).orElse(null);
        if(perfilNutricional1 != null){
            perfilNutricionalValidator.PerfilNutricionalValidaciones(perfilNutricional);
            perfilNutricional1.setPeso_kg(perfilNutricional.getPeso_kg());
            perfilNutricional1.setAltura(perfilNutricional.getAltura());
            perfilNutricional1.setEdad(perfilNutricional.getEdad());
            perfilNutricional1.setGenero(perfilNutricional.getGenero());
            perfilNutricional1.setNivel_actividad(perfilNutricional.getNivel_actividad());
            perfilNutricional1.setObjetivo(perfilNutricional.getObjetivo());
            perfilNutricional1.setFk_id_usuario(perfilNutricional.getFk_id_usuario());

        }else{
            throw new Exception("el id del perfil no existe");
        }
        return perfilNutricionalRepository.save(perfilNutricional1);
    }

    @Override
    public void deletePerfilNutricional(Integer id) {
        PerfilNutricional perfilNutricional1 = perfilNutricionalRepository.findById(id).orElse(null);
        if(perfilNutricional1 == null){
            throw new Exception("el id del perfil no existe");
        }
        perfilNutricionalRepository.deleteById(id);
    }
}
