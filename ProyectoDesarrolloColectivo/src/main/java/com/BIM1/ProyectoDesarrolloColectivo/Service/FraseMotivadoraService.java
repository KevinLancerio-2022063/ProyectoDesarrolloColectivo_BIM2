package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;

import java.util.List;

public interface FraseMotivadoraService {
    List<FraseMotivadora> getAllFraseMotivadora();
    FraseMotivadora getById(Integer id);
    FraseMotivadora saveFraseMotivadora(FraseMotivadora fraseMotivadora) throws RuntimeException;
    FraseMotivadora updateFraseMotivadora(Integer id, FraseMotivadora fraseMotivadora);
    void deleteFraseMotivadora(Integer id);
}
