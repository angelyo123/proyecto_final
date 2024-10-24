package com.meatplace.meatplace.controladores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.meatplace.meatplace.entidades.Login;
import com.meatplace.meatplace.entidades.Pedido;
import com.meatplace.meatplace.entidades.Reserva;
import com.meatplace.meatplace.repositorio.LoginRepository;
import com.meatplace.meatplace.repositorio.ReservaRepository;
import com.meatplace.meatplace.services.PedidoService;

@Controller
public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    PedidoService pedidoService;

    @GetMapping("/Acceso")
    @Transactional(readOnly = false)
    public String acceder(@RequestParam("usuario") String usuario,
            @RequestParam("contrasena") String contrasena, Model model) {
        Login login = loginRepository.validarLogin(usuario, contrasena);

        if (login != null) {
            model.addAttribute("nombre", login.getNombre());
            if (login.getPerfil() == 1) {
                List<Reserva> listaReserva = reservaRepository.findAll();
                model.addAttribute("listaReserva", listaReserva);
                return "recepcionar";
            } else if (login.getPerfil() == 0) {
                List<Pedido> pedidos = pedidoService.obtenerTodos();

                pedidos = pedidos.stream()
                        .filter(pedido -> pedido != null)
                        .collect(Collectors.toList());

                long totalEntregados = pedidos.stream()
                        .filter(p -> "Entregado".equals(p.getEstado()))
                        .count();
                long totalPendientes = pedidos.size() - totalEntregados;

                model.addAttribute("pedidos", pedidos);
                model.addAttribute("totalEntregados", totalEntregados);
                model.addAttribute("totalPendientes", totalPendientes);
                model.addAttribute("usuario", usuario);
                model.addAttribute("contrasena", contrasena);

                return "gestion-pedidos";
            } else
                return "login";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }

}
