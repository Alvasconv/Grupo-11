/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    @FXML Button favorito;
    
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
        editarVehiculo();
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
        if(!panelInfo.getChildren().isEmpty()){panelInfo.getChildren().clear();}
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
            PrimaryController controller = backloader.getController();
            controller.mostrarVehiculos(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
            App.stage.setTitle("Catalogo de Vehiculos");
            App.stage.setScene(s);
        });
    }

    @FXML
    private void borrarVehiculo(ActionEvent event) {
         if (vehiculo != null) {
            try {
                String csvFilePath = Vehiculo.archivoVehiculos; 
                ArrayListED<String> newLines = new ArrayListED<>();

                try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] datos = line.split(",");
                        if (!datos[1].trim().equals(vehiculo.getMarca()) ||
                            !datos[2].trim().equals(vehiculo.getModelo()) ||
                            !datos[3].trim().equals(String.valueOf(vehiculo.getAño()))) {
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

            App.historial.removeLast();
            FXMLLoader backloader = App.historial.getLast();
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            PrimaryController controller = backloader.getController();
            controller.mostrarVehiculos(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
            App.stage.setTitle("Catálogo de Vehículos");
            App.stage.setScene(s);
            
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
//     @FXML
//    private void marcarFavorito(ActionEvent event) {
//        // Implementación del método de acción
//        
//    }
//    

     private void editarVehiculo(){
        btnEditar.setOnMouseClicked((MouseEvent e)->{
            
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarVehiculo.fxml"));
                Parent p = loader.load();
                Stage newStage = new Stage();
                newStage.getIcons().add(App.loadImage(App.pathIconos+"icono.png"));
                newStage.setTitle("Editar Vehiculo");
                EditarVehiculoController controller = loader.getController();
                controller.cargarInfo(vehiculo, newStage, loader);
                App.historial.add(loader);
                App.actualFxml = loader;
                newStage.setScene(new Scene(p, 600, 450));

                newStage.initModality(Modality.APPLICATION_MODAL);
                newStage.initOwner(null);
                newStage.showAndWait();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
