package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsuarioService {

    List<Usuario> getAllUsuarios();
    Usuario getUsuariosById (Integer id);
    Usuario saveUsuario (Usuario usuario) throws RuntimeException;
    Usuario updateUsuario (Integer id, Usuario usuario);
    void deleteUsuario (Integer id);
}
