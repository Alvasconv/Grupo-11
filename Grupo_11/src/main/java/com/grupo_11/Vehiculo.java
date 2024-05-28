/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayList;
import Modelo.CircularList;
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
    private ArrayList<String> reparaciones; 
    private CircularList<String> fotos; 
    
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
        this.reparaciones = new ArrayList<>();
        for (String reparacion : reparacionesS) {
            this.reparaciones.add(reparacion);
        }
            
        String[] fotosArray = parts[10].split("=")[1].split(",");
        this.fotos = new CircularList<>();
        for (String foto : fotosArray) {
            this.fotos.add(foto);
        }
        
    }

    @Override
    public String toString() {
        return "Vehiculo{" + "precio=" + precio + ", marca=" + marca + ", modelo=" + modelo + ", a\u00f1o=" + año + ", kilometraje=" + kilometraje + ", motor=" + motor + ", transmision=" + transmision + ", peso=" + peso + ", ubicacion=" + ubicacion + ", reparaciones=" + reparaciones + ", fotos=" + fotos + '}';
    }
    
    public static void saveListToFile(String nfile, ArrayList<Vehiculo> vehiculos) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(nfile))) {
        for (Vehiculo vehiculo : vehiculos) {
            writer.write(vehiculo.toString()); 
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println(e.getMessage());
    }
    }
    
    public static ArrayList<Vehiculo> readListFromFile(String nfile) {
    ArrayList<Vehiculo> vehiculos = new ArrayList<>();
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
}
