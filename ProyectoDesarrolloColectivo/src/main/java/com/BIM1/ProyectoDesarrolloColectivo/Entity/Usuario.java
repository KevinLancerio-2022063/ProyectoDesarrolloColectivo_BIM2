package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer id_usuario;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 150, message = "El nombre no puede tener más de 150 caracteres")
    @Column(name = "nombre_completo")
    private String nombre_completo;

    @NotBlank(message = "El correo no puede estar vacío")
    @Column(name = "correo_usuario")
    private String correoUsuario;

    @NotBlank(message = "La contraseña no puede estar vacío")
    @Size(max = 100, message = "La contraseña no puede tener más de 100 caracteres")
    @Column(name = "contraseña")
    private String contraseña;

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}