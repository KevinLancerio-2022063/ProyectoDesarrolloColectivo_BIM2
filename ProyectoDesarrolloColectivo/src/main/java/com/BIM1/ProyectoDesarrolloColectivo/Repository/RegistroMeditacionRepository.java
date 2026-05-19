package com.BIM1.ProyectoDesarrolloColectivo.Repository;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroMeditacionRepository extends JpaRepository<RegistroMeditacion, Integer> {

    List<RegistroMeditacion> findByFkIdUsuario(Integer fkIdUsuario);

}