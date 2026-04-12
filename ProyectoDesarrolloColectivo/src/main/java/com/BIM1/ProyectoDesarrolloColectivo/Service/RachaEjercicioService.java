package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaEjercicio;

import java.time.LocalDate;
import java.util.List;

public interface RachaEjercicioService {

    List<RachaEjercicio> getRachasByUsuario(Integer idUsuario);

    List<RachaEjercicio> getRachasByUsuarioAndRango(Integer idUsuario, LocalDate inicio, LocalDate fin);

    RachaEjercicio saveRacha(RachaEjercicio racha) throws RuntimeException;

    RachaEjercicio addRacha(Integer idUsuario, LocalDate fecha) throws RuntimeException;
}