package com.meatplace.meatplace.controladores;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.meatplace.meatplace.entidades.Reserva;
import com.meatplace.meatplace.repositorio.ReservaRepository;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping(value = "/reserva-mesas", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE })
    public String generarReserva(
            @RequestParam String nroPersonas,
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam String comentarios,
            @RequestParam String mesa,
            @RequestParam String dni,
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String telefono,
            @RequestParam String correo,
            Model model) {


            reservaRepository.insertarReserva(
                    Integer.parseInt(nroPersonas),
                    java.sql.Date.valueOf(fecha),
                    java.sql.Time.valueOf(hora + ":00"),
                    comentarios,
                    Integer.parseInt(mesa),
                    nombres,
                    apellidos,
                    telefono,
                    correo,
                    dni);

        return "informacionReserva";
    }

    
    @GetMapping("/reserva-mesas")
    public String obtenerUltimaReserva(@RequestParam String nroPersonas,
    @RequestParam String fecha,
    @RequestParam String hora,
    @RequestParam String comentarios,
    @RequestParam String mesa,
    @RequestParam String dni,
    @RequestParam String nombres,
    @RequestParam String apellidos,
    @RequestParam String telefono,
    @RequestParam String correo,
    Model model) {

        reservaRepository.insertarReserva(Integer.parseInt(nroPersonas), Date.valueOf(fecha), Time.valueOf(hora+":00"), comentarios, Integer.parseInt(mesa), nombres, apellidos, telefono, correo, dni);

        try {
            Reserva ultimaReserva = reservaRepository.obtenerUltimaReserva();
            model.addAttribute("idMesa", ultimaReserva.getMesa().getIdMesa());
            model.addAttribute("fecha", ultimaReserva.getFecha());
            model.addAttribute("horaInicio", ultimaReserva.getHoraInicio());
            model.addAttribute("horaFin", ultimaReserva.getHoraFin());
            model.addAttribute("nombre", ultimaReserva.getNombreCliente() + " " + ultimaReserva.getApellidoCliente());
            model.addAttribute("dni", ultimaReserva.getDniCliente());
            model.addAttribute("correo", ultimaReserva.getCorreoCliente());
        } catch (Exception e) {
            model.addAttribute("error", "No se pudo obtener la última reserva.");
        }
        return "informacionReserva"; 
    }

   @GetMapping("/filtroReservas")
   public String fitroPorFecha(@RequestParam String fecha, Model model) {

    if (fecha != null && !fecha.isEmpty()) {
        List<Reserva> listadoReserva = reservaRepository.findByFecha(java.sql.Date.valueOf(fecha));
        model.addAttribute("listaReserva", listadoReserva);
        
    } else 
        model.addAttribute("listaReserva", reservaRepository.findAll()); 

    return "recepcionar"; 
    }

    @PostMapping("/actEstado")
    public String actualizarEstadoReserva(@RequestParam String id, @RequestParam String estado, Model model) {
        reservaRepository.actualizar_estado_reserva(Integer.parseInt(id), estado);
        model.addAttribute("listaReserva", reservaRepository.findAll()); 

        return "recepcionar";
    }
    
}
