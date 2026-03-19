package com.clubdeportivo2.servicioreservas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.clubdeportivo2.servicioreservas.model.dto.Cancha;

/**
 * Cliente Feign para comunicarse con el microservicio de Canchas.
 * 
 * En lugar de armar manualmente las peticiones HTTP con RestTemplate o WebClient,
 * usamos esta interfaz declarativa: solo definimos los métodos y OpenFeign se
 * encarga de todo el trabajo pesado por nosotros (serialización, conexión, etc.).
 * 
 * La URL apunta directamente al servicio de Canchas que corre en el puerto 8081.
 */
@FeignClient(name = "serviciocanchas", url = "http://localhost:8081")
public interface CanchaFeignClient {

    /**
     * Obtiene la lista completa de canchas disponibles desde el otro microservicio.
     * Equivale a hacer un GET a http://localhost:8081/api/canchas
     */
    @GetMapping("/api/canchas")
    List<Cancha> listarCanchas();

    /**
     * Busca una cancha específica por su ID en el microservicio de Canchas.
     * Equivale a hacer un GET a http://localhost:8081/api/canchas/{id}
     */
    @GetMapping("/api/canchas/{id}")
    Cancha obtenerCanchaPorId(@PathVariable("id") Long id);

}
