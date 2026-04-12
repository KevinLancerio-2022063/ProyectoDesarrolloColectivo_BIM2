package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaLectura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RachaLecturaRepository extends JpaRepository<RachaLectura, Integer> {
}