package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.EntradaDiarioRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntradaDiarioServiceImplements implements EntradaDiarioService {

    private com.BIM1.ProyectoDesarrolloColectivo.Repository.EntradaDiarioRepository entradaDiarioRepository = null;

    public EntradaDiarioServiceImplements(EntradaDiarioRepository entradaDiarioRepository) {
        this.entradaDiarioRepository = entradaDiarioRepository;
    }

    @Override
    public List<com.BIM1.ProyectoDesarrolloColectivo.Entity.EntradaDiario> getAListEntradaDiario() {
        return entradaDiarioRepository.findAll();
    }


    @Override
    public EntradaDiario getEntradaDiarioById(Integer id) {
        return entradaDiarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "EntradaDiario"));
        // La funcion de lambda en este apartado facilita la implementacion de interfaces ya que antes de la flecha van parametros y luego va el cuerpo de la funcion
    }
    @Override
    public EntradaDiario saveEntradaDiario(EntradaDiario entradaDiario) throws RuntimeException {
        return entradaDiarioRepository.save(entradaDiario);
    }

    @Override
    public EntradaDiario updateEntradaDiario(Integer id, EntradaDiario entradaDiario) {
        EntradaDiario existing = entradaDiarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "EntradaDiario"));
        existing.setFecha(entradaDiario.getFecha());
        existing.setPlan_mañana(entradaDiario.getPlan_mañana());
        existing.setReflexion(entradaDiario.getReflexion());
        existing.setFk_id_usuario(entradaDiario.getFk_id_usuario());
        return entradaDiarioRepository.save(existing);
    }
    @Override
    public void deleteEntradaDiario(Integer id) {
        entradaDiarioRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "EntradaDiario"));
        entradaDiarioRepository.deleteById(id);
    }
}
