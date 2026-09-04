package com.tienda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "motor")
public class Motor {

    @Id
    @Column(name = "numero_serie")
    private String numeroSerie;

    private String tipo;
    private Integer cilindraje;

    public Motor() {}

    public Motor(String numeroSerie, String tipo, Integer cilindraje) {
        this.numeroSerie = numeroSerie;
        this.tipo = tipo;
        this.cilindraje = cilindraje;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCilindraje() {
        return cilindraje;
    }

    public void setCilindraje(Integer cilindraje) {
        this.cilindraje = cilindraje;
    }
}
