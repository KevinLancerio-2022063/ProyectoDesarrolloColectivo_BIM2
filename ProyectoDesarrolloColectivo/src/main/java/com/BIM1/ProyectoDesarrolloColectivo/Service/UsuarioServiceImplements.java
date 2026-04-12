package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Exceptions.Exception;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import com.BIM1.ProyectoDesarrolloColectivo.Validator.UsuarioValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UsuarioServiceImplements implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioValidator usuarioValidator;

    public UsuarioServiceImplements(UsuarioRepository usuarioRepository, UsuarioValidator usuarioValidator) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuariosById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {

        String correo = usuario.getCorreoUsuario().trim().toLowerCase();
        String contraseña = usuario.getContraseña();

        Usuario usuarioExistente = usuarioRepository.findByCorreoUsuario(correo);

        if (usuarioExistente != null) {

            if (usuarioExistente.getContraseña().equals(contraseña)) {
                throw new Exception("Bienvenido de nuevo " + usuarioExistente.getNombre_completo());
            }
            throw new IllegalArgumentException("El correo ya está registrado pero la contraseña es incorrecta intente de nuevo por favor"
            );
        }

        usuarioValidator.UsuarioValidar(usuario);

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        throw new Exception("Bienvenido " + nuevoUsuario.getNombre_completo() + ", tu cuenta fue creada correctamente");
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("El usuario no se ha encontrado con id: " + id));

        usuarioValidator.UsuarioValidar(usuario);
        usuarioExistente.setNombre_completo(usuario.getNombre_completo());
        usuarioExistente.setCorreoUsuario(usuario.getCorreoUsuario());
        usuarioExistente.setContraseña(usuario.getContraseña());

        return usuarioRepository.save(usuarioExistente);
    }
}