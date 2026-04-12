package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ApoyoEmocional;
import com.BIM1.ProyectoDesarrolloColectivo.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApoyoEmocionalRepository extends JpaRepository<ApoyoEmocional,Integer> {

    boolean existsByTituloAndCategoriaAndContenidoAndNivelAnimoAndUsuario(
            String titulo,
            String categoria,
            String contenido,
            String nivelAnimo,
            Usuario usuario
    );
}
