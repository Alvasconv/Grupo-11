/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import Modelo.CircularListED;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class Vehiculo implements Serializable {

    private int precio;
    private String marca;
    private String modelo;
    private int año;
    private int kilometraje;
    private String motor;
    private String transmision;
    private int peso;
    private String ubicacion;
    private ArrayListED<String> reparaciones;
    private CircularListED<String> fotos;
    public static String archivoVehiculos= "src/main/resources/archivos/vehiculos.ser";
    public static String carpetaReparaciones = "src/main/resources/archivos/reparaciones/";
    public static String carpetaFotos = "src/main/resources/archivos/fotos/";

    public Vehiculo(int precio, String marca, String modelo, int año, int kilometraje, String motor, String transmision, int peso, String ubicacion, ArrayListED<String> reparaciones, CircularListED<String> fotos) {
        this.precio = precio;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.kilometraje = kilometraje;
        this.motor = motor;
        this.transmision = transmision;
        this.peso = peso;
        this.ubicacion = ubicacion;
        this.reparaciones = reparaciones;
        this.fotos = fotos;
    }

    public static void guardarListaVehiculos(ArrayListED<Vehiculo> vehiculos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoVehiculos))) {
            oos.writeObject(vehiculos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarVehiculo() {
        ArrayListED<Vehiculo> vehiculos = leerListaVehiculos();
        vehiculos.add(this);
        guardarListaVehiculos(vehiculos);
    }

    public static ArrayListED<Vehiculo> leerListaVehiculos() {
        ArrayListED<Vehiculo> vehiculos = new ArrayListED<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoVehiculos))) {
            vehiculos = (ArrayListED<Vehiculo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return vehiculos;
    }


    public void addFoto(String foto) {
        this.fotos.add(foto);
    }

    
    public void leerFotos(){
    
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

    public void setKilometraje(int kilometraje) {
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

    public void setPeso(int peso) {
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
