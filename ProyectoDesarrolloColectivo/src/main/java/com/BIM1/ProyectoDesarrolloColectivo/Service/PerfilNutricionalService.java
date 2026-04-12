package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.PerfilNutricional;

import java.util.List;

public interface PerfilNutricionalService {
    List<PerfilNutricional> getAListPerfilNuticional();
    PerfilNutricional getPerfilNutricionalById(Integer id);
    PerfilNutricional savePerfilNutrcional(PerfilNutricional perfilNutricional) throws RuntimeException;
    PerfilNutricional updatePerfilNutricional(Integer id, PerfilNutricional perfilNutricional);
    void deletePerfilNutricional(Integer id);

}
