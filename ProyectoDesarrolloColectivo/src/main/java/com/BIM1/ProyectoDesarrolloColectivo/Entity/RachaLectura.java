package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "Racha_lectura")
public class RachaLectura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_racha_lectura")
    private Integer idRachaLectura;

    @Min(value = 0, message = "Los días consecutivos no pueden ser negativos")
    @Column(name = "dias_consecutivos")
    private Integer diasConsecutivos;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser pasada")
    @Column(name = "fecha")
    private LocalDate fecha;

    @NotNull(message = "El usuario es obligatorio")
    @Column(name = "fk_id_usuario")
    private Integer fkIdUsuario;

    public Integer getIdRachaLectura() {
        return idRachaLectura;
    }

    public void setIdRachaLectura(Integer idRachaLectura) {
        this.idRachaLectura = idRachaLectura;
    }

    public Integer getDiasConsecutivos() {
        return diasConsecutivos;
    }

    public void setDiasConsecutivos(Integer diasConsecutivos) {
        this.diasConsecutivos = diasConsecutivos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Integer getFkIdUsuario() {
        return fkIdUsuario;
    }

    public void setFkIdUsuario(Integer fkIdUsuario) {
        this.fkIdUsuario = fkIdUsuario;
    }
}