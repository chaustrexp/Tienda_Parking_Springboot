package com.tienda.service;

import com.tienda.model.Motor;
import com.tienda.repository.MotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotorService {

    @Autowired
    private MotorRepository motorRepository;

    public List<Motor> obtenerTodos() {
        return motorRepository.findAll();
    }

    public Optional<Motor> obtenerPorNumeroSerie(String numeroSerie) {
        return motorRepository.findById(numeroSerie);
    }

    public Motor guardar(Motor motor) {
        if (motor.getNumeroSerie() == null || motor.getNumeroSerie().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de serie del motor es obligatorio.");
        }
        String serieLimpia = motor.getNumeroSerie().trim();
        motor.setNumeroSerie(serieLimpia);

        if (motor.getTipo() == null || motor.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de motor es obligatorio (ej: Diesel, Gasolina, Híbrido).");
        }
        motor.setTipo(motor.getTipo().trim());

        if (motor.getCilindraje() == null || motor.getCilindraje() < 500) {
            throw new IllegalArgumentException("El cilindraje debe ser un valor numérico de al menos 500 cc.");
        }

        // Verificar si la serie ya existe para no sobreescribirla
        if (motorRepository.existsById(serieLimpia)) {
            throw new IllegalArgumentException("El motor con número de serie '" + serieLimpia + "' ya se encuentra registrado.");
        }

        return motorRepository.save(motor);
    }

    public void eliminar(String numeroSerie) {
        motorRepository.deleteById(numeroSerie);
    }

    public long contarMotores() {
        return motorRepository.count();
    }
}
