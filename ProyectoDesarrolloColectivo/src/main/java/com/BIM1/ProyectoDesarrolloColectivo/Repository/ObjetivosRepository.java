package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;


public interface ObjetivosRepository extends JpaRepository<Objetivos,Integer> {
    boolean existsByDescripcionObjetivoAndEstadoObjetivoAndFechaObjetivoAndUsuarioAndFraseMotivadora(
            String descripcionObjetivo,
            String estadoObjetivo,
            LocalDate fechaObjetivo,
            Usuario usuario,
            FraseMotivadora fraseMotivadora
    );
}
