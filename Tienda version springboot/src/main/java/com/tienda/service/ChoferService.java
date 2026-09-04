package com.tienda.service;

import com.tienda.model.Chofer;
import com.tienda.repository.ChoferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ChoferService {

    @Autowired
    private ChoferRepository choferRepository;

    public List<Chofer> obtenerTodos() {
        return choferRepository.findAll();
    }

    public Optional<Chofer> obtenerPorCedula(String cedula) {
        return choferRepository.findById(cedula);
    }

    public Chofer guardar(Chofer chofer) {
        if (chofer.getCedula() == null || chofer.getCedula().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de cédula del chofer es obligatorio.");
        }
        String cedulaLimpia = chofer.getCedula().trim();
        chofer.setCedula(cedulaLimpia);

        if (!cedulaLimpia.matches("^[0-9]{6,12}$")) {
            throw new IllegalArgumentException("La cédula del chofer debe contener únicamente dígitos numéricos (entre 6 y 12 dígitos).");
        }

        if (chofer.getNombre() == null || chofer.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del chofer es obligatorio.");
        }
        String nombreLimpio = chofer.getNombre().trim();
        chofer.setNombre(nombreLimpio);

        if (!nombreLimpio.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{2,100}$")) {
            throw new IllegalArgumentException("El nombre del chofer solo debe contener letras y espacios (mínimo 2 letras).");
        }

        if (chofer.getLicencia() == null || chofer.getLicencia().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de licencia del chofer es obligatorio.");
        }
        chofer.setLicencia(chofer.getLicencia().trim());

        // Verificar duplicados de cédula
        if (choferRepository.existsById(cedulaLimpia)) {
            throw new IllegalArgumentException("El chofer con cédula '" + cedulaLimpia + "' ya se encuentra registrado.");
        }

        return choferRepository.save(chofer);
    }

    public void eliminar(String cedula) {
        choferRepository.deleteById(cedula);
    }

    public long contarChoferes() {
        return choferRepository.count();
    }
}
