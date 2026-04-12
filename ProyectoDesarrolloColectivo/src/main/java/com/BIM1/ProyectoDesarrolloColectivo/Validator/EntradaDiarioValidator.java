package com.BIM1.ProyectoDesarrolloColectivo.Validator;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EntradaDiarioValidator {

    public void validate(EntradaDiario entradaDiario) {

        // Validación: la fecha no puede ser nula
        if (entradaDiario.getFecha() == null) {
            throw new IllegalArgumentException("La fecha no puede ser nula.");
        }

        // Validación: la fecha no puede ser futura
        if (entradaDiario.getFecha().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha no puede ser una fecha futura.");
        }

        // Validación: que_paso no puede ser nulo ni vacío
        if (entradaDiario.getQue_paso() == null || entradaDiario.getQue_paso().isBlank()) {
            throw new IllegalArgumentException("El campo 'que_paso' no puede estar vacío.");
        }

        // Validación: que_paso no puede exceder los 500 caracteres
        if (entradaDiario.getQue_paso().length() > 500) {
            throw new IllegalArgumentException("El campo 'que_paso' no puede exceder los 500 caracteres.");
        }

        // Validación: plan_mañana no puede exceder los 500 caracteres si está presente
        if (entradaDiario.getPlan_mañana() != null && entradaDiario.getPlan_mañana().length() > 500) {
            throw new IllegalArgumentException("El campo 'plan_mañana' no puede exceder los 500 caracteres.");
        }

        // Validación: reflexion no puede exceder los 1000 caracteres si está presente
        if (entradaDiario.getReflexion() != null && entradaDiario.getReflexion().length() > 1000) {
            throw new IllegalArgumentException("El campo 'reflexion' no puede exceder los 1000 caracteres.");
        }

        // Validación: fk_id_usuario no puede ser nulo ni negativo
        if (entradaDiario.getFk_id_usuario() == null) {
            throw new IllegalArgumentException("El campo 'fk_id_usuario' no puede ser nulo.");
        }

        if (entradaDiario.getFk_id_usuario() <= 0) {
            throw new IllegalArgumentException("El campo 'fk_id_usuario' debe ser un número positivo.");
        }
    }
}