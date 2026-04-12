package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class ObjetivoMeditacionValidator {

    private final UsuarioRepository usuarioRepository;

    public ObjetivoMeditacionValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void ObjetivoMeditacionValidar(ObjetivoMeditacion objetivoMeditacion) {

        if (!usuarioRepository.existsById(objetivoMeditacion.getFk_id_usuario())){
            throw new Exception("No se ha encontrado un objetivo meditación con el id: "+ objetivoMeditacion.getFk_id_usuario());
        }
    }
}
