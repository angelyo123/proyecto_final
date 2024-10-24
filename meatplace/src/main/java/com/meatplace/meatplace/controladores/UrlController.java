package com.meatplace.meatplace.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.meatplace.meatplace.entidades.Plato;
import com.meatplace.meatplace.repositorio.PedidoRepository;
import com.meatplace.meatplace.repositorio.PlatoRepository;


@Controller
public class UrlController {

    @Autowired
	private PlatoRepository platoRepository;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/reserva")
    public String reserva(Model model) {
        model.addAttribute("formulario", 0);
        return "reserva";
    }

    @GetMapping("/irPedidos")
    public String mostrarFormularioDePedido(Model model) {
		List<Plato> platos = platoRepository.findAll();
		model.addAttribute("platos", platos);
		return "pedido"; 
	}
    
}
