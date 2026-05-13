package com.BIM1.ProyectoDesarrolloColectivo.Repository;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaDiarioRepository extends JpaRepository<EntradaDiario, Integer> {

    List<EntradaDiario> findByFkIdUsuario(Integer fkIdUsuario);

}