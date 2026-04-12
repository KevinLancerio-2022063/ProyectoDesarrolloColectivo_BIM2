package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.CustomException;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.FraseMotivadoraRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.ObjetivosRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class ObjetivosValidator {
    public  final ObjetivosRepository objetivosRepository;
    private final UsuarioRepository usuarioRepository;
    private final FraseMotivadoraRepository fraseRepository;

    public ObjetivosValidator(ObjetivosRepository objetivosRepository, UsuarioRepository usuarioRepository, FraseMotivadoraRepository fraseRepository) {
        this.objetivosRepository = objetivosRepository;
        this.usuarioRepository = usuarioRepository;
        this.fraseRepository = fraseRepository;
    }

    public void ObjetivosValidaciones(Objetivos objetivos){
        if (objetivos.getDescripcionObjetivo().trim().length() > 150){
            throw new CustomException("texto muy grande,caracteres maximos 150");
        }

        if (objetivos.getEstadoObjetivo().trim().length() > 45){
            throw new CustomException("texto muy grande,caracteres maximos 45");
        }

        if (objetivosRepository.existsByDescripcionObjetivoAndEstadoObjetivoAndFechaObjetivoAndUsuarioAndFraseMotivadora(
                objetivos.getDescripcionObjetivo(),objetivos.getEstadoObjetivo(),objetivos.getFechaObjetivo(),
                objetivos.getUsuario(),objetivos.getFraseMotivadora()
        )){
            throw new CustomException("Ya existe un Objetivo con los mismos datos");
        }

        if (!usuarioRepository.existsById(objetivos.getUsuario().getId_usuario())){
            throw new CustomException("Usuario inexistente");
        }

        if (!fraseRepository.existsById(objetivos.getFraseMotivadora().getIdFraseMotivadora())){
            throw new CustomException("Frase inexistente");
        }
    }

    public void ObjetivosValidacionesId(Integer id){
        if (!objetivosRepository.existsById(id)){
            throw new CustomException("Objetivo no encontrado");
        }
    }
}
