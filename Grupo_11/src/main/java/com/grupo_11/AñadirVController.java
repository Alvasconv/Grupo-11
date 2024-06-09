/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import Modelo.CircularListED;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AñadirVController implements Initializable {

    @FXML
    private TextField precio;
    @FXML
    private TextField marca;
    @FXML
    private TextField modelo;
    @FXML
    private TextField año;
    @FXML
    private TextField ubicacion;
    @FXML
    private TextField motor;
    @FXML
    private TextField transmision;
    @FXML
    private TextField kilometraje;
    @FXML
    private TextField peso;

    private ArrayListED<String> reparaciones = new ArrayListED<>();
    private CircularListED<String> fotos = new CircularListED<>();
    private CircularListED<String> fotosTemp = new CircularListED<>();



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void regresar(ActionEvent event) throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void ingresar(ActionEvent event) throws IOException {
        System.out.println(fotosTemp);
        fotos=fotosTemp;
        Vehiculo v= new Vehiculo((Integer.parseInt(precio.getText())),marca.getText(),modelo.getText(),Integer.parseInt(año.getText()),Integer.parseInt(kilometraje.getText()),motor.getText(),transmision.getText(),Integer.parseInt(peso.getText()),ubicacion.getText(), reparaciones, fotos );
        v.guardarVehiculo();
        App.setRoot("primary");
    }

    @FXML
    private void añadirFotos(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione Imágenes");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                Image image = new Image(file.toURI().toString());
                String rutaArchivo = file.toURI().toString();
                fotosTemp.add(rutaArchivo); 
            } 
        }
    }

    @FXML
    private void añadirReparaciones(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione Imágenes");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(new Stage());
        if (selectedFiles != null) {
            for (File file : selectedFiles) {
                String rutaArchivo = file.toURI().toString();
                reparaciones.add(rutaArchivo); 
            } 
        }
    }
}
