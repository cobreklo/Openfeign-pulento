package com.clubdeportivo.serviciocanchas.services;
import java.util.List;
import com.clubdeportivo.serviciocanchas.model.Cancha;

public interface CanchaServices {

    List<Cancha> listarCanchas();

    Cancha crearCancha(Cancha cancha);
    
    Cancha buscarCancha(Long id);

}
