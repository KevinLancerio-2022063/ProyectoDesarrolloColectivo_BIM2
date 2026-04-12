package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;

import java.util.List;

public interface ApoyoEmocionalService {
    List<ApoyoEmocional> getAllFraseMotivadora();
    ApoyoEmocional getById(Integer id);
    ApoyoEmocional saveFraseMotivadora(ApoyoEmocional apoyoEmocional) throws RuntimeException;
    ApoyoEmocional updateFraseMotivadora(Integer id,ApoyoEmocional apoyoEmocional);
    void deleteFraseMotivadora(Integer id);
}
