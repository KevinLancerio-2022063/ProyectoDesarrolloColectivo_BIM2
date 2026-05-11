package com.BIM1.ProyectoDesarrolloColectivo.SpringSecurity;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Mínimo 32 caracteres para HS256
    private final SecretKey clave = Keys.hmacShaKeyFor(
            "evoluciona-clave-secreta-32chars!!".getBytes()
    );

    // Token dura 8 horas
    private final long EXPIRACION = 1000L * 60 * 60 * 8;

    public String generarToken(Integer usuarioId, String nombre, String rol) {
        return Jwts.builder()
                .subject(String.valueOf(usuarioId))
                .claim("nombre", nombre)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRACION))
                .signWith(clave)
                .compact();
    }

    public Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(clave)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Integer extraerUsuarioId(String token) {
        return Integer.parseInt(extraerClaims(token).getSubject());
    }

    public String extraerRol(String token) {
        return extraerClaims(token).get("rol", String.class);
    }

    public String extraerNombre(String token) {
        return extraerClaims(token).get("nombre", String.class);
    }

    public boolean validarToken(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
