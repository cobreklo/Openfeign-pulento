package com.clubdeportivo2.servicioreservas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.clubdeportivo2.servicioreservas.model.dto.Cancha;

@FeignClient(name = "serviciocanchas", url = "http://localhost:8081")
public interface CanchaFeignClient {

    /**
     * obtiene la lista completa de canchas disponibles desde el otro microservicio.
     * es como hacer un GET a http://localhost:8081/api/canchas
     */
    @GetMapping("/api/canchas")
    List<Cancha> listarCanchas();

    /**
     * busca una cancha específica por su ID en el microservicio de canchas.
     * es como hacer un GET a http://localhost:8081/api/canchas/{id}
     */
    @GetMapping("/api/canchas/{id}")
    Cancha obtenerCanchaPorId(@PathVariable("id") Long id);

}
