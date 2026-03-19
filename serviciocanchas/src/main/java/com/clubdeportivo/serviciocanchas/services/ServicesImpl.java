package com.clubdeportivo.serviciocanchas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clubdeportivo.serviciocanchas.model.Cancha;
import com.clubdeportivo.serviciocanchas.repository.CanchaRepository;
import java.util.List;

@Service
public class ServicesImpl implements CanchaServices {

    @Autowired
    private CanchaRepository canchaRepository;

    @Override
    public List<Cancha> listarCanchas() {
        return canchaRepository.findAll();
    }

    @Override
    public Cancha crearCancha(Cancha cancha) {
        return canchaRepository.save(cancha);
    }

    @Override
    public Cancha buscarCancha(Long id) {
        return canchaRepository.findById(id).orElse(null);
    }

}
