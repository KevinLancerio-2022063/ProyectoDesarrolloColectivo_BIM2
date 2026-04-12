package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistroSuenoService {

    List<RegistroSueno> getAllRegistrosSuenos();
    RegistroSueno getRegistrosSuenosById (Integer id);
    RegistroSueno saveRegistroSueno (RegistroSueno registroSueno) throws RuntimeException;
    RegistroSueno updateRegistroSueno (Integer id, RegistroSueno registroSueno);
    void deleteRegistroSueno (Integer id);
}
