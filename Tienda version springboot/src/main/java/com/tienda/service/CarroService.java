package com.tienda.service;

import com.tienda.model.Carro;
import com.tienda.repository.CarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CarroService {

    @Autowired
    private CarroRepository carroRepository;

    public List<Carro> obtenerTodos() {
        return carroRepository.findAll();
    }

    public Optional<Carro> obtenerPorPlaca(String placa) {
        return carroRepository.findById(placa);
    }

    public Carro guardar(Carro carro) {
        if (carro.getPlaca() == null || carro.getPlaca().trim().isEmpty()) {
            throw new IllegalArgumentException("La placa del carro es obligatoria.");
        }
        
        String placaLimpia = carro.getPlaca().trim().toUpperCase();
        carro.setPlaca(placaLimpia);

        // Validación de formato de placa: entre 5 y 7 caracteres alfanuméricos (ej. ABC123 o ABC12D)
        if (!placaLimpia.matches("^[A-Z0-9]{5,7}$")) {
            throw new IllegalArgumentException("La placa debe tener entre 5 y 7 caracteres alfanuméricos sin espacios (ej: QWE123).");
        }

        if (carro.getMarca() == null || carro.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca del carro es obligatoria.");
        }
        carro.setMarca(carro.getMarca().trim());

        if (carro.getModelo() == null || carro.getModelo().trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo / año del carro es obligatorio.");
        }
        carro.setModelo(carro.getModelo().trim());

        // Verificar si la placa ya existe en la base de datos para no sobreescribirla
        if (carroRepository.existsById(placaLimpia)) {
            throw new IllegalArgumentException("El vehículo con placa '" + placaLimpia + "' ya se encuentra registrado.");
        }

        return carroRepository.save(carro);
    }

    public void eliminar(String placa) {
        carroRepository.deleteById(placa);
    }

    public long contarCarros() {
        return carroRepository.count();
    }
}
