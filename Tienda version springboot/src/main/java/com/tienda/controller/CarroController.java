package com.tienda.controller;

import com.tienda.model.Carro;
import com.tienda.service.CarroService;
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
@RequestMapping("/carros")
public class CarroController {

    @Autowired
    private CarroService carroService;

    @GetMapping
    public String listarCarros(Model model) {
        model.addAttribute("carros", carroService.obtenerTodos());
        model.addAttribute("nuevoCarro", new Carro());
        return "carros";
    }

    @PostMapping("/guardar")
    public String guardarCarro(@ModelAttribute("nuevoCarro") Carro carro,
                               RedirectAttributes redirectAttrs) {
        try {
            carroService.guardar(carro);
            redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo con placa '" + carro.getPlaca() + "' registrado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al registrar vehículo: " + e.getMessage());
        }
        return "redirect:/carros";
    }

    @GetMapping("/eliminar/{placa}")
    public String eliminarCarro(@PathVariable("placa") String placa, RedirectAttributes redirectAttrs) {
        try {
            carroService.eliminar(placa);
            redirectAttrs.addFlashAttribute("mensajeExito", "Vehículo eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Error al eliminar vehículo: " + e.getMessage());
        }
        return "redirect:/carros";
    }
}
