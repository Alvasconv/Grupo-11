/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class EditarVehiculo2Controller implements Initializable {

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
    ArrayListED<String>  listFiles;
    private Vehiculo vehiculo;
    String selectedItem;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(oListR==null){oListR = FXCollections.observableArrayList();}
        addReparaciones();
        volver();
        finalizar();
        configListaReparaciones();
        borrarDeLista();
        
    }
    
    private void addReparaciones() {
        btnAdd.setOnAction((e) -> {
            if (!txtReparacion.getText().isEmpty()) {
                oListR.add(txtReparacion.getText());
                listReparaciones.setItems(oListR);
                txtReparacion.clear();
            }
        });
    }
    
    private void configListaReparaciones(){
        listReparaciones.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectedItem = newValue;
                System.out.println("Selected item: " + selectedItem);
            }
        });
    }
    
    private boolean creacionVehiculo() {
        vehiculo.setTransmision(txtTransmision.getText());
        vehiculo.setUbicacion(txtUbicacion.getText());
        vehiculo.setMotor(txtMotor.getText());
        vehiculo.getReparaciones().clear();
        try {
            double peso = Double.parseDouble(txtPeso.getText().trim());
            if (peso <= 0) {
                throw new IllegalArgumentException("Por favor, ingresa un peso válido.");
            }
            vehiculo.setPeso(peso);
            for(String s: oListR){
                vehiculo.getReparaciones().add(s);
            }

            return true; 
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de formato");
            alert.setContentText("Por favor, ingresa un número válido en el campo de peso.");
            alert.showAndWait();
            return false; 
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de entrada");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return false; 
        }
    }
    
    private void saveFile(String path,File archivo, String key){
        File destFile = new File(path, key+archivo.getName());
        
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
            String uniqueID = UUID.randomUUID().toString();
            uniqueID = uniqueID.substring(0, 7);
            vehiculo.getFotos().clear();
            for(String s: listFiles){
                File f = new File(s);
                saveFile(App.pathFotos,f,uniqueID);
                vehiculo.addFoto(uniqueID+f.getName());
            }
            try {
                borrarVehiculoAnterior();
            } catch (IOException ex) {
                ex.getMessage();
                System.out.println("Fallo borrar vehiculo anterior");
            }
            
            vehiculo.guardarVehiculo();
            App.historial.removeLast();
            App.historial.removeLast();
            App.actualFxml = App.historial.getLast();
            DetallesVehiculoController controller = App.historial.getLast().getController();
            controller.cargarVehiculo(vehiculo);
            EditarVehiculoController.actualStage.close();
        });
    }
    
    private void borrarDeLista() {
        btnBorrar.setOnMouseClicked((MouseEvent e) -> {
            if(selectedItem==null) {return;}
            oListR.remove(selectedItem);
            listReparaciones.setItems(oListR);
        });
    }

    
    public void cargarVehiculo(Vehiculo v, ArrayListED<String> ls){
        listFiles = ls;
        cerrarVentana();
        if(vehiculo!=null) {return;}
        vehiculo = v;
        
        txtTransmision.setText(v.getTransmision());
        txtUbicacion.setText(v.getUbicacion());
        txtMotor.setText(v.getMotor());
        txtPeso.setText(String.valueOf(v.getPeso()));
        
        if(oListR==null){oListR = FXCollections.observableArrayList();}
        for(String s: v.getReparaciones()){
            oListR.add(s);
        }
        listReparaciones.setItems(oListR);
    }
    
    private void volver() {
        btnVolver.setOnMouseClicked((MouseEvent e) -> {
            creacionVehiculo();
            FXMLLoader backloader = App.historial.getBack(App.actualFxml);
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            for(String f: listFiles){
            System.out.println(f);
        }
            EditarVehiculoController.actualStage.setScene(s);
        });
    }
    private void cerrarVentana(){
        EditarVehiculoController.actualStage.setOnCloseRequest(e->{
            App.historial.removeLast();
            App.historial.removeLast();
            App.actualFxml = App.historial.getLast();
        });
    }
    
    private void borrarVehiculoAnterior() throws FileNotFoundException, IOException{
        try {
                String csvFilePath = Vehiculo.archivoVehiculos; 
                ArrayListED<String> newLines = new ArrayListED<>();

                try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (!datos[1].trim().equals(EditarVehiculoController.vehiculoAnterior.getMarca()) ||
                            !datos[2].trim().equals(EditarVehiculoController.vehiculoAnterior.getModelo()) ||
                            !datos[3].trim().equals(String.valueOf(EditarVehiculoController.vehiculoAnterior.getAño()))) {
                            newLines.add(line);
                        }
                    }
                }

                try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath))) {
                    for (String line : newLines) {
                        bw.write(line);
                        bw.newLine();
                    }
                }
                } catch (IOException e) {
                e.printStackTrace();
            }

    }

}
