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
import java.util.UUID;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class AddVehiculo2Controller implements Initializable {

    @FXML
    private Button btnFinalizar;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBorrar;
    @FXML
    private TextField txtMotor;
    @FXML
    private TextField txtUbicacion;
    @FXML
    private TextField txtTransmision;
    @FXML
    private TextField txtPeso;
    @FXML
    private TextField txtReparacion;
    @FXML
    private ListView listReparaciones;
    @FXML
    private Label info2;

    ObservableList<String> oListR;
    ArrayListED<String> listR = new ArrayListED<>();
    ArrayListED<File> listFiles;
    private Vehiculo vehiculoCreado;
    String selectedItem;
    //private PrimaryController primary;
//
//    public AddVehiculo2Controller(PrimaryController primary) {
//        this.primary = primary;
//    }

    /**
     * Initializes the controller class.
     *
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
                System.out.println("Selected item: " + selectedItem);
            }
        });
        borrarDeLista();
    }
    
    
    public void cargarVehiculo(Vehiculo v, ArrayListED<File> ls) {
        vehiculoCreado = v;
        listFiles = ls;
    }

    
    private void addReparaciones() {
        btnAdd.setOnAction((e) -> {
            if (!txtReparacion.getText().isEmpty()) {
                oListR.add(txtReparacion.getText());
                listR.add(txtReparacion.getText());
                listReparaciones.setItems(oListR);
                txtReparacion.clear();
            }
            System.out.println("Lista Reparaciones "+ listR.size()+" -> "+ listR.print());
            System.out.println("ObsList Reparaciones "+ oListR.size()+" -> ");
            oListR.forEach((x)-> System.out.println(x));
        });
    }

    private boolean creacionVehiculo() {
        vehiculoCreado.setTransmision(txtTransmision.getText());
        vehiculoCreado.setUbicacion(txtUbicacion.getText());
        vehiculoCreado.setMotor(txtMotor.getText());
        vehiculoCreado.setReparaciones(listR);

        try {
            double peso = Double.parseDouble(txtPeso.getText().trim());
            if (peso <= 0) {
                throw new IllegalArgumentException("Por favor, ingresa un peso válido.");
            }
            vehiculoCreado.setPeso(peso);

            return true;
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de formato");
            alert.setContentText("Por favor, ingresa un número válido en el campo de peso.");
            alert.showAndWait();
            return false;
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de entrada");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return false;
        }
    }

    
    private void finalizar() {
        btnFinalizar.setOnMouseClicked((MouseEvent e) -> {
            if (txtMotor.getText().isEmpty() || txtTransmision.getText().isEmpty() || txtPeso.getText().isEmpty()
                    || txtUbicacion.getText().isEmpty() || listReparaciones.getItems().isEmpty()) {
                info2.setText("ⓘ No se han completado todos los campos");
                return;
            }
            if (!creacionVehiculo()) {
                return;
            }

            App.historial.removeLast();
            App.historial.removeLast();
            FXMLLoader backloader = App.historial.getLast();
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;           
            vehiculoCreado.guardarVehiculo();
            guardarFotos(listFiles);
            
            PrimaryController controller = backloader.getController();
            controller.mostrarVehiculos(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
            App.stage.setTitle("Catalogo de Vehiculos");

//            ArrayListED<Vehiculo> listaVehiculos = Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos);
//            primary.mostrarVehiculos(listaVehiculos);

            App.stage.setScene(s);

        });

    }

    private void borrarDeLista() {
        btnBorrar.setOnMouseClicked((MouseEvent e) -> {
            if(selectedItem==null) {return;}
            listR.remove(selectedItem);
            oListR.remove(selectedItem);
            listReparaciones.setItems(oListR);
        });
    }

    private void volver() {
        btnVolver.setOnMouseClicked((MouseEvent e) -> {
            FXMLLoader backloader = App.historial.getBack(App.actualFxml);
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            App.stage.setScene(s);
        });
    }


    private void guardarFotos(ArrayListED<File> files){
        String uniqueID = UUID.randomUUID().toString();
        for (File f : files) {
                saveFile(App.pathFotos, f, uniqueID.substring(0, 7));
            }
    }
    
    
    private void saveFile(String path, File archivo, String nombre) {
        File destFile = new File(path, nombre+archivo.getName());

        try (FileInputStream fis = new FileInputStream(archivo); FileOutputStream fos = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

            System.out.println("Archivo guardado en: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.getMessage();
        }
    }

}
