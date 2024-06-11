/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class TablaParametrizadaController implements Initializable {

    @FXML private VBox listaVehiculos;
    @FXML private HBox panelComparativa;
    @FXML private Button btnVolver;
    @FXML private Button btnAñadir;
    
    Vehiculo actualVehiculo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mostrarVehiculos(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
        } catch (Exception e) {
            System.out.println(e);
        }
        añadir();
        volver();
    }
    
    public void mostrarVehiculos(ArrayListED<Vehiculo> lst) {

        for (Vehiculo v : lst) {
            HBox hboxVehiculo = new HBox();
            hboxVehiculo.setSpacing(10);
            hboxVehiculo.getStyleClass().add("fondoCuadroVehiculo");

            String imagePath = Vehiculo.carpetaFotos + v.getFotos().first.getContent();
            File imageFile = new File(imagePath);

            ImageView imageView = new ImageView(new Image(imageFile.toURI().toString()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            VBox vboxDatos = new VBox();
            vboxDatos.setSpacing(5);

            Label marcaV = new Label(v.getMarca());
            marcaV.getStyleClass().add("texto1");

            Label kilometraje = new Label(v.getAño() + " - " + v.getKilometraje() + " km");
            kilometraje.getStyleClass().add("texto");

            Label ubicacion = new Label(v.getUbicacion());
            ubicacion.getStyleClass().add("texto");
            
            Label precioV = new Label("$"+v.getPrecio());
            precioV.getStyleClass().add("texto1");

            vboxDatos.getChildren().addAll(marcaV, kilometraje, ubicacion, precioV);

            hboxVehiculo.getChildren().addAll(imageView, vboxDatos);
            
            hboxVehiculo.setOnMouseClicked((MouseEvent e) -> {
               actualVehiculo = v;
            });
            
            listaVehiculos.getChildren().add(hboxVehiculo);
           
        }
        
    }
    
    public void addVehiculo(Vehiculo v){
        VBox vb = new VBox();
        
        Label lb1 = new Label(v.getMarca()+" "+v.getModelo());
        lb1.getStyleClass().add("texto2");
        
        Label lb2 = new Label(""+v.getAño());
        lb2.getStyleClass().add("texto3");
        
        Label lb3 = new Label(""+v.getPrecio());
        lb3.getStyleClass().add("texto3");
        
        Label lb4 = new Label(v.getUbicacion());
        lb4.getStyleClass().add("texto3");
        
        Label lb5 = new Label(""+v.getKilometraje());
        lb5.getStyleClass().add("texto3");
        
        Label lb6 = new Label(""+v.getPeso());
        lb6.getStyleClass().add("texto3");
        
        Label lb7 = new Label(""+v.getMotor());
        lb7.getStyleClass().add("texto3");
        
        Label lb8 = new Label(""+v.getTransmision());
        lb8.getStyleClass().add("texto3");
        
        vb.getChildren().addAll(lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8);
        vb.setAlignment(Pos.TOP_CENTER);
        vb.setFillWidth(true);
        panelComparativa.getChildren().add(vb);
    }
    
    private void añadir(){
        btnAñadir.setOnMouseClicked(e->{
            if(actualVehiculo==null){return;}
            addVehiculo(actualVehiculo);
            actualVehiculo=null;
        });
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
}
