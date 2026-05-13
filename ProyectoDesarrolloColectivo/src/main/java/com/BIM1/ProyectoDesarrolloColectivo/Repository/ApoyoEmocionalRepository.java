package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApoyoEmocionalRepository extends JpaRepository<ApoyoEmocional, Integer> {

    boolean existsByTituloAndCategoriaAndContenidoAndNivelAnimoAndUsuario(
            String titulo,
            String categoria,
            String contenido,
            String nivelAnimo,
            Usuario usuario
    );

    @Query("SELECT a FROM ApoyoEmocional a WHERE a.usuario.id_usuario = :idUsuario")
    List<ApoyoEmocional> findByIdUsuario(@Param("idUsuario") Integer idUsuario);
}
