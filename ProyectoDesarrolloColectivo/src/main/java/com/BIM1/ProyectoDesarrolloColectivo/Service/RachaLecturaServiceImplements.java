package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.RachaLectura;
import com.BIM1.ProyectoDesarrolloColectivo.Repository.RachaLecturaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RachaLecturaServiceImplements implements RachaLecturaService {

    private final RachaLecturaRepository repository;

    // Ordena por fecha: primero las más recientes (si no hay fecha, se va al final)
    private static final Comparator<RachaLectura> POR_FECHA_DESC = new Comparator<RachaLectura>() {
        @Override
        public int compare(RachaLectura a, RachaLectura b) { LocalDate fa = (a != null) ? a.getFecha() : null; LocalDate fb = (b != null) ? b.getFecha() : null;
            if (fa == fb) return 0; if (fa == null) return 1; if (fb == null) return -1;
            return fb.compareTo(fa);
        }
    };

    public RachaLecturaServiceImplements(RachaLecturaRepository repository) {
        this.repository = repository;
    }

    // Lista las rachas de un usuario y las devuelve ordenadas por fecha (desc)
    @Override
    public List<RachaLectura> getRachasByUsuario(Integer idUsuario) {
        List<RachaLectura> todas = repository.findAll();
        if (todas == null || todas.isEmpty()) {
            return Collections.emptyList();
        }

        List<RachaLectura> resultado = new ArrayList<>();
        for (RachaLectura r : todas) {
            boolean mismoUsuario = (r != null && r.getFkIdUsuario() != null && r.getFkIdUsuario().equals(idUsuario));
            if (mismoUsuario) {
                resultado.add(r);
            }
        }

        Collections.sort(resultado, POR_FECHA_DESC);
        return resultado;
    }

    // Lista rachas del usuario dentro del rango [inicio, fin] y ordena por fecha (desc)
    @Override
    public List<RachaLectura> getRachasByUsuarioAndRango(Integer idUsuario, LocalDate inicio, LocalDate fin) {
        List<RachaLectura> todas = repository.findAll();
        if (todas == null || todas.isEmpty()) {
            return Collections.emptyList();
        }

        List<RachaLectura> resultado = new ArrayList<>();
        for (RachaLectura r : todas) {
            if (r == null) continue;
            Integer fk = r.getFkIdUsuario();
            LocalDate fecha = r.getFecha(); boolean mismoUsuario = (fk != null && fk.equals(idUsuario));
            boolean enRango = (fecha != null && !fecha.isBefore(inicio) && !fecha.isAfter(fin)); // [inicio, fin]
            if (mismoUsuario && enRango) {
                resultado.add(r);
            }
        }

        Collections.sort(resultado, POR_FECHA_DESC);
        return resultado;
    }

    // Guarda una racha desde el body y recarga para traer diasConsecutivos que calcula el trigger
    @Override
    public RachaLectura saveRacha(RachaLectura racha) throws RuntimeException {
        RachaLectura saved = repository.save(racha);
        return repository.findById(saved.getIdRachaLectura()).orElse(saved);
    }

    // Crea una racha con idUsuario + fecha (POST corto) y recarga por ID
    @Override
    public RachaLectura addRacha(Integer idUsuario, LocalDate fecha) throws RuntimeException {

        // Obtener rachas del usuario (ya vienen ordenadas por fecha DESC)
        List<RachaLectura> rachasUsuario = getRachasByUsuario(idUsuario);

        int dias = 1; // por defecto empieza en 1

        if (!rachasUsuario.isEmpty()) {
            RachaLectura ultima = rachasUsuario.get(0);

            if (ultima.getFecha() != null &&
                    ultima.getFecha().plusDays(1).equals(fecha)) {
                dias = ultima.getDiasConsecutivos() + 1;
            }
        }

        RachaLectura racha = new RachaLectura();
        racha.setFkIdUsuario(idUsuario);
        racha.setFecha(fecha);
        racha.setDiasConsecutivos(dias);

        return repository.save(racha);
    }

}