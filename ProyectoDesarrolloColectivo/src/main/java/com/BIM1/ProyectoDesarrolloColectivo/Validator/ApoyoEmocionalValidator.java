package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.ApoyoEmocionalRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class ApoyoEmocionalValidator {
    public final UsuarioRepository usuarioRepository;
    public final ApoyoEmocionalRepository apoyoEmocionalRepository;

    public ApoyoEmocionalValidator(UsuarioRepository usuarioRepository, ApoyoEmocionalRepository apoyoEmocionalRepository) {
        this.usuarioRepository = usuarioRepository;
        this.apoyoEmocionalRepository = apoyoEmocionalRepository;
    }

    public void ApoyoEmocionalValidacion(ApoyoEmocional apoyoEmocional){
        if (apoyoEmocional.getTitulo().trim().length() > 50){
            throw new CustomException("El nombre del autor es demasiado largo, solo se permiten 50 digitos");
        }

        if (apoyoEmocional.getCategoria().trim().length() >50){
            throw new CustomException("El nombre del autor es demasiado largo, solo se permiten 50 digitos");
        }

        if (!(apoyoEmocional.getNivelAnimo().contains("Mal") ||
                apoyoEmocional.getNivelAnimo().contains("Bien") ||
                apoyoEmocional.getNivelAnimo().contains("Mas o menos"))){
            throw new CustomException("Nivel de ánimo inválido. Solo se permite: Mal, Bien o Mas o menos");
        }

        if (!usuarioRepository.existsById(apoyoEmocional.getUsuario().getId_usuario())){
            throw new CustomException("Usuario inexistente");
        }

        if (apoyoEmocionalRepository.existsByTituloAndCategoriaAndContenidoAndNivelAnimoAndUsuario(
               apoyoEmocional.getTitulo(),apoyoEmocional.getCategoria(),apoyoEmocional.getContenido(),
               apoyoEmocional.getNivelAnimo(),apoyoEmocional.getUsuario()
        )){
            throw new CustomException("Ya existe un registro con estos datos");
        }
    }

    public void ApoyoEmocionalValidacionId(Integer id){
        if (!apoyoEmocionalRepository.existsById(id)){
            throw new CustomException("apoyo Emocional no encontrado");
        }
    }
}
