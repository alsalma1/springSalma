package com.prova.prova.model;

import jakarta.persistence.*;
@Entity
@Table(name="platos")
public class Plato {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private float precio;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private boolean estadop;

    @Column(nullable = false)
    private boolean especial;

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isEstadop() {
        return estadop;
    }

    public void setEstadop(boolean estadop) {
        this.estadop = estadop;
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
}
