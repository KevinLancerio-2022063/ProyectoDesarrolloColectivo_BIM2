package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Apoyo_emocional")
public class ApoyoEmocional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apoyo_emocional")
    private Integer idApoyoEmocional;

    @NotBlank(message = "Todos los campos son Obligatorios")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "Todos los campos son Obligatorios")
    @Column(name = "categoria")
    private String categoria;

    @NotBlank(message = "Todos los campos son Obligatorios")
    @Column(name = "contenido")
    private String contenido;

    @NotBlank(message = "Todos los campos son Obligatorios")
    @Column(name = "nivel_animo")
    private String nivelAnimo;

    @NotNull(message = "Todos los campos son Obligatorios")
    @ManyToOne
    @JoinColumn(name = "fk_id_usuario")
    private Usuario usuario;


    // Getter and Setter

    public Integer getIdApoyoEmocional() {
        return idApoyoEmocional;
    }

    public void setIdApoyoEmocional(Integer idApoyoEmocional) {
        this.idApoyoEmocional = idApoyoEmocional;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNivelAnimo() {
        return nivelAnimo;
    }

    public void setNivelAnimo(String nivelAnimo) {
        this.nivelAnimo = nivelAnimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
