/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import Modelo.CircularListED;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author USER
 */
public class Vehiculo {

    private int precio;
    private String marca;
    private String modelo;
    private int año;
    private double kilometraje;
    private String motor;
    private String transmision;
    private double peso;
    private String ubicacion;
    private ArrayListED<String> reparaciones;
    private CircularListED<String> fotos;

    public Vehiculo(String s) {
        String[] parts = s.split(", ");
        this.precio = Integer.parseInt(parts[0].split("=")[1]);
        this.marca = parts[1].split("=")[1];
        this.modelo = parts[2].split("=")[1];
        this.año = Integer.parseInt(parts[3].split("=")[1]);
        this.kilometraje = Double.parseDouble(parts[4].split("=")[1]);
        this.motor = parts[5].split("=")[1];
        this.transmision = parts[6].split("=")[1];
        this.peso = Double.parseDouble(parts[7].split("=")[1]);
        this.ubicacion = parts[8].split("=")[1];

        String[] reparacionesS = parts[9].split("=")[1].split(",");
        this.reparaciones = new ArrayListED<>();
        for (String reparacion : reparacionesS) {
            this.reparaciones.add(reparacion);
        }

        String[] fotosArray = parts[10].split("=")[1].split(",");
        this.fotos = new CircularListED<>();
        for (String foto : fotosArray) {
            this.fotos.add(foto);
        }

    }

    @Override
    public String toString() {
        return "Vehiculo{" + "precio=" + precio + ", marca=" + marca + ", modelo=" + modelo + ", a\u00f1o=" + año + ", kilometraje=" + kilometraje + ", motor=" + motor + ", transmision=" + transmision + ", peso=" + peso + ", ubicacion=" + ubicacion + ", reparaciones=" + reparaciones + ", fotos=" + fotos + '}';
    }

    public static void saveListToFile(String nfile, ArrayListED<Vehiculo> vehiculos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nfile))) {
            for (Vehiculo vehiculo : vehiculos) {
                writer.write(vehiculo.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayListED<Vehiculo> readListFromFile(String nfile) {
        ArrayListED<Vehiculo> vehiculos = new ArrayListED<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nfile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vehiculo vehiculo = new Vehiculo(line);
                vehiculos.add(vehiculo);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return vehiculos;

    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getTransmision() {
        return transmision;
    }

    public void setTransmision(String transmision) {
        this.transmision = transmision;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public ArrayListED<String> getReparaciones() {
        return reparaciones;
    }

    public void setReparaciones(ArrayListED<String> reparaciones) {
        this.reparaciones = reparaciones;
    }

    public CircularListED<String> getFotos() {
        return fotos;
    }

    public void setFotos(CircularListED<String> fotos) {
        this.fotos = fotos;
    }

}
