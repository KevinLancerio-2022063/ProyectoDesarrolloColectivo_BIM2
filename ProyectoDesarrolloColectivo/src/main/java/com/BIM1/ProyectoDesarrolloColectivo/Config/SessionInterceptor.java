package com.BIM1.ProyectoDesarrolloColectivo.Config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        // Si hay sesión activa y tiene usuarioId, deja pasar
        if (session != null && session.getAttribute("usuarioId") != null) {
            return true;
        }

        // Sin sesión se va a redirigir al login
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}