package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Ejercicio;

import java.util.List;

public interface EjercicioService {
    List<Ejercicio> getAListEjercicio();
    Ejercicio getEjercicioById(Integer id);
    Ejercicio saveEjercicio(Ejercicio ejercicio) throws RuntimeException;
    Ejercicio updateEjercicio(Integer id, Ejercicio ejercicio);
    void deleteEjercicio(Integer id);
}
