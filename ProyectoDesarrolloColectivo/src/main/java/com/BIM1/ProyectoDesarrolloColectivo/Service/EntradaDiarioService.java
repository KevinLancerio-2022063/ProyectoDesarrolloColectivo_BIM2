package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;

import java.util.List;

public interface EntradaDiarioService {
    List<com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario> getAListEntradaDiario();
    EntradaDiario getEntradaDiarioById(Integer id);
    EntradaDiario saveEntradaDiario(EntradaDiario entradaDiario) throws RuntimeException;
    EntradaDiario updateEntradaDiario(Integer id, EntradaDiario entradaDiario);
    void deleteEntradaDiario(Integer id);
}
