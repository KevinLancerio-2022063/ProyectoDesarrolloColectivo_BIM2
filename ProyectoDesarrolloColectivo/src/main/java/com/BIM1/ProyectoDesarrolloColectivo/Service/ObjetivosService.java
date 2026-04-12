package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;

import java.util.List;

public interface ObjetivosService {
    List<Objetivos> getAllObjetivos();
    Objetivos getById(Integer id);
    Objetivos saveObjetivos(Objetivos objetivos) throws RuntimeException;
    Objetivos updateObjetivos(Integer id, Objetivos objetivos);
    void deleteObjetivos(Integer id);
}
