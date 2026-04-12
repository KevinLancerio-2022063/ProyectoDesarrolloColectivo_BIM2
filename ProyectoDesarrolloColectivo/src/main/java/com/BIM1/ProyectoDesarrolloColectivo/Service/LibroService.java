package com.BIM1.ProyectoDesarrolloColectivo.Service;

import com.BIM1.ProyectoDesarrolloColectivo.Entity.Libro;

import java.util.List;

public interface LibroService {
    List<Libro> getAListLibro();
    Libro getLibroById(Integer id);
    Libro saveLibro(Libro libro) throws RuntimeException;
    Libro updateLibro(Integer id, Libro libro );
    void deleteLibro(Integer id);
}
