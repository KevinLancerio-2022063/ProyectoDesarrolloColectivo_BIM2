package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class RegistroSuenoValidator {

    private final UsuarioRepository usuarioRepository;

    public RegistroSuenoValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void RegistroSuenoValidar(RegistroSueno registroSueno) {

        if (!usuarioRepository.existsById(registroSueno.getFk_id_usuario())) {
            throw new Exception("No se ha encontrado una registro sueño con id: " + registroSueno.getFk_id_usuario());
        }
    }
}


