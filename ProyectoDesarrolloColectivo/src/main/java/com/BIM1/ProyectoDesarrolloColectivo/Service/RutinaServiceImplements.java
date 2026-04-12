package com.BIM1.ProyectoDesarrolloColectivo.Service;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.RutinaRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.RutinaValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaServiceImplements implements RutinaService {
    private final RutinaRepository rutinaRepository;
    private final RutinaValidator rutinaValidator;

    public RutinaServiceImplements(RutinaRepository rutinaRepository, RutinaValidator rutinaValidator) {
        this.rutinaRepository = rutinaRepository;
        this.rutinaValidator = rutinaValidator;
    }

    @Override
    public List<Rutina> getAListRutina() {
        return rutinaRepository.findAll();
    }

    @Override
    public Rutina getRutinaById(Integer id) {
        Rutina rutina = rutinaRepository.findById(id).orElse(null);
        if(rutina == null){
            throw new IllegalArgumentException();
        }else {
            return rutinaRepository.findById(id).orElse(null);
        }
    }

    @Override
    public Rutina saveRutina(Rutina rutina) throws RuntimeException {
        rutinaValidator.validarRutina(rutina);
        return rutinaRepository.save(rutina);
    }

    @Override
    public Rutina updateRutina(Integer id, Rutina rutina) {
        Rutina rutina1 = rutinaRepository.findById(id).orElse(null);
        if(rutina1 != null){
            rutinaValidator.validarRutina(rutina);
            rutina1.setNombre_rutina(rutina.getNombre_rutina());
            rutina1.setDias_semana(rutina.getDias_semana());
            rutina1.setFk_id_usuario(rutina.getFk_id_usuario());

        }else{
            throw new Exception("el id de la rutina no existe");
        }
        return rutinaRepository.save(rutina1);
    }

    @Override
    public void deleteRutina(Integer id) {
        Rutina rutina = rutinaRepository.findById(id).orElse(null);
        if(rutina == null){
            throw new Exception("el id de la rutina no existe");
        }
        rutinaRepository.deleteById(id);

    }
}
