package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "RegistroMeditacion")
public class RegistroMeditacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Registro_Meditacion")
    private Integer id_registro_meditacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_Meditacion")
    @NotNull(message = "Seleccione un tipo de meditacion")
    private TipoMeditacion tipo_meditacion;

    @Column(name = "duracion_Minutos")
    @NotNull(message = "Seleccione la cantidad de minutos")
    private Integer duracion_minutos;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_Dificultad")
    @NotNull(message = "Seleccione un nivel de dificultad")
    private NivelDificultad nivel_dificultad;

    @Column(name = "fecha_Registro")
    private LocalDateTime fecha_registro;

    @Column(name = "fk_id_Usuario")
    @NotNull(message = "la llave foranea no puede estar vacia")
    private Integer fk_id_usuario;

    public enum TipoMeditacion {
        Guiada, Respiracion, Mindfulness, BodyScan
    }

    public enum NivelDificultad {
        Principiante, Intermedio, Avanzado
    }

    public Integer getId_registro_meditacion() {
        return id_registro_meditacion;
    }

    public void setId_registro_meditacion(Integer id_registro_meditacion) {
        this.id_registro_meditacion = id_registro_meditacion;
    }

    public TipoMeditacion getTipo_meditacion() {
        return tipo_meditacion;
    }

    public void setTipo_meditacion(TipoMeditacion tipo_meditacion) {
        this.tipo_meditacion = tipo_meditacion;
    }

    public Integer getDuracion_minutos() {
        return duracion_minutos;
    }

    public void setDuracion_minutos(Integer duracion_minutos) {
        this.duracion_minutos = duracion_minutos;
    }

    public NivelDificultad getNivel_dificultad() {
        return nivel_dificultad;
    }

    public void setNivel_dificultad(NivelDificultad nivel_dificultad) {
        this.nivel_dificultad = nivel_dificultad;
    }

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public Integer getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(Integer fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }
}