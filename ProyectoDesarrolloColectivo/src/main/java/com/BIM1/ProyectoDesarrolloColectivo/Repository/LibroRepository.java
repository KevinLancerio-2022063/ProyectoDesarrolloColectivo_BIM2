package com.BIM1.ProyectoDesarrolloColectivo.Repository;


import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
}
