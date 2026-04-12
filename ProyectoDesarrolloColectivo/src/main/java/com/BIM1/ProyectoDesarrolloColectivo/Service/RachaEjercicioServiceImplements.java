package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaEjercicio;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.RachaEjercicioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RachaEjercicioServiceImplements implements RachaEjercicioService {

    private final RachaEjercicioRepository repository;

    // Ordena por fecha: primero las más recientes (maneja nulls al final)
    private static final Comparator<RachaEjercicio> POR_FECHA_DESC = new Comparator<RachaEjercicio>() {
        @Override
        public int compare(RachaEjercicio a, RachaEjercicio b) {
            LocalDate fa = (a != null) ? a.getFecha() : null;
            LocalDate fb = (b != null) ? b.getFecha() : null; if (fa == fb) return 0; if (fa == null) return 1; if (fb == null) return -1;
            return fb.compareTo(fa);
        }
    };

    public RachaEjercicioServiceImplements(RachaEjercicioRepository repository) {
        this.repository = repository;
    }

    // Lista las rachas de un usuario y las devuelve ordenadas por fecha (desc)
    @Override
    public List<RachaEjercicio> getRachasByUsuario(Integer idUsuario) {
        List<RachaEjercicio> todas = repository.findAll();
        if (todas == null || todas.isEmpty()) {
            return Collections.emptyList();
        }
        List<RachaEjercicio> resultado = new ArrayList<>();
        for (RachaEjercicio r : todas) { boolean mismoUsuario = (r != null && r.getFkIdUsuario() != null && r.getFkIdUsuario().equals(idUsuario));
            if (mismoUsuario) { resultado.add(r);
            }
        }
        Collections.sort(resultado, POR_FECHA_DESC);
        return resultado;
    }
    // Lista rachas del usuario dentro del rango [inicio, fin] y ordena por fecha (desc)
    @Override
    public List<RachaEjercicio> getRachasByUsuarioAndRango(Integer idUsuario, LocalDate inicio, LocalDate fin) {
        List<RachaEjercicio> todas = repository.findAll();
        if (todas == null || todas.isEmpty()) {
            return Collections.emptyList();
        }

        List<RachaEjercicio> resultado = new ArrayList<>();
        for (RachaEjercicio r : todas) {
            if (r == null) continue;
            Integer fk = r.getFkIdUsuario();
            LocalDate fecha = r.getFecha(); boolean mismoUsuario = (fk != null && fk.equals(idUsuario));
            boolean enRango = (fecha != null && !fecha.isBefore(inicio) && !fecha.isAfter(fin));

            if (mismoUsuario && enRango) {
                resultado.add(r);
            }
        }
        Collections.sort(resultado, POR_FECHA_DESC);
        return resultado;
    }

    // Guarda una racha desde el body y recarga para traer diasConsecutivos que calcula el trigger
    @Override
    public RachaEjercicio saveRacha(RachaEjercicio racha) throws RuntimeException {
        RachaEjercicio saved = repository.save(racha);
        return repository.findById(saved.getIdRachaEjercicio()).orElse(saved);
    }

    // Crea una racha con idUsuario + fecha (POST corto) y recarga por ID
    @Override
    public RachaEjercicio addRacha(Integer idUsuario, LocalDate fecha) throws RuntimeException {
        RachaEjercicio r = new RachaEjercicio();
        r.setFkIdUsuario(idUsuario);
        r.setFecha(fecha);

        RachaEjercicio saved = repository.save(r);
        return repository.findById(saved.getIdRachaEjercicio()).orElse(saved);
    }
}