package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RegistroMeditacion;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.RegistroMeditacionRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroMeditacionServiceImplements implements RegistroMeditacionService {

    private final RegistroMeditacionRepository registroMeditacionRepository;

    public RegistroMeditacionServiceImplements(RegistroMeditacionRepository registroMeditacionRepository) {
        this.registroMeditacionRepository = registroMeditacionRepository;
    }

    @Override
    public List<RegistroMeditacion> getAListRegistroMeditacion(){
        return registroMeditacionRepository.findAll();
    }

    @Override
    public RegistroMeditacion getRegistroMeditacionById(Integer id){
        return registroMeditacionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "RegistroMeditacion"));
        // La funcion de lambda en este apartado facilita la implementacion de interfaces ya que antes de la flecha van parametros y luego va el cuerpo de la funcion
    }

    @Override
    public RegistroMeditacion saveRegistroMeditacion(RegistroMeditacion registroMeditacion) throws RuntimeException {
        return registroMeditacionRepository.save(registroMeditacion);
    }

    @Override
    public RegistroMeditacion updateRegistroMeditacion(Integer id, RegistroMeditacion registroMeditacion){
        RegistroMeditacion existing = registroMeditacionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "RegistroMeditacion"));
        existing.setTipo_meditacion(registroMeditacion.getTipo_meditacion());
        existing.setDuracion_minutos(registroMeditacion.getDuracion_minutos());
        existing.setNivel_dificultad(registroMeditacion.getNivel_dificultad());
        existing.setFecha_registro(registroMeditacion.getFecha_registro());
        existing.setFk_id_usuario(registroMeditacion.getFk_id_usuario());
        return registroMeditacionRepository.save(existing);
    }

    @Override
    public void deleteRegistroMeditacion(Integer id){
        registroMeditacionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id, "RegistroMeditacion"));
        registroMeditacionRepository.deleteById(id);
    }
}