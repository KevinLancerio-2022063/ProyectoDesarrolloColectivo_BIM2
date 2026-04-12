package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "registroSueño")
public class RegistroSueno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro_sueño")
    private Integer id_registro_sueño;

    @NotBlank(message = "La fecha no puede estar vacío")
    @Column(name = "fecha_sueño")
    private String fecha_sueño;

    @NotBlank(message = "Las horas dormidas no pueden estar vacías")
    @Max(value = 15, message = "Las horas máximas dormidas permitidas son de 15")
    @Column(name = "horas_dormidas")
    private String horas_dormidas;

    @NotBlank(message = "La calidad del sueño no puede estar vacío")
    @Size(max = 100, message = "La calidad del sueño no puede tener más de 100 caracteres")
    @Column(name = "calidad_sueño")
    private String calidad_sueño;

    @NotNull(message = "El fk_id_usuario no puede estar vacío")
    @Positive(message = "El FK no puede ser menor que 1")
    @Column(name = "fk_id_usuario")
    private Integer fk_id_usuario;

    public Integer getId_registro_sueño() {
        return id_registro_sueño;
    }

    public void setId_registro_sueño(Integer id_registro_sueño) {
        this.id_registro_sueño = id_registro_sueño;
    }

    public String getFecha_sueño() {
        return fecha_sueño;
    }

    public void setFecha_sueño(String fecha_sueño) {
        this.fecha_sueño = fecha_sueño;
    }

    public String getHoras_dormidas() {
        return horas_dormidas;
    }

    public void setHoras_dormidas(String horas_dormidas) {
        this.horas_dormidas = horas_dormidas;
    }

    public String getCalidad_sueño() {
        return calidad_sueño;
    }

    public void setCalidad_sueño(String calidad_sueño) {
        this.calidad_sueño = calidad_sueño;
    }

    public Integer getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(Integer fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }
}