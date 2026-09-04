package com.tienda.controller;

import com.tienda.model.Motor;
import com.tienda.service.MotorService;
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
@RequestMapping("/motores")
public class MotorController {

    @Autowired
    private MotorService motorService;

    @GetMapping
    public String listarMotores(Model model) {
        model.addAttribute("motores", motorService.obtenerTodos());
        model.addAttribute("nuevoMotor", new Motor());
        return "motores";
    }

    @PostMapping("/guardar")
    public String guardarMotor(@ModelAttribute("nuevoMotor") Motor motor,
                               RedirectAttributes redirectAttrs) {
        try {
            motorService.guardar(motor);
            redirectAttrs.addFlashAttribute("mensajeExito", "Motor con número de serie '" + motor.getNumeroSerie() + "' registrado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al registrar motor: " + e.getMessage());
        }
        return "redirect:/motores";
    }

    @GetMapping("/eliminar/{numeroSerie}")
    public String eliminarMotor(@PathVariable("numeroSerie") String numeroSerie, RedirectAttributes redirectAttrs) {
        try {
            motorService.eliminar(numeroSerie);
            redirectAttrs.addFlashAttribute("mensajeExito", "Motor eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al eliminar motor: " + e.getMessage());
        }
        return "redirect:/motores";
    }
}
