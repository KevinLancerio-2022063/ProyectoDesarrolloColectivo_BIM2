package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroSuenoRepository extends JpaRepository<RegistroSueno, Integer> {

    List<RegistroSueno> findByFkIdUsuario(Integer fkIdUsuario);
}
