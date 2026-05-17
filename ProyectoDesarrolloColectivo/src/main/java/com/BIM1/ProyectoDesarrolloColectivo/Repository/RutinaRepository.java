package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Integer> {

    @Query("SELECT r FROM Rutina r WHERE r.fk_id_usuario = :idUsuario")
    List<Rutina> findRutinasByUsuario(@Param("idUsuario") Integer idUsuario);
}