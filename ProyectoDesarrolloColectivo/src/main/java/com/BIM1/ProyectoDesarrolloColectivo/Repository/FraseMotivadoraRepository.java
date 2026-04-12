package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.FraseMotivadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraseMotivadoraRepository extends JpaRepository<FraseMotivadora,Integer> {
    boolean existsByTextoAndAutor(
            String texto,
            String autor
    );
}
