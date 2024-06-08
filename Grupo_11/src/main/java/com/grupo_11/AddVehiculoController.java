/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class AddVehiculoController implements Initializable {

    @FXML private Button btnContinuar;
    @FXML private Button btnVolver;
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtAnio;
    @FXML private TextField txtKilometraje;
    @FXML private TextField txtPrecio;
    @FXML private VBox panelFotos;
    
    private Vehiculo vehiculoCreado;
    private ImageView selectedItem;
    private CircularListED<String> clFotos;
    protected ArrayListED<File> filesFotos;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addFotos();
        continuar();
        volver();
        deleteImg();
    }    
      
    private void addFotos(){
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if(filesFotos==null) { filesFotos = new ArrayListED<>();}
                if(clFotos==null) { clFotos = new CircularListED<>();}
                //Cargar foto del usuario
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files","*.png", "*.jpg", "*.jpeg", "*.gif")
                );
                File fotoFile = fileChooser.showOpenDialog(null);
                //Creacion de los ImageView con la foto cargada
                ImageView imgv = new ImageView();
                imgv.setImage(new Image(fotoFile.toURI().toString()));
                imgv.setFitWidth(200);
                imgv.setPreserveRatio(true);
                imgv.setOnMouseClicked(itemResaltado());

                panelFotos.getChildren().add(imgv);
                clFotos.add(fotoFile.getName());
                filesFotos.add(fotoFile);
            }     
      }); 
    }
    
    private void creacionVehiculo(){
        if(vehiculoCreado==null) { vehiculoCreado = new Vehiculo();}
        
        vehiculoCreado.setMarca(txtMarca.getText());
        vehiculoCreado.setModelo(txtModelo.getText());
        vehiculoCreado.setAño(Integer.parseInt(txtAnio.getText()));
        vehiculoCreado.setKilometraje(Integer.parseInt(txtKilometraje.getText()));
        vehiculoCreado.setPrecio(Integer.parseInt(txtPrecio.getText()));
        vehiculoCreado.setFotos(clFotos);
    }
    
    private void continuar(){
        btnContinuar.setOnAction((ActionEvent e)->{
            creacionVehiculo();
            try { pasarVehiculo();} 
            catch (IOException ex) { ex.getMessage(); }
        });
    }
    
    private void volver(){
        btnVolver.setOnAction((ActionEvent e)->{
            FXMLLoader backLoader = App.historial.getBack(App.actualFxml);
            try {
                Scene s = new Scene(backLoader.load());
                App.stage.setScene(s);
            } catch (IOException ex) { ex.printStackTrace();}
        });
    }
    
    
    private void pasarVehiculo() throws IOException{
        FXMLLoader loader;
        Parent p;
        Scene nextScene;
        if(App.historial.hasNext(App.actualFxml)){
            loader = App.historial.getNext(App.actualFxml);
            p = loader.getRoot();
            nextScene = p.getScene();
        }else{
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddVehiculo2.fxml"));
            App.historial.add(loader, App.actualFxml);
            p = loader.load();
            nextScene = new Scene(p);
        }
        App.actualFxml = loader;
        AddVehiculo2Controller controller = loader.getController();
        controller.cargarVehiculo(vehiculoCreado);
        App.stage.setScene(nextScene);
    }
    
   public EventHandler<MouseEvent> itemResaltado() {
        return e -> {
            if (e.getSource() instanceof ImageView) {
                ImageView imv = (ImageView) e.getSource();

                for (Node node : panelFotos.getChildren()) {
                    ((ImageView) node).setEffect(null);
                }
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.DODGERBLUE);
                dropShadow.setRadius(5);
                dropShadow.setSpread(0.5);
                imv.setEffect(dropShadow);

                selectedItem = imv;
            }
        };
    }
    
    public void deleteImg(){
        btnDelete.setOnMouseClicked((MouseEvent e)->{ 
            if (selectedItem == null) {return;}
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText(null);
                alert.setContentText("Seguro de borrar esta foto?");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.initStyle(StageStyle.UTILITY);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    panelFotos.getChildren().remove(selectedItem);
                    selectedItem = null;
                }
        });
    }
    
}
