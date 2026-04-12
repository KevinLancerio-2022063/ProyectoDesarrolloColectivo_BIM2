package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;

import java.util.List;

public interface RegistroMeditacionService {
    List<RegistroMeditacion> getAListRegistroMeditacion();
    RegistroMeditacion getRegistroMeditacionById(Integer id);
    RegistroMeditacion saveRegistroMeditacion(RegistroMeditacion registroMeditacion) throws RuntimeException;
    RegistroMeditacion updateRegistroMeditacion(Integer id, RegistroMeditacion registroMeditacion);
    void deleteRegistroMeditacion(Integer id);
}
