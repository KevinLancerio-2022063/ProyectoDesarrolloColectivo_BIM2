package com.BIM1.ProyectoDesarrolloColectivo.Config;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // Estáticos y errores: siempre pasan porque si no no va a funcionar los estilos, errores, y js
        if (uri.startsWith("/css/") || uri.startsWith("/js/")  || uri.startsWith("/error/")) {
            return true;
        }

        // Sin sesión → login
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("/acceder");
            return false;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        // La BD guarda "ADMIN" / "USER" en mayúsculas
        String rol = usuario.getRol() != null ? usuario.getRol().toUpperCase() : "USER";

        // Rutas exclusivas de ADMIN
        boolean esRutaAdmin =
                uri.startsWith("/libro") ||
                        uri.startsWith("/libroEstadisticas") ||
                        uri.startsWith("/perfilNutricional") ||
                        uri.startsWith("/rutina") ||
                        uri.startsWith("/ejercicios");

        if (esRutaAdmin && !rol.equals("ADMIN")) {
            response.sendRedirect("/index");
            return false;
        }

        return true;
    }
}