package com.tienda.controller;

import com.tienda.model.Pasajero;
import com.tienda.service.PasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pasajeros")
public class PasajeroController {

    @Autowired
    private PasajeroService pasajeroService;

    @GetMapping
    public String listarPasajeros(Model model) {
        model.addAttribute("pasajeros", pasajeroService.obtenerTodos());
        model.addAttribute("nuevoPasajero", new Pasajero());
        return "pasajeros";
    }

    @PostMapping("/guardar")
    public String guardarPasajero(@ModelAttribute("nuevoPasajero") Pasajero pasajero, RedirectAttributes redirectAttrs) {
        try {
            pasajeroService.guardar(pasajero);
            redirectAttrs.addFlashAttribute("mensajeExito", "Pasajero '" + pasajero.getNombreCompleto() + "' registrado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al guardar pasajero: " + e.getMessage());
        }
        return "redirect:/pasajeros";
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminarPasajero(@PathVariable("cedula") String cedula, RedirectAttributes redirectAttrs) {
        try {
            pasajeroService.eliminar(cedula);
            redirectAttrs.addFlashAttribute("mensajeExito", "Pasajero eliminado con éxito.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al eliminar pasajero: " + e.getMessage());
        }
        return "redirect:/pasajeros";
    }
}
