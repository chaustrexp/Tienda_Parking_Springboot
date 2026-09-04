package com.tienda.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chofer")
public class Chofer {

    @Id
    private String cedula;

    private String nombre;
    private String licencia;

    public Chofer() {}

    public Chofer(String cedula, String nombre, String licencia) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.licencia = licencia;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
}
