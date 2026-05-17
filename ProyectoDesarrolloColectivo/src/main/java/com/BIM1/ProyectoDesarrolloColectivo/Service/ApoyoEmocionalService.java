package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;

import java.util.List;

public interface ApoyoEmocionalService {
    List<ApoyoEmocional> getAllApoyoEmovional();
    ApoyoEmocional getById(Integer id);
    List<ApoyoEmocional> getByIdUsuario(Integer id);
    ApoyoEmocional saveApoyoEmocional(ApoyoEmocional apoyoEmocional) throws RuntimeException;
    ApoyoEmocional updateApoyoEmocional(Integer id, ApoyoEmocional apoyoEmocional);
    void deleteApoyoEmocional(Integer id);
}
