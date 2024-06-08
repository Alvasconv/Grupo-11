package com.grupo_11;

import Metodos.FiltroVehiculos;
import Modelo.ArrayListED;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController {


    @FXML
    private VBox lstvehiculos;
    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonRemover;
    @FXML
    private ComboBox<String> marca;
    @FXML
    private ComboBox<String> modelo;
    @FXML
    private TextField precioMax;
    @FXML
    private TextField precioMin;
    @FXML
    private TextField kilometrajeMax;
    @FXML
    private TextField kilometrajeMin;
    
    public void initialize() {
        mostrarVehiculos(Vehiculo.leerListaVehiculos()); 
    }
    private void regresar() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void añadirVehiculo(ActionEvent event) throws IOException {
        App.setRoot("añadirV");
    }

    @FXML
    private void editarVehiculo(ActionEvent event) {
    }

    @FXML
    private void RemoverVehiculo(ActionEvent event) {
    }
    
    public void mostrarVehiculos(ArrayListED<Vehiculo> lst){
        for (Vehiculo v: lst){
            HBox hboxVehiculo = new HBox();
            hboxVehiculo.setSpacing(10);
            ImageView imageView = new ImageView(v.getFotos().first.getContent());
            imageView.setFitWidth(60);
            imageView.setFitHeight(60);
            VBox vboxDatos = new VBox();
            vboxDatos.setSpacing(5);
//
            vboxDatos.getChildren().addAll(
                new Label("Marca: " + v.getMarca()),
                new Label("Año: " + v.getAño()),
                new Label("Kilometraje: " + v.getKilometraje())
//                new Label(v.getFotos().first.toString())
            );
//
            hboxVehiculo.getChildren().addAll( imageView,vboxDatos);
            lstvehiculos.getChildren().add(hboxVehiculo);
        }
            
    }

    @FXML
    private void filtrarVehiculos(ActionEvent event) {
        ArrayListED<Vehiculo> lst= Vehiculo.leerListaVehiculos();
        ArrayListED<Vehiculo> lstf1= FiltroVehiculos.filtrarPorMarcaYModelo(lst, marca.getValue().toString(), modelo.getValue().toString());
        ArrayListED<Vehiculo> lstf2 = FiltroVehiculos.filtrarPorRangoDePrecio(lst, Integer.parseInt(precioMin.getText()), Integer.parseInt(precioMax.getText()));
        mostrarVehiculos(lstf2);
        
    }
    
    private void actualizarDatos(){
        for (Vehiculo v: Vehiculo.leerListaVehiculos()){
            marca.getItems().add(v.getMarca());
            modelo.getItems().add(v.getModelo());

            
        }
    }
}
