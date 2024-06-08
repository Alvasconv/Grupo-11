/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class AddVehiculo2Controller implements Initializable {

    @FXML private Button btnFinalizar;
    @FXML private Button btnVolver;
    @FXML private Button btnAdd;
    @FXML private Button btnBorrar;
    @FXML private TextField txtMotor;
    @FXML private TextField txtUbicacion;
    @FXML private TextField txtTransmision;
    @FXML private TextField txtPeso;
    @FXML private TextField txtReparacion;
    @FXML private ListView listReparaciones;
    
    ObservableList<String> oListR;
    ArrayListED<String>  listR = new ArrayListED<>();
    private Vehiculo vehiculoCreado;
    String selectedItem;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oListR = FXCollections.observableArrayList();
        addReparaciones();
        volver();
        finalizar();
         listReparaciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedItem = newValue;
                System.out.println("Selected item: " + selectedItem); // Mensaje de depuración
            }
        });
        borrarDeLista();
    }
    
    private void addReparaciones(){
        btnAdd.setOnAction((e)->{
            if(!txtReparacion.getText().isEmpty()){
                oListR.add(txtReparacion.getText());
                listR.add(txtReparacion.getText());
                listReparaciones.setItems(oListR);
                txtReparacion.clear();
            }
        });
    }
    
    private void creacionVehiculo(){
        vehiculoCreado.setTransmision(txtTransmision.getText());
        vehiculoCreado.setUbicacion(txtUbicacion.getText());
        vehiculoCreado.setMotor(txtMotor.getText());
        vehiculoCreado.setReparaciones(listR);
        vehiculoCreado.setPeso(Integer.parseInt(txtPeso.getText()));
    }
    
    private void saveFile(String path,File archivo){
        File destFile = new File(path, archivo.getName());
        
        try (FileInputStream fis = new FileInputStream(archivo);
             FileOutputStream fos = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {fos.write(buffer, 0, length);}
            
            System.out.println("Archivo guardado en: " + destFile.getAbsolutePath());
        } catch (IOException e) {e.getMessage();}
    }
    
    private void finalizar(){
        btnFinalizar.setOnMouseClicked((MouseEvent e)->{
            creacionVehiculo();
            App.historial.removeLast();
            App.historial.removeLast();
            FXMLLoader backloader = App.historial.getLast();
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            App.stage.setScene(s);
            vehiculoCreado.guardarVehiculo();
        });
    }
    
    private void borrarDeLista(){
        btnBorrar.setOnMouseClicked((MouseEvent e)->{
            oListR.remove(selectedItem);
            listReparaciones.setItems(oListR);
        });
    }
    
    private void volver(){
        btnVolver.setOnMouseClicked((MouseEvent e)->{
            FXMLLoader backloader = App.historial.getBack(App.actualFxml);
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            App.stage.setScene(s);
        });
    }
    
    public void cargarVehiculo(Vehiculo v){
        vehiculoCreado = v;
        System.out.println(vehiculoCreado.toString());
    }
    
}
