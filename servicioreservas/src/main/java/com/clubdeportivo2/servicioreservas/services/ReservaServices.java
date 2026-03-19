package com.clubdeportivo2.servicioreservas.services;

import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import java.util.List;

public interface ReservaServices {

   List<Reserva> listarReservas();

    Reserva crearReserva(Reserva reserva);

    List<Reserva> buscarReservasPorCancha(Long canchaId);

    // Método que usa OpenFeign para consultar la cancha en el otro microservicio
    Cancha obtenerCanchaDeReserva(Long canchaId);

    // Método que usa OpenFeign para traer todas las canchas del microservicio de Canchas
    List<Cancha> listarCanchasDisponibles();
}
