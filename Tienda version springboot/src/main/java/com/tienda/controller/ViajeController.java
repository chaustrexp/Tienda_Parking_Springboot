package com.tienda.controller;

import com.tienda.service.CarroService;
import com.tienda.service.ChoferService;
import com.tienda.service.MotorService;
import com.tienda.service.PasajeroService;
import com.tienda.service.ViajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/viajes")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @Autowired
    private CarroService carroService;

    @Autowired
    private MotorService motorService;

    @Autowired
    private ChoferService choferService;

    @Autowired
    private PasajeroService pasajeroService;

    @GetMapping
    public String listarViajes(Model model) {
        model.addAttribute("viajes", viajeService.obtenerTodos());
        model.addAttribute("carros", carroService.obtenerTodos());
        model.addAttribute("motores", motorService.obtenerTodos());
        model.addAttribute("choferes", choferService.obtenerTodos());
        model.addAttribute("pasajeros", pasajeroService.obtenerTodos());
        return "viajes";
    }

    @PostMapping("/guardar")
    public String guardarViaje(@RequestParam("placaCarro") String placaCarro,
                               @RequestParam("serieMotor") String serieMotor,
                               @RequestParam("cedulaChofer") String cedulaChofer,
                               @RequestParam("cedulaPasajero") String cedulaPasajero,
                               RedirectAttributes redirectAttrs) {
        try {
            viajeService.registrarViaje(placaCarro, serieMotor, cedulaChofer, cedulaPasajero);
            redirectAttrs.addFlashAttribute("mensajeExito", "¡Ficha de Viaje (HU-01) registrada exitosamente en la BD!");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al registrar viaje: " + e.getMessage());
        }
        return "redirect:/viajes";
    }

    @GetMapping("/eliminar/{idViaje}")
    public String eliminarViaje(@PathVariable("idViaje") Integer idViaje, RedirectAttributes redirectAttrs) {
        try {
            viajeService.eliminar(idViaje);
            redirectAttrs.addFlashAttribute("mensajeExito", "Viaje eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al eliminar viaje: " + e.getMessage());
        }
        return "redirect:/viajes";
    }
}
