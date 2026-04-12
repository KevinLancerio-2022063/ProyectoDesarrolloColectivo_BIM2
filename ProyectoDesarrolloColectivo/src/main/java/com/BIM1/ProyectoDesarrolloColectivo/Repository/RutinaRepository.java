package com.BIM1.ProyectoDesarrolloColectivo.Repository;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Integer> {

}
