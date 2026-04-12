package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ejercicio")
    private Integer id_ejercicio;

    @Column(name = "nombre_ejercicio")
    @NotBlank(message = "el nombre del ejercicio no puede estar vacio")
    @Size(max = 60, message = "el nombre del ejercicio no puede pasar de 60 letras")
    private String nombre_ejercicio;

    @Column(name = "series_ejercicio")
    @NotNull(message = "las series del ejercicio son obligatorias")
    @Positive(message = "las series tienen que ser mayor que 1")
    @Max(value = 24, message = "la series no pueden ser mayores a 24")
    private int series_ejercicio;

    @Column(name = "repeticiones_ejercicio")
    @NotNull(message = "las repeticiones del ejercicio no pueden ser nulas")
    @Positive(message = "las repeticiones del ejercicio tiene que ser mayor que 1")
    @Max(value = 100, message = "la repeticiones del ejercicio no pueden ser mayores a 100")
    private int repeticiones_ejercicio;

    @Column(name = "tiempo_ejercicio")
    @NotNull(message = "el tiempo del ejercicio es obligatorio")
    @PositiveOrZero(message = "el tiempo del ejercicio no puede ser menor a 0")
    @Max(value = 3600, message = "el tiempo del ejercicio no puede ser mayor a una hora")
    private int tiempo_ejercicio;

    @Column(name = "descanso_ejercicio")
    @NotNull(message = "el descanso del ejercicio es obligatorio")
    @PositiveOrZero(message = "el descanso del ejercicio no puede ser menor a 0")
    @Max(value = 600, message = "el tiempo del descanso no puede ser mayor a 10 minutos")
    private int descanso_ejercicio;

    @Column(name = "fk_id_rutina")
    @NotNull(message = "el id de la llave foranea es obligatoria")
    @Positive(message = "el id de la rutina no puede ser menor o igual a 0")
    private Integer fk_id_rutina;

    public Integer getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(Integer id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public String getNombre_ejercicio() {
        return nombre_ejercicio;
    }

    public void setNombre_ejercicio(String nombre_ejercicio) {
        this.nombre_ejercicio = nombre_ejercicio;
    }

    public int getSeries_ejercicio() {
        return series_ejercicio;
    }

    public void setSeries_ejercicio(int series_ejercicio) {
        this.series_ejercicio = series_ejercicio;
    }

    public int getRepeticiones_ejercicio() {
        return repeticiones_ejercicio;
    }

    public void setRepeticiones_ejercicio(int repeticiones_ejercicio) {
        this.repeticiones_ejercicio = repeticiones_ejercicio;
    }

    public int getTiempo_ejercicio() {
        return tiempo_ejercicio;
    }

    public void setTiempo_ejercicio(int tiempo_ejercicio) {
        this.tiempo_ejercicio = tiempo_ejercicio;
    }

    public int getDescanso_ejercicio() {
        return descanso_ejercicio;
    }

    public void setDescanso_ejercicio(int descanso_ejercicio) {
        this.descanso_ejercicio = descanso_ejercicio;
    }

    public Integer getFk_id_rutina() {
        return fk_id_rutina;
    }

    public void setFk_id_rutina(Integer fk_id_rutina) {
        this.fk_id_rutina = fk_id_rutina;
    }
}
