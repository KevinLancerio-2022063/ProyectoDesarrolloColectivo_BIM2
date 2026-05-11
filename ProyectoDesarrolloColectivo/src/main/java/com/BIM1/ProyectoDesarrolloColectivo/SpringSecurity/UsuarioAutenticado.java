package com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioAutenticado {

    private final UsuarioRepository usuarioRepository;

    public UsuarioAutenticado(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Obtiene el usuarioId desde los detalles del token
    public Integer getId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof UsernamePasswordAuthenticationToken token) {
            Object details = token.getDetails();
            if (details instanceof Integer) {
                return (Integer) details;
            }
        }

        return getUsuarioDeBD().getId_usuario();
    }

    // Obtiene el rol desde las authorities del token
    public String getRol() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElse("USER").replace("ROLE", "");
    }

    public String getNombre() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    // Solo se llama si no hay token (fallback)
    private Usuario getUsuarioDeBD() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usuarioRepository.findByCorreoUsuario(auth.getName());
    }
}