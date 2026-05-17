package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.ObjetivoMeditacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetivoMeditacionRepository extends JpaRepository<ObjetivoMeditacion, Integer> {

    List<ObjetivoMeditacion> findByFkIdUsuario(Integer fkIdUsuario);
}
