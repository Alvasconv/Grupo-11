/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metodos;

import Modelo.*;
import com.grupo_11.*;

/**
 *
 * @author hilda
 */
public class FiltroVehiculos {

    public static ArrayListED<Vehiculo> filtrarPorMarcaYModelo(ArrayListED<Vehiculo> vehiculos, String marca, String modelo) {
        ArrayListED<Vehiculo> resultado = new ArrayListED<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getMarca().equals(marca) && vehiculo.getModelo().equals(modelo)) {
                resultado.add(vehiculo);
            }
        }
        if (resultado.isEmpty()) {
            System.out.println("No existe el vehículo especificado.");
        }
        return resultado;
    }

    public static ArrayListED<Vehiculo> filtrarPorRangoDePrecio(ArrayListED<Vehiculo> vehiculos, double precioMin, double precioMax) {
        ArrayListED<Vehiculo> resultado = new ArrayListED<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPrecio() >= precioMin && vehiculo.getPrecio() <= precioMax) {
                resultado.add(vehiculo);
            }
        }
        if (resultado.isEmpty()) {
            System.out.println("No existen vehículos dentro del rango de precio especificado.");
        }
        return resultado;
    }

    public static ArrayListED<Vehiculo> filtrarPorRangoDeKilometraje(ArrayListED<Vehiculo> vehiculos, int kmMin, int kmMax) {
        ArrayListED<Vehiculo> resultado = new ArrayListED<>();
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getKilometraje() >= kmMin && vehiculo.getKilometraje() <= kmMax) {
                resultado.add(vehiculo);
            }
        }
        if (resultado.isEmpty()) {
            System.out.println("No existen vehículos dentro del rango de kilometraje especificado.");
        }
        return resultado;
    }

}
