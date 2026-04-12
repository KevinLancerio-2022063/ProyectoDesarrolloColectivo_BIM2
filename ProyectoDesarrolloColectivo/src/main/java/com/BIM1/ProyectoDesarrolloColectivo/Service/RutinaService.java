package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;

import java.util.List;

public interface RutinaService {
    List<Rutina> getAListRutina();
    Rutina getRutinaById(Integer id);
    Rutina saveRutina(Rutina rutina) throws RuntimeException;
    Rutina updateRutina(Integer id, Rutina rutina);
    void deleteRutina(Integer id);
}
