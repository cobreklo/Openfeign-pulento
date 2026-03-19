package com.clubdeportivo2.servicioreservas.model.dto;

public class Cancha {

    private Long id;
    private String nombre;
    private String tipo;
    private double precioPorHora;

    public Cancha() {}

    public Cancha(Long id, String nombre, String tipo, double precioPorHora) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioPorHora = precioPorHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecioPorHora() {
        return precioPorHora;
    }

    public void setPrecioPorHora(double precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

}
