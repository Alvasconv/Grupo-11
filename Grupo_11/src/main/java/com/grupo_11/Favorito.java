/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo_11;

/**
 *
 * @author hilda
 */
public class Favorito {

    private Vehiculo vehiculo;
    private boolean esFavorito;

    public Favorito(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        this.esFavorito = true;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public boolean isFavorito() {
        return esFavorito;
    }

    public void setFavorito(boolean esFavorito) {
        this.esFavorito = esFavorito;
    }
}
