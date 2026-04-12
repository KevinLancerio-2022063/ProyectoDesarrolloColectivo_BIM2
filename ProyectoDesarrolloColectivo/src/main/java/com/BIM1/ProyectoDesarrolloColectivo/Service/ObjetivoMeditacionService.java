package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ObjetivoMeditacionService {

    List<ObjetivoMeditacion> getAllObjetivosMeditacion();
    ObjetivoMeditacion getObjetivosMeditacionById (Integer id);
    ObjetivoMeditacion saveObjetivoMeditacion (ObjetivoMeditacion objetivoMeditacion) throws RuntimeException;
    ObjetivoMeditacion updateObjetivoMeditacion (Integer id, ObjetivoMeditacion objetivoMeditacion);
    void deleteObjetivoMeditacion (Integer id);
}
