package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "objetivoMeditacion")
public class ObjetivoMeditacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo_meditacion")
    private Integer id_objetivo_meditacion;

    @NotBlank(message = "El tiempo objetivo no puede estar vacío")
    @Column(name = "tiempo_objetivo")
    private String tiempo_objetivo;

    @NotNull(message = "Los días objetivo no pueden estar vacíos")
    @Positive(message = "Los días objetivo no puede ser menor que 1")
    @Column(name = "dias_objetivo")
    private Integer dias_objetivo;

    @Column(name = "fk_id_usuario")
    private Integer fkIdUsuario;

    public Integer getId_objetivo_meditacion() {
        return id_objetivo_meditacion;
    }

    public void setId_objetivo_meditacion(Integer id_objetivo_meditacion) {
        this.id_objetivo_meditacion = id_objetivo_meditacion;
    }

    public String getTiempo_objetivo() {
        return tiempo_objetivo;
    }

    public void setTiempo_objetivo(String tiempo_objetivo) {
        this.tiempo_objetivo = tiempo_objetivo;
    }

    public Integer getDias_objetivo() {
        return dias_objetivo;
    }

    public void setDias_objetivo(Integer dias_objetivo) {
        this.dias_objetivo = dias_objetivo;
    }

    public Integer getFkIdUsuario() {
        return fkIdUsuario;
    }

    public void setFkIdUsuario(Integer fkIdUsuario) {
        this.fkIdUsuario = fkIdUsuario;
    }
}
