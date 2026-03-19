package com.clubdeportivo2.servicioreservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.clubdeportivo2.servicioreservas.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByCanchaId(Long canchaId);

}