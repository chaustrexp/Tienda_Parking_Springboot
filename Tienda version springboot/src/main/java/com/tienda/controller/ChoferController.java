package com.tienda.controller;

import com.tienda.model.Chofer;
import com.tienda.service.ChoferService;
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
@RequestMapping("/choferes")
public class ChoferController {

    @Autowired
    private ChoferService choferService;

    @GetMapping
    public String listarChoferes(Model model) {
        model.addAttribute("choferes", choferService.obtenerTodos());
        model.addAttribute("nuevoChofer", new Chofer());
        return "choferes";
    }

    @PostMapping("/guardar")
    public String guardarChofer(@ModelAttribute("nuevoChofer") Chofer chofer, RedirectAttributes redirectAttrs) {
        try {
            choferService.guardar(chofer);
            redirectAttrs.addFlashAttribute("mensajeExito", "Chofer '" + chofer.getNombre() + "' registrado exitosamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al registrar chofer: " + e.getMessage());
        }
        return "redirect:/choferes";
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminarChofer(@PathVariable("cedula") String cedula, RedirectAttributes redirectAttrs) {
        try {
            choferService.eliminar(cedula);
            redirectAttrs.addFlashAttribute("mensajeExito", "Chofer eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al eliminar chofer: " + e.getMessage());
        }
        return "redirect:/choferes";
    }
}
