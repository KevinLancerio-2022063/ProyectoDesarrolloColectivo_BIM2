package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "EntradaDiario")
public class EntradaDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrada_diario")
    private Integer id_entrada_diario;

    @Column(name = "fecha")
    @NotNull
    private LocalDate fecha;

    @Column(name = "que_paso")
    @NotBlank
    private String que_paso;

    @Column(name = "plan_mañana")
    private String plan_mañana;

    @Column(name = "reflexion")
    private String reflexion;

    @Column(name = "fk_id_Usuario")
    @NotNull(message = "la llave foranea no puede estar vacia")
    private Integer fk_id_usuario;

    public Integer getId_entrada_diario() {
        return id_entrada_diario;
    }

    public void setId_entrada_diario(Integer id_entrada_diario) {
        this.id_entrada_diario = id_entrada_diario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getQue_paso() {
        return que_paso;
    }

    public void setQue_paso(String que_paso) {
        this.que_paso = que_paso;
    }

    public String getPlan_mañana() {
        return plan_mañana;
    }

    public void setPlan_mañana(String plan_mañana) {
        this.plan_mañana = plan_mañana;
    }

    public String getReflexion() {
        return reflexion;
    }

    public void setReflexion(String reflexion) {
        this.reflexion = reflexion;
    }

    public Integer getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(Integer fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }
}