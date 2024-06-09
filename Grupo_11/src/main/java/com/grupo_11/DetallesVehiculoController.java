/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import Modelo.CircularListED;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class DetallesVehiculoController implements Initializable {

    @FXML Button btnCerrar;
    @FXML Button btnCambiarIzq;
    @FXML Button btnCambiarDer;
    @FXML ImageView ftEstelar;
    @FXML ImageView ftAnterior;
    @FXML ImageView ftActual;
    @FXML ImageView ftSiguiente;
    @FXML VBox panelInfo;
    @FXML Label titulo;
    @FXML Label precio;
    @FXML Label marca;
    @FXML Label modelo;
    @FXML Label anio;
    @FXML Label ubicacion;
    @FXML Label kilometraje;
    @FXML Label motor;
    @FXML Label transmision;
    @FXML Label peso;
    
    private Vehiculo vehiculo;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CircularListED<String> cl = new CircularListED<>();
        cl.add("addIcon.png");
        cl.add("deleteIcon.png");
        cl.add("cerrarIcon.png");
        cl.add("icono.png");
        ArrayListED<String> ls = new ArrayListED<>();
        ls.add("focos dañados");
        ls.add("motor generico");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
        ls.add("discos cambiados");
 
        vehiculo = new Vehiculo(2000,"Audi","gtr",100,100,"v8","6 cilindros",200,"Guayaquil",ls,cl);
         for(int c=0; c<vehiculo.getFotos().size(); c++){
            System.out.println("Foto Anterior ind: "+c+" --> "+vehiculo.getFotos().get(c));
        }
        
        String u =vehiculo.getFotos().get(0);
        for(int c=0; c<10; c++){
            System.out.println("Foto U "+App.pathFotos+ u);
            System.out.println("Foto Anterior ind: "+c+" --> "+vehiculo.getFotos().getBack(u));
            System.out.println("Foto Siguiente ind: "+c+" --> "+vehiculo.getFotos().getNext(u));
            u = vehiculo.getFotos().getNext(u);
        }

        mostrarInfo();
        cambiarFotoAnt();
        cambiarFotoSig();
    }
    
    public void cargarVehiculo(Vehiculo v){
        vehiculo = v;
    }
    
    public void cambiarFotoSig(){
        btnCambiarDer.setOnAction(e->{
            String[] urlActual1 = ftActual.getImage().getUrl().split("/");
            String actual1 = urlActual1[urlActual1.length-1];
            setImage(ftEstelar,vehiculo.getFotos().getNext(actual1),300);
            setImage(ftActual,vehiculo.getFotos().getNext(actual1),80);
            setImage(ftAnterior,actual1,80);

            String[] urlActual2 = ftActual.getImage().getUrl().split("/");
            String actual2 = urlActual2[urlActual2.length-1];
            setImage(ftSiguiente,vehiculo.getFotos().getNext(actual2),80);
        });
    }
    public void cambiarFotoAnt(){
        btnCambiarIzq.setOnAction(e->{
            String[] urlActual1 = ftActual.getImage().getUrl().split("/");
            String actual1 = urlActual1[urlActual1.length-1];
            setImage(ftEstelar,vehiculo.getFotos().getBack(actual1),300);
            setImage(ftActual,vehiculo.getFotos().getBack(actual1),80);
            setImage(ftSiguiente,actual1,80);

            String[] urlActual2 = ftActual.getImage().getUrl().split("/");
            String actual2 = urlActual2[urlActual2.length-1];
            setImage(ftAnterior,vehiculo.getFotos().getBack(actual2),80);
        });
    }
    
    private void mostrarInfo(){
        titulo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo()+" - "+ vehiculo.getPrecio());
        cargarFotos();
        precio.setText(String.valueOf(vehiculo.getPrecio()));
        marca.setText(vehiculo.getMarca());
        modelo.setText(vehiculo.getModelo());
        anio.setText(String.valueOf(vehiculo.getAño()));
        ubicacion.setText(vehiculo.getUbicacion());
        kilometraje.setText(String.valueOf(vehiculo.getKilometraje()));
        peso.setText(String.valueOf(vehiculo.getPeso()));
        motor.setText(vehiculo.getMotor());
        transmision.setText(vehiculo.getTransmision());
        cargarListaReparaciones();
    }
    
    private void cargarListaReparaciones(){
        for(String s: vehiculo.getReparaciones() ){
            Label valor = new Label(s);
            valor.getStyleClass().add("texto");
            HBox hb = new HBox();
            ImageView imv = new ImageView(App.loadImage(App.pathFotos+"viñeta.png"));
            imv.setFitWidth(18);
            imv.setPreserveRatio(true);
            hb.getChildren().add(imv);
            hb.getChildren().add(valor);
            panelInfo.getChildren().add(hb);
        }
    }
    
    private void cargarFotos(){
        String actual =vehiculo.getFotos().get(0);
        setImage(ftEstelar,actual,300);
        setImage(ftActual,actual,300);
        setImage(ftSiguiente,vehiculo.getFotos().getNext(actual),80);
        setImage(ftAnterior,vehiculo.getFotos().getBack(actual),80);
    }
    
    private void setImage(ImageView imv, String foto, int ancho){
        imv.setImage(App.loadImage(App.pathIconos+foto));
        imv.setPreserveRatio(true);
        imv.setFitWidth(ancho);
    
    }

    
}
