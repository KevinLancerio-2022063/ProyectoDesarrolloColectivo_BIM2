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
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/error/")) {
            return true;
        }

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("/acceder");
            return false;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        boolean isAdminRoute = uri.startsWith("/home") ||
                uri.startsWith("/perfilNutricional") ||
                uri.startsWith("/rutina") ||
                uri.startsWith("/ejercicios") ||
                uri.startsWith("/libro") ||
                uri.startsWith("/libroEstadisticas");

        if (isAdminRoute) {
            if (usuario.getRol() == null || !usuario.getRol().equalsIgnoreCase("admin")) {
                response.sendRedirect("/index");
                return false;
            }
        }

        return true;
    }

}
