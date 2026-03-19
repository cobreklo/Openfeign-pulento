package com.clubdeportivo2.servicioreservas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubdeportivo2.servicioreservas.client.CanchaFeignClient;
import com.clubdeportivo2.servicioreservas.model.Reserva;
import com.clubdeportivo2.servicioreservas.model.dto.Cancha;
import com.clubdeportivo2.servicioreservas.repository.ReservaRepository;
import java.util.List;

@Service
public class ReservaServicesImpl implements ReservaServices {

    @Autowired
    private ReservaRepository reservaRepository;

    // inyectamos el cliente Feign, spring se encarga de crear la implementación
    @Autowired
    private CanchaFeignClient canchaFeignClient;

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> buscarReservasPorCancha(Long canchaId) {
        return reservaRepository.findByCanchaId(canchaId);
    }

    // consulta los datos de una cancha específica llamando al microservicio de
    // canchas.
    // internamente, openfeign hace un GET a
    // http://localhost:8081/api/canchas/{canchaId}
    // y nos devuelve el objeto cancha ya deserializado.
    @Override
    public Cancha obtenerCanchaDeReserva(Long canchaId) {
        return canchaFeignClient.obtenerCanchaPorId(canchaId);
    }

    // trae la lista completa de canchas disponibles desde el otro microservicio.
    // openfeign se encarga de hacer el GET y convertir la respuesta JSON a objetos
    // Java.
    @Override
    public List<Cancha> listarCanchasDisponibles() {
        return canchaFeignClient.listarCanchas();
    }

}
