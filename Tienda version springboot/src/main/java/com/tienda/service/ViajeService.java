package com.tienda.service;

import com.tienda.model.Carro;
import com.tienda.model.Chofer;
import com.tienda.model.Motor;
import com.tienda.model.Pasajero;
import com.tienda.model.Viaje;
import com.tienda.repository.CarroRepository;
import com.tienda.repository.ChoferRepository;
import com.tienda.repository.MotorRepository;
import com.tienda.repository.PasajeroRepository;
import com.tienda.repository.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private MotorRepository motorRepository;

    @Autowired
    private ChoferRepository choferRepository;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    public List<Viaje> obtenerTodos() {
        return viajeRepository.findAll();
    }

    public Optional<Viaje> obtenerPorId(Integer idViaje) {
        return viajeRepository.findById(idViaje);
    }

    public Viaje registrarViaje(String placaCarro, String serieMotor, String cedulaChofer, String cedulaPasajero) {
        if (placaCarro == null || placaCarro.trim().isEmpty() ||
                serieMotor == null || serieMotor.trim().isEmpty() ||
                cedulaChofer == null || cedulaChofer.trim().isEmpty() ||
                cedulaPasajero == null || cedulaPasajero.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Debe seleccionar obligatoriamente un carro, un motor, un chofer y un pasajero.");
        }

        Carro carro = carroRepository.findById(placaCarro.trim())
                .orElseThrow(() -> new IllegalArgumentException("El carro con placa '" + placaCarro + "' no existe."));
        Motor motor = motorRepository.findById(serieMotor.trim())
                .orElseThrow(() -> new IllegalArgumentException("El motor con serie '" + serieMotor + "' no existe."));
        Chofer chofer = choferRepository.findById(cedulaChofer.trim())
                .orElseThrow(
                        () -> new IllegalArgumentException("El chofer con cédula '" + cedulaChofer + "' no existe."));
        Pasajero pasajero = pasajeroRepository.findById(cedulaPasajero.trim())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El pasajero con cédula '" + cedulaPasajero + "' no existe."));

        if (viajeRepository.existsByCarroPlacaAndMotorNumeroSerieAndChoferCedulaAndPasajeroCedula(
                carro.getPlaca(), motor.getNumeroSerie(), chofer.getCedula(), pasajero.getCedula())) {
            throw new IllegalArgumentException(
                    "Error: Ya existe un viaje idéntico registrado con este carro, motor, chofer y pasajero.");
        }

        Viaje viaje = new Viaje(carro, motor, chofer, pasajero);
        return viajeRepository.save(viaje);
    }

    public void eliminar(Integer idViaje) {
        viajeRepository.deleteById(idViaje);
    }

    public long contarViajes() {
        return viajeRepository.count();
    }
}
