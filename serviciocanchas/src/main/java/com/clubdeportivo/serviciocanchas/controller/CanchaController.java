
package com.clubdeportivo.serviciocanchas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.clubdeportivo.serviciocanchas.model.Cancha;
import com.clubdeportivo.serviciocanchas.services.CanchaServices;

@RestController
@RequestMapping("/api/canchas")
public class CanchaController {

 @Autowired
    private CanchaServices canchaService;

    @GetMapping
    public List<Cancha> listarCanchas(){
        return canchaService.listarCanchas();
    }

    @PostMapping
    public Cancha crearCancha(@RequestBody Cancha cancha){
        return canchaService.crearCancha(cancha);
    }
   
    @GetMapping("/{id}")
    public Cancha buscarPorCancha(@PathVariable Long id){
        return canchaService.buscarCancha(id);
}
}
