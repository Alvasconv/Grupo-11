/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class FavoritosController implements Initializable {
    
    @FXML private Button btnVolver;
    @FXML private ScrollPane panel;
    @FXML private VBox lstvehiculos;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mostrarVehiculos(Vehiculo.leerListaVehiculos(App.pathArchivos+"favoritos.txt"));
        } catch (Exception e) {
            System.out.println(e);
        }
        volver();
    }   
    
     public void mostrarVehiculos(ArrayListED<Vehiculo> lst) {
         GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int numColumns = 2;
        int row = 0;
        int col = 0;

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
            
            gridPane.add(hboxVehiculo, col, row);
            col++;
            if (col >= numColumns) {
                col = 0;
                row++;
            }
        }
        panel.setContent(gridPane);
        panel.setPadding(new Insets(10));

        panel.setFitToWidth(true);
        panel.setFitToHeight(true);
        lstvehiculos.getChildren().clear();
        lstvehiculos.getChildren().add(panel);
        VBox.setVgrow(panel, Priority.ALWAYS);

        panel.setFitToWidth(false);
        lstvehiculos.getChildren().clear();
        lstvehiculos.getChildren().add(panel);
    }
     
     private void pasarInfoVehiculo(Vehiculo v) throws IOException {
        FXMLLoader loader;
        Parent p;
        Scene nextScene;
        if (App.historial.hasNext(App.actualFxml)) {
            loader = App.historial.getNext(App.actualFxml);
            p = loader.getRoot();
            nextScene = p.getScene();
        } else {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DetallesVehiculo.fxml"));
            App.historial.add(loader, App.actualFxml);
            p = loader.load();
            nextScene = new Scene(p);
        }
        App.actualFxml = loader;
        DetallesVehiculoController controller = loader.getController();
        controller.cargarVehiculo(v);
        App.stage.setTitle("Detalles del Vehiculo");
        App.stage.setScene(nextScene);
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
