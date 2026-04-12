package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public UsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void UsuarioValidar(Usuario usuario) {

        List<Usuario> usuarios = usuarioRepository.findAll();
        String correoUsu = usuario.getCorreoUsuario();

        correoUsu = correoUsu.trim().toLowerCase();

        if (!(correoUsu.endsWith("@gmail.com") || correoUsu.endsWith("@outlook.com") || correoUsu.endsWith("@yahoo.com"))) {
            throw new Exception(
                    "El correo debe ser @gmail.com, @outlook.com o @yahoo.com");
        }

        for (Usuario correoUsua : usuarios) {
            if (correoUsu.equals(correoUsua.getCorreoUsuario())){
                throw new IllegalArgumentException("El correo del usuario ya existe");
            }
        }
    }
}

