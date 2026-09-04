package com.tienda.repository;

import com.tienda.model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, Integer> {

    boolean existsByCarroPlacaAndMotorNumeroSerieAndChoferCedulaAndPasajeroCedula(
            String placaCarro, String serieMotor, String cedulaChofer, String cedulaPasajero);
}
