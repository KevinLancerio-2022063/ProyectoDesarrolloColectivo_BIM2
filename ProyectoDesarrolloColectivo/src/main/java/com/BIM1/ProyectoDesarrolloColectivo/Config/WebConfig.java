package com.BIM1.ProyectoDesarrolloColectivo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                // Proteger TODAS las rutas de las entidades
                .addPathPatterns("/**")
                // excepto las públicas: login, registro y estáticos
                .excludePathPatterns(
                        "/acceder",         // vista login/registro
                        "/login",           // POST login
                        "/registro",        // POST registro
                        "/index",           // home de usuario común (pública una vez logueado)
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/error/**",
                        "/api/usuarios",
                        "/api/usuarios/**"
                );
    }
}