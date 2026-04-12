package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroSueno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroSuenoRepository extends JpaRepository<RegistroSueno, Integer> {

}
