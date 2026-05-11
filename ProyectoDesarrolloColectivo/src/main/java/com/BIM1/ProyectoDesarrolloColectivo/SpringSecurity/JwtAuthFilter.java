package com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // El filtro NO se aplica a rutas públicas
        return path.startsWith("/login")   || path.startsWith("/register")|| path.startsWith("/css")     ||
                path.startsWith("/js")      || path.startsWith("/img")     || path.startsWith("/images")  ||
                path.startsWith("/webjars") || path.equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extraerToken(request);

        if (token != null && jwtUtil.validarToken(token)) {
            Integer usuarioId = jwtUtil.extraerUsuarioId(token);
            String rol = jwtUtil.extraerRol(token);
            String nombre = jwtUtil.extraerNombre(token);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(nombre, null, List.of(new SimpleGrantedAuthority("" + rol)));

            auth.setDetails(usuarioId);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String extraerToken(HttpServletRequest request) {
        // Primero intenta del header
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        // Si no, busca en el formulario
        return request.getParameter("_jwt");
    }
}