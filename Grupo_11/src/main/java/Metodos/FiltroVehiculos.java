/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Metodos;

import java.util.List;
import java.util.stream.Collectors;
import com.grupo_11.*;

/**
 *
 * @author hilda
 */


public class FiltroVehiculos {

    public static List<Vehiculo> filtrarPorMarcaYModelo(List<Vehiculo> vehiculos, String marca, String modelo) {
        List<Vehiculo> filtrados = vehiculos.stream()
                .filter(v -> v.getMarca().equalsIgnoreCase(marca) && v.getModelo().equalsIgnoreCase(modelo))
                .collect(Collectors.toList());
        if (filtrados.isEmpty()) {
            System.out.println("No existe el vehículo especificado.");
        }
        return filtrados;
    }

    public static List<Vehiculo> filtrarPorRangoDePrecio(List<Vehiculo> vehiculos, double precioMin, double precioMax) {
        List<Vehiculo> filtrados = vehiculos.stream()
                .filter(v -> v.getPrecio() >= precioMin && v.getPrecio() <= precioMax)
                .collect(Collectors.toList());
        if (filtrados.isEmpty()) {
            System.out.println("No existen vehículos dentro del rango de precio especificado.");
        }
        return filtrados;
    }

    public static List<Vehiculo> filtrarPorRangoDeKilometraje(List<Vehiculo> vehiculos, int kmMin, int kmMax) {
        List<Vehiculo> filtrados = vehiculos.stream()
                .filter(v -> v.getKilometraje() >= kmMin && v.getKilometraje() <= kmMax)
                .collect(Collectors.toList());
        if (filtrados.isEmpty()) {
            System.out.println("No existen vehículos dentro del rango de kilometraje especificado.");
        }
        return filtrados;
    }

    public static List<Vehiculo> filtrarPorVariosCriterios(List<Vehiculo> vehiculos, String marca, String modelo, double precioMin, double precioMax, int kmMin, int kmMax) {
        List<Vehiculo> filtrados = vehiculos.stream()
                .filter(v -> v.getMarca().equalsIgnoreCase(marca) && v.getModelo().equalsIgnoreCase(modelo))
                .filter(v -> v.getPrecio() >= precioMin && v.getPrecio() <= precioMax)
                .filter(v -> v.getKilometraje() >= kmMin && v.getKilometraje() <= kmMax)
                .collect(Collectors.toList());
        if (filtrados.isEmpty()) {
            System.out.println("No existen vehículos que cumplan con los criterios especificados.");
        }
        return filtrados;
    }
}
