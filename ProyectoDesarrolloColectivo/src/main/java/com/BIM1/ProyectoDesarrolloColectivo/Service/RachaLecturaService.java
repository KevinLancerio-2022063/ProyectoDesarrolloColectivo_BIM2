package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaLectura;

import java.time.LocalDate;
import java.util.List;

public interface RachaLecturaService {

    List<RachaLectura> getRachasByUsuario(Integer idUsuario);

    List<RachaLectura> getRachasByUsuarioAndRango(Integer idUsuario, LocalDate inicio, LocalDate fin);

    RachaLectura saveRacha(RachaLectura racha) throws RuntimeException;

    RachaLectura addRacha(Integer idUsuario, LocalDate fecha) throws RuntimeException;
}