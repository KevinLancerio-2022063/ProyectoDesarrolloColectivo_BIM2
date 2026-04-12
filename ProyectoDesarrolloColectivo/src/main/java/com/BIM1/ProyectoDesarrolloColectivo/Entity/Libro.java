package com.BIM1.ProyectoDesarrolloColectivo.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_libro")
    private Integer id_libro;

    @Column(name = "titulo_libro")
    @NotBlank(message = "el titulo del libro no puede estar vacio")
    @Size(max = 100, message = "el titulo del libro no puede tener mas de 100 caracteres")
    private String titulo_libro;

    @Column(name = "autor_libro")
    @NotBlank(message = "el autor del libro no puede estar vacio")
    @Size(max = 100, message = "el nombre del autor del libro no puede tener mas de 100 caracteres")
    private String autor_libro;

    @Column(name = "estado")
    @NotBlank(message = "el estado del libro no puede estar vacio")
    private String estado;

    @Column(name = "cantidad_pag")
    @NotNull(message = "la cantidad de las paginas no pueden ser nulas")
    @Positive(message = "la cantidad de paginas debe ser mayor a 0")
    @Max(value = 4000, message = "la cantidad de paginas no puede superar los 4000 mil ")
    private int cantidad_pag;

    @Column(name = "cantidad_leido")
    @NotNull(message = "la cantidad de las pag leidas no pueden ser nulas")
    @PositiveOrZero(message = "las paginas leidas no pueden ser negativas")
    private int cantidad_leido;

    @Column(name = "fk_id_usuario")
    @NotNull(message = "el id del usuario no puede estar vacio o nulo")
    @Positive(message = "el id de la llave foranea no puede ser menor o igual 0")
    private Integer fk_id_usuario;

    public Integer getId_libro() {
        return id_libro;
    }

    public void setId_libro(Integer id_libro) {
        this.id_libro = id_libro;
    }

    public String getTitulo_libro() {
        return titulo_libro;
    }

    public void setTitulo_libro(String titulo_libro) {
        this.titulo_libro = titulo_libro;
    }

    public String getAutor_libro() {
        return autor_libro;
    }

    public void setAutor_libro(String autor_libro) {
        this.autor_libro = autor_libro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad_pag() {
        return cantidad_pag;
    }

    public void setCantidad_pag(int cantidad_pag) {
        this.cantidad_pag = cantidad_pag;
    }

    public int getCantidad_leido() {
        return cantidad_leido;
    }

    public void setCantidad_leido(int cantidad_leido) {
        this.cantidad_leido = cantidad_leido;
    }

    public Integer getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(Integer fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }
}
