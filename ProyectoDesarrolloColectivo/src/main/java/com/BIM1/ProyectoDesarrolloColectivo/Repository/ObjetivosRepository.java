package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Objetivos;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface ObjetivosRepository extends JpaRepository<Objetivos,Integer> {
    boolean existsByTituloObjetivoAndDescripcionObjetivoAndEstadoObjetivoAndFechaObjetivoAndUsuarioAndFraseMotivadora(
            String tituloObjetivo,
            String descripcionObjetivo,
            String estadoObjetivo,
            LocalDate fechaObjetivo,
            Usuario usuario,
            FraseMotivadora fraseMotivadora
    );

    @Query("SELECT o FROM Objetivos o WHERE o.usuario.id_usuario = :id_usuario")
    List<Objetivos> findByUsuarioId(@Param("id_usuario") Integer id_usuario);
}
