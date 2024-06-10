/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import Modelo.CircularListED;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML Button btnVolver;
    @FXML Button btnBorrar;
    @FXML Button btnEditar;
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
        cambiarFotoAnt();
        cambiarFotoSig();
        volver();
    }
    
    public void cargarVehiculo(Vehiculo v){
        vehiculo = v;
        System.out.println(v.getFotos().size());
        cargarFotos();
        titulo.setText(vehiculo.getMarca()+" "+vehiculo.getModelo()+" - "+ vehiculo.getPrecio());
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
    
    private void cambiarFotoSig(){
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
    private void cambiarFotoAnt(){
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
    
    private void cargarListaReparaciones(){
        for(String s: vehiculo.getReparaciones() ){
            Label valor = new Label(s);
            valor.getStyleClass().add("texto");
            HBox hb = new HBox();
            ImageView imv = new ImageView(App.loadImage(App.pathIconos+"viñeta.png"));
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
        imv.setImage(App.loadImage(App.pathFotos+foto));
        imv.setPreserveRatio(true);
        imv.setFitWidth(ancho);
    
    }
    
    private void volver() {
        btnVolver.setOnAction((ActionEvent e) -> {
            App.historial.removeLast();
            FXMLLoader backloader = App.historial.getLast();
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            App.stage.setTitle("Catalogo de Vehiculos");
            App.stage.setScene(s);
        });
    }

    
}
