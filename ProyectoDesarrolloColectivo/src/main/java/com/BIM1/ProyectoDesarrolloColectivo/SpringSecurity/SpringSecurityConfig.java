package com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    private final UsuarioSecurityService usuarioSecurityService;
    private final JwtAuthFilter          jwtAuthFilter;

    public SpringSecurityConfig(UsuarioSecurityService usuarioSecurityService,
                                JwtAuthFilter jwtAuthFilter) {
        this.usuarioSecurityService = usuarioSecurityService;
        this.jwtAuthFilter          = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Todas las rutas públicas — GET y POST incluidos
                        .requestMatchers(
                                "/login",         // GET mostrar login
                                "/login/**",      // POST procesar login y logout
                                "/register",      // GET mostrar register
                                "/register/**",   // POST procesar register
                                "/css/**",
                                "/js/**",
                                "/img/**",
                                "/images/**",
                                "/webjars/**",
                                "/favicon.ico"
                        ).permitAll()

                        // Solo ADMIN puede gestionar usuarios
                        .requestMatchers("/usuarios/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                )
                // Deshabilitamos el login de Spring Security
                // porque lo manejamos manualmente con JWT
                .formLogin(form -> form.disable())
                .logout(logout -> logout.disable())
                // Sin CSRF porque usamos JWT stateless
                .csrf(csrf -> csrf.disable())
                // Filtro JWT se ejecuta antes del filtro de autenticación estándar
                .addFilterBefore(jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter.class)
                // Si no hay token y la ruta requiere auth, redirigir al login
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/login?error=acceso");
                        })
                );

        return http.build();
    }
}



