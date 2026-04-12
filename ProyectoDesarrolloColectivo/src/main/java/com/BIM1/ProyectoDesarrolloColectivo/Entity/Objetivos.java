package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "Objetivos")
public class Objetivos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivos")
    private Integer idObjetivos;

    @NotBlank(message = "Todos los campos son Obligatorios")
    @Column(name = "descripcion_objetivo")
    private String descripcionObjetivo;

    @NotBlank(message = "Todos los campos son obligatorios")
    @Column(name = "estado_objetivo")
    private String estadoObjetivo;

    @NotNull(message = "Todos los campos son Obligatorios")
    @Column(name = "fecha_objetivo")
    private Date fechaObjetivo;

    @NotNull(message = "Todos los campos son Obligatorios")
    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;

    @NotNull(message = "Todos los campos son obligatorios")
    @ManyToOne
    @JoinColumn(name = "fk_id_frase_motivadora")
    private FraseMotivadora fraseMotivadora;


    //Getters and Setters

    public Integer getIdObjetivos() {
        return idObjetivos;
    }

    public void setIdObjetivos(Integer idObjetivos) {
        this.idObjetivos = idObjetivos;
    }

    public String getDescripcionObjetivo() {
        return descripcionObjetivo;
    }

    public void setDescripcionObjetivo(String descripcionObjetivo) {
        this.descripcionObjetivo = descripcionObjetivo;
    }

    public String getEstadoObjetivo() {
        return estadoObjetivo;
    }

    public void setEstadoObjetivo(String estadoObjetivo) {
        this.estadoObjetivo = estadoObjetivo;
    }

    public Date getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(Date fechaObjetivo) {
        this.fechaObjetivo = fechaObjetivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FraseMotivadora getFraseMotivadora() {
        return fraseMotivadora;
    }

    public void setFraseMotivadora(FraseMotivadora fraseMotivadora) {
        this.fraseMotivadora = fraseMotivadora;
    }
}