package com.prova.prova.model;

import jakarta.persistence.*;
@Entity
@Table(name="mesas")
public class Mesa {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private int numero;
    private int capacidad;
    private String comentario;
    private boolean estado;

    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}