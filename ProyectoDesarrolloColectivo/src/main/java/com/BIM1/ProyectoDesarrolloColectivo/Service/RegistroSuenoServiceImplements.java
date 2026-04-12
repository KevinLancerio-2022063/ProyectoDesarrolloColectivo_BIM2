package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.RegistroSuenoRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.RegistroSuenoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroSuenoServiceImplements implements RegistroSuenoService {

    private final RegistroSuenoRepository registroSuenoRepository;
    private final RegistroSuenoValidator  registroSuenoValidator;

    public RegistroSuenoServiceImplements (RegistroSuenoRepository registroSuenoRepository, RegistroSuenoValidator registroSuenoValidator) {
        this.registroSuenoRepository = registroSuenoRepository;
        this.registroSuenoValidator = registroSuenoValidator;
    }

    @Override
    public List<RegistroSueno> getAllRegistrosSuenos() {
        return registroSuenoRepository.findAll();
    }

    @Override
    public RegistroSueno getRegistrosSuenosById (Integer id) {
        return registroSuenoRepository.findById(id).orElse(null);
    }

    @Override
    public RegistroSueno saveRegistroSueno (RegistroSueno registroSueno) throws RuntimeException {
        registroSuenoValidator.RegistroSuenoValidar(registroSueno);
        return registroSuenoRepository.save(registroSueno);
    }

    @Override
    public void deleteRegistroSueno (Integer id) {
        registroSuenoRepository.deleteById(id);
    }

    @Override
    public RegistroSueno updateRegistroSueno (Integer id, RegistroSueno registroSueno) {

        RegistroSueno registroSuenoExistente = registroSuenoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El registro sueño no se ha encontrado con id: " + id));

        registroSuenoValidator.RegistroSuenoValidar(registroSueno);
        registroSuenoExistente.setFecha_sueño(registroSueno.getFecha_sueño());
        registroSuenoExistente.setHoras_dormidas(registroSueno.getHoras_dormidas());
        registroSuenoExistente.setCalidad_sueño(registroSueno.getCalidad_sueño());
        registroSuenoExistente.setFk_id_usuario(registroSueno.getFk_id_usuario());


        return registroSuenoRepository.save(registroSuenoExistente);
    }

}
