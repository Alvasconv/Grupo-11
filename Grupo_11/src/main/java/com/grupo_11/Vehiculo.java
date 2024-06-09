/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import Modelo.CircularListED;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author USER
 */
public class Vehiculo implements Serializable {

    private double precio;
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
    public static String archivoVehiculos = "src/main/resources/archivos/vehiculos.csv";
    public static String carpetaReparaciones = "src/main/resources/archivos/reparaciones/";
    public static String carpetaFotos = "src/main/resources/archivos/fotos/";

    public Vehiculo(double precio, String marca, String modelo, int año, double kilometraje, String motor, String transmision, double peso, String ubicacion, ArrayListED<String> reparaciones, CircularListED<String> fotos) {
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

    public Vehiculo() {
    }

    public static void guardarListaVehiculos(ArrayListED<Vehiculo> vehiculos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivoVehiculos))) {
            oos.writeObject(vehiculos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardarVehiculo() {
        ArrayListED<Vehiculo> vehiculos = leerListaVehiculos(archivoVehiculos);
        vehiculos.add(this);
        guardarListaVehiculos(vehiculos);
    }

//    public static ArrayListED<Vehiculo> leerListaVehiculos() {
//        ArrayListED<Vehiculo> vehiculos = new ArrayListED<>();
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivoVehiculos))) {
//            vehiculos = (ArrayListED<Vehiculo>) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return vehiculos;
//    }
    public static ArrayListED<Vehiculo> leerListaVehiculos(String archivoCSV) {
        ArrayListED<Vehiculo> vehiculos = new ArrayListED<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                double precio = Double.parseDouble(datos[0].trim());
                String marca = datos[1].trim();
                String modelo = datos[2].trim();
                int año = Integer.parseInt(datos[3].trim());
                double kilometraje = Double.parseDouble(datos[4].trim());
                String motor = datos[5].trim();
                String transmision = datos[6].trim();
                double peso = Double.parseDouble(datos[7].trim());
                String ubicacion = datos[8].trim();

                ArrayListED<String> reparaciones = new ArrayListED<>();
                String[] reparacionesArray = datos[9].split(";");
                for (String reparacion : reparacionesArray) {
                    reparaciones.add(reparacion.trim());
                }

                CircularListED<String> fotos = new CircularListED<>();
                String[] fotosArray = datos[10].split(";");
                for (String foto : fotosArray) {
                    fotos.add(foto.trim());
                }

                Vehiculo vehiculo = new Vehiculo(precio, marca, modelo, año, kilometraje, motor, transmision, peso, ubicacion, reparaciones, fotos);
                vehiculos.add(vehiculo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vehiculos;
    }

    public void addFoto(String foto) {
        this.fotos.add(foto);
    }

    public void leerFotos() {

    }

    public double getPrecio() {
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

    @Override
    public String toString() {
        return "Vehiculo{" + "precio=" + precio + ", marca=" + marca + ", modelo=" + modelo + ", a\u00f1o=" + año + ", kilometraje=" + kilometraje + ", motor=" + motor + ", transmision=" + transmision + ", peso=" + peso + ", ubicacion=" + ubicacion + ", reparaciones=" + reparaciones + ", fotos=" + fotos + '}';
    }

}
