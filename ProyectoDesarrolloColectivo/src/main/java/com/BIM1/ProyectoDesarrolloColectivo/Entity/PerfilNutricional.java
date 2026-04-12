package com.BIM1.ProyectoDesarrolloColectivo.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Perfil_nutricional")
public class PerfilNutricional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil_nutricional")
    private Integer id_perfil_nutricional;

    @Column(name = "peso_kg")
    @NotNull(message = "el peso no puede estar vacio o nulo")
    @Positive(message = "el peso debe de ser mayor a 0")
    @Max(value = 300, message = "el peso no puede ser mayor a 300 kg")
    @Min(value = 45, message = "el peso no puede ser menor a 45 kg")
    private int peso_kg;

    @Column(name = "altura")
    @NotNull(message = "la altura no puede estar vacia")
    @DecimalMin(value = "0.50", message = "la altura minima valida es de 0.5 metros")
    @DecimalMax(value = "2.50", message = "la altura maxima valida es de 2.5 metros")
    private Double altura;

    @Column(name = "edad")
    @NotNull(message = "la edad no puede ser vacia ni nula")
    @Positive(message = "la edad no puede ser menor o igual a 0 ")
    @Min(value = 12, message = "la edad minima debe ser de 12 años")
    @Max(value = 120, message = "la edad no puede superar los 120 años")
    private int edad;

    @Column(name = "genero")
    @NotBlank(message = "el genero no puede ser vacio")
    private String genero;

    @Column(name = "nivel_actividad")
    @NotBlank(message = "el nivel de actividad no puede ser vacio")
    private String nivel_actividad;

    @Column(name = "objetivo")
    @NotBlank(message = "el objetivo no puede estar vacio")
    @Size(max = 75, message = "el mensaje no puede tener mas de 75 caracteres")
    private String objetivo;

    @Column(name = "fk_id_usuario")
    @NotNull(message = "el id del usuario no puede ser vacio ni nulo")
    @Positive(message = "el id de la llave foranea no pude ser menor o igual 0")
    private Integer fk_id_usuario;

    public Integer getId_perfil_nutricional() {
        return id_perfil_nutricional;
    }

    public void setId_perfil_nutricional(Integer id_perfil_nutricional) {
        this.id_perfil_nutricional = id_perfil_nutricional;
    }

    public int getPeso_kg() {
        return peso_kg;
    }

    public void setPeso_kg(int peso_kg) {
        this.peso_kg = peso_kg;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNivel_actividad() {
        return nivel_actividad;
    }

    public void setNivel_actividad(String nivel_actividad) {
        this.nivel_actividad = nivel_actividad;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(Integer fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }
}
