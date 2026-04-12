package com.BIM1.ProyectoDesarrolloColectivo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Frase_motivadora")
public class FraseMotivadora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frase_motivadora")
    private Integer idFraseMotivadora;

    @NotBlank(message = "Todos los campos son obligatorios")
    @Column(name = "texto")
    private String texto;

    @NotBlank(message = "Todos los campos son obligatorios")
    @Column(name = "autor")
    private String autor;


    //Getters and Setters

    public Integer getIdFraseMotivadora() {
        return idFraseMotivadora;
    }

    public void setIdFraseMotivadora(Integer idFraseMotivadora) {
        this.idFraseMotivadora = idFraseMotivadora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
