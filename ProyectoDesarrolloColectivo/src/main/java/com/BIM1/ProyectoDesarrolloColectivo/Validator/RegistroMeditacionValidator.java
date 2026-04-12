package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class RegistroMeditacionValidator {

    public void validate(RegistroMeditacion registroMeditacion) {

        // Validación: tipo_meditacion no puede ser nulo
        if (registroMeditacion.getTipo_meditacion() == null) {
            throw new IllegalArgumentException("El tipo de meditación no puede ser nulo.");
        }

        // Validación: duracion_minutos no puede ser nulo
        if (registroMeditacion.getDuracion_minutos() == null) {
            throw new IllegalArgumentException("La duración en minutos no puede ser nula.");
        }

        // Validación: duracion_minutos debe ser un valor positivo
        if (registroMeditacion.getDuracion_minutos() <= 0) {
            throw new IllegalArgumentException("La duración en minutos debe ser un número positivo.");
        }

        // Validación: duracion_minutos no puede exceder 200 minutos
        if (registroMeditacion.getDuracion_minutos() > 200) {
            throw new IllegalArgumentException("La duración en minutos no puede exceder las 8 horas (480 minutos).");
        }

        // Validación: nivel_dificultad no puede ser nulo
        if (registroMeditacion.getNivel_dificultad() == null) {
            throw new IllegalArgumentException("El nivel de dificultad no puede ser nulo.");
        }

        // Validación: fecha_registro no puede ser una fecha futura
        if (registroMeditacion.getFecha_registro() != null &&
                registroMeditacion.getFecha_registro().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de registro no puede ser una fecha futura.");
        }

        // Validación: fk_id_usuario no puede ser nulo ni negativo
        if (registroMeditacion.getFk_id_usuario() == null) {
            throw new IllegalArgumentException("El campo 'fk_id_usuario' no puede ser nulo.");
        }

        if (registroMeditacion.getFk_id_usuario() <= 0) {
            throw new IllegalArgumentException("El campo 'fk_id_usuario' debe ser un número positivo.");
        }
    }
}