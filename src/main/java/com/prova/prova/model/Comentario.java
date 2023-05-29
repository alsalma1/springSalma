package com.prova.prova.model;

import jakarta.persistence.*;
@Entity
@Table(name="comentarios")
public class Comentario {
    @Id
    @GeneratedValue
    private Long id;

    private String email_cliente;

    private String resenya;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }

    public String getResenya() {
        return resenya;
    }

    public void setResenya(String resenya) {
        this.resenya = resenya;
    }
}
