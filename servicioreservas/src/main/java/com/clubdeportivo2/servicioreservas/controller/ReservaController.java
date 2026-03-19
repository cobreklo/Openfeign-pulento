package com.clubdeportivo2.servicioreservas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import com.clubdeportivo2.servicioreservas.services.ReservaServices;

/**
 * Controlador REST del microservicio de Reservas.
 * 
 * Además de los endpoints propios de reservas, incluimos endpoints que
 * consumen datos del microservicio de Canchas a través de OpenFeign.
 * Esto demuestra cómo un microservicio puede consultar a otro de forma
 * transparente, como si fuera una llamada local.
 */
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaServices reservaService;

    // ==================== Endpoints propios de Reservas ====================

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

    // =============== Endpoints que usan OpenFeign (inter-servicio) ===============

    /**
     * Obtiene los datos de una cancha específica consultando al microservicio de Canchas.
     * Este endpoint demuestra la comunicación entre microservicios usando OpenFeign.
     * Ejemplo: GET /api/reservas/canchas/1 -> devuelve la cancha con id 1
     */
    @GetMapping("/canchas/{canchaId}")
    public Cancha obtenerCancha(@PathVariable Long canchaId) {
        return reservaService.obtenerCanchaDeReserva(canchaId);
    }

    /**
     * Lista todas las canchas disponibles obtenidas directamente del microservicio de Canchas.
     * Internamente usa OpenFeign para hacer la petición HTTP de forma transparente.
     * Ejemplo: GET /api/reservas/canchas -> devuelve todas las canchas
     */
    @GetMapping("/canchas")
    public List<Cancha> listarCanchasDisponibles() {
        return reservaService.listarCanchasDisponibles();
    }

}
