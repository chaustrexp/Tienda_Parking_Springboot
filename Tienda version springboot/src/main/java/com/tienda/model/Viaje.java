package com.tienda.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "viaje")
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viaje")
    private Integer idViaje;

    @ManyToOne
    @JoinColumn(name = "placa_carro", referencedColumnName = "placa")
    private Carro carro;

    @ManyToOne
    @JoinColumn(name = "serie_motor", referencedColumnName = "numero_serie")
    private Motor motor;

    @ManyToOne
    @JoinColumn(name = "cedula_chofer", referencedColumnName = "cedula")
    private Chofer chofer;

    @ManyToOne
    @JoinColumn(name = "cedula_pasajero", referencedColumnName = "cedula")
    private Pasajero pasajero;

    public Viaje() {}

    public Viaje(Carro carro, Motor motor, Chofer chofer, Pasajero pasajero) {
        this.carro = carro;
        this.motor = motor;
        this.chofer = chofer;
        this.pasajero = pasajero;
    }

    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Motor getMotor() {
        return motor;
    }

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
}
