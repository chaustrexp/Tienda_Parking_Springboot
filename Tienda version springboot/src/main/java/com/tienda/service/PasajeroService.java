package com.tienda.service;

import com.tienda.model.Pasajero;
import com.tienda.repository.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PasajeroService {

    @Autowired
    private PasajeroRepository pasajeroRepository;

    public List<Pasajero> obtenerTodos() {
        return pasajeroRepository.findAll();
    }

    public Optional<Pasajero> obtenerPorCedula(String cedula) {
        return pasajeroRepository.findById(cedula);
    }

    public Pasajero guardar(Pasajero pasajero) {
        if (pasajero.getCedula() == null || pasajero.getCedula().trim().isEmpty()) {
            throw new IllegalArgumentException("La cédula del pasajero es obligatoria.");
        }
        String cedulaLimpia = pasajero.getCedula().trim();
        pasajero.setCedula(cedulaLimpia);

        if (!cedulaLimpia.matches("^[0-9]{6,12}$")) {
            throw new IllegalArgumentException("La cédula del pasajero debe contener únicamente dígitos numéricos (entre 6 y 12 dígitos).");
        }

        if (pasajero.getNombre() == null || pasajero.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del pasajero es obligatorio.");
        }
        String nombreLimpio = pasajero.getNombre().trim();
        pasajero.setNombre(nombreLimpio);

        if (!nombreLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,100}$")) {
            throw new IllegalArgumentException("El nombre del pasajero solo debe contener letras y espacios.");
        }

        if (pasajero.getApellido() != null && !pasajero.getApellido().trim().isEmpty()) {
            String apellidoLimpio = pasajero.getApellido().trim();
            if (!apellidoLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,100}$")) {
                throw new IllegalArgumentException("El apellido del pasajero solo debe contener letras y espacios.");
            }
            pasajero.setApellido(apellidoLimpio);
        }

        if (pasajero.getTelefono() != null && !pasajero.getTelefono().trim().isEmpty()) {
            String telLimpio = pasajero.getTelefono().trim();
            if (!telLimpio.matches("^[0-9]{7,10}$")) {
                throw new IllegalArgumentException("El teléfono debe contener entre 7 y 10 dígitos numéricos (sin letras ni guiones).");
            }
            pasajero.setTelefono(telLimpio);
        }

        // Verificar duplicados de cédula
        if (pasajeroRepository.existsById(cedulaLimpia)) {
            throw new IllegalArgumentException("El pasajero con cédula '" + cedulaLimpia + "' ya se encuentra registrado.");
        }

        return pasajeroRepository.save(pasajero);
    }

    public void eliminar(String cedula) {
        pasajeroRepository.deleteById(cedula);
    }

    public long contarPasajeros() {
        return pasajeroRepository.count();
    }
}
