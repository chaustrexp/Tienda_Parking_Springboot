package com.tienda.controller;

import com.tienda.model.Viaje;
import com.tienda.service.CarroService;
import com.tienda.service.ChoferService;
import com.tienda.service.MotorService;
import com.tienda.service.PasajeroService;
import com.tienda.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private PasajeroService pasajeroService;

    @Autowired
    private CarroService carroService;

    @Autowired
    private ChoferService choferService;

    @Autowired
    private ViajeService viajeService;

    @Autowired
    private MotorService motorService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalPasajeros", pasajeroService.contarPasajeros());
        model.addAttribute("totalCarros", carroService.contarCarros());
        model.addAttribute("totalMotores", motorService.contarMotores());
        model.addAttribute("totalChoferes", choferService.contarChoferes());
        model.addAttribute("totalViajes", viajeService.contarViajes());
        model.addAttribute("viajesRecientes", viajeService.obtenerTodos());
        return "index";
    }

    @ResponseBody
    @GetMapping("/api/reporte-general")
    public Map<String, Object> obtenerReporteGeneral() {
        Map<String, Object> reporte = new HashMap<>();

        List<Map<String, Object>> viajesList = new ArrayList<>();
        for (Viaje v : viajeService.obtenerTodos()) {
            Map<String, Object> vMap = new HashMap<>();
            vMap.put("idViaje", v.getIdViaje());
            vMap.put("placaCarro", v.getCarro() != null ? v.getCarro().getPlaca() : "-");
            vMap.put("marcaCarro", v.getCarro() != null ? v.getCarro().getMarca() : "-");
            vMap.put("modeloCarro", v.getCarro() != null ? v.getCarro().getModelo() : "-");
            vMap.put("serieMotor", v.getMotor() != null ? v.getMotor().getNumeroSerie() : "-");
            vMap.put("tipoMotor", v.getMotor() != null ? v.getMotor().getTipo() : "-");
            vMap.put("cilindrajeMotor", v.getMotor() != null ? v.getMotor().getCilindraje() : "-");
            vMap.put("cedulaChofer", v.getChofer() != null ? v.getChofer().getCedula() : "-");
            vMap.put("nombreChofer", v.getChofer() != null ? v.getChofer().getNombre() : "-");
            vMap.put("licenciaChofer", v.getChofer() != null ? v.getChofer().getLicencia() : "-");
            vMap.put("cedulaPasajero", v.getPasajero() != null ? v.getPasajero().getCedula() : "-");
            String nombreCompleto = "";
            if (v.getPasajero() != null) {
                nombreCompleto = (v.getPasajero().getNombre() != null ? v.getPasajero().getNombre() : "") + " " +
                                 (v.getPasajero().getApellido() != null ? v.getPasajero().getApellido() : "");
            }
            vMap.put("nombrePasajero", !nombreCompleto.trim().isEmpty() ? nombreCompleto.trim() : "-");
            vMap.put("telefonoPasajero", v.getPasajero() != null ? v.getPasajero().getTelefono() : "-");
            viajesList.add(vMap);
        }

        reporte.put("viajes", viajesList);
        reporte.put("carros", carroService.obtenerTodos());
        reporte.put("motores", motorService.obtenerTodos());
        reporte.put("choferes", choferService.obtenerTodos());
        reporte.put("pasajeros", pasajeroService.obtenerTodos());
        return reporte;
    }
}
