package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.ObjetivoMeditacionRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.ObjetivoMeditacionValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjetivoMeditacionServiceImplements implements ObjetivoMeditacionService{

    private final ObjetivoMeditacionRepository objetivoMeditacionRepository;
    private final ObjetivoMeditacionValidator objetivoMeditacionValidator;

    public ObjetivoMeditacionServiceImplements (ObjetivoMeditacionRepository objetivoMeditacionRepository, ObjetivoMeditacionValidator objetivoMeditacionValidator) {
        this.objetivoMeditacionRepository = objetivoMeditacionRepository;
        this.objetivoMeditacionValidator = objetivoMeditacionValidator;
    }

    @Override
    public List<ObjetivoMeditacion> getAllObjetivosMeditacion() {
        return objetivoMeditacionRepository.findAll();
    }

    @Override
    public ObjetivoMeditacion getObjetivosMeditacionById (Integer id) {
        return objetivoMeditacionRepository.findById(id).orElse(null);
    }

    @Override
    public ObjetivoMeditacion saveObjetivoMeditacion (ObjetivoMeditacion objetivoMeditacion) throws RuntimeException {
        objetivoMeditacionValidator.ObjetivoMeditacionValidar(objetivoMeditacion);
        return objetivoMeditacionRepository.save(objetivoMeditacion);
    }

    @Override
    public void deleteObjetivoMeditacion (Integer id) {
        objetivoMeditacionRepository.deleteById(id);
    }

    @Override
    public ObjetivoMeditacion updateObjetivoMeditacion (Integer id, ObjetivoMeditacion objetivoMeditacion) {

        ObjetivoMeditacion ObjetivoMeditacionExistente = objetivoMeditacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El objetivo meditacion no se ha encontrado con id: " + id));

        objetivoMeditacionValidator.ObjetivoMeditacionValidar(objetivoMeditacion);
        ObjetivoMeditacionExistente.setTiempo_objetivo(objetivoMeditacion.getTiempo_objetivo());
        ObjetivoMeditacionExistente.setDias_objetivo(objetivoMeditacion.getDias_objetivo());
        ObjetivoMeditacionExistente.setFk_id_usuario(objetivoMeditacion.getFk_id_usuario());

        return objetivoMeditacionRepository.save(ObjetivoMeditacionExistente);
    }

}
