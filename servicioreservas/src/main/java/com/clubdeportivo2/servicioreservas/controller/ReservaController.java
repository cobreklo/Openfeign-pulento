package com.clubdeportivo2.servicioreservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import com.clubdeportivo2.servicioreservas.services.ReservaServices;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaServices reservaService;

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @PostMapping
    public Reserva crearReserva(@RequestBody Reserva reserva) {
        return reservaService.crearReserva(reserva);
    }

    @GetMapping("/cancha/{id}")
    public List<Reserva> buscarPorCancha(@PathVariable Long id) {
        return reservaService.buscarReservasPorCancha(id);
    }

    @GetMapping("/canchas/{canchaId}")
    public Cancha obtenerCancha(@PathVariable Long canchaId) {
        return reservaService.obtenerCanchaDeReserva(canchaId);
    }

    @GetMapping("/canchas")
    public List<Cancha> listarCanchasDisponibles() {
        return reservaService.listarCanchasDisponibles();
    }

}
