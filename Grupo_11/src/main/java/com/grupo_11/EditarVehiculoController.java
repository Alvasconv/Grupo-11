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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author vv
 */
public class EditarVehiculoController implements Initializable {

    @FXML private Button btnContinuar;
    @FXML private Button btnAdd;
    @FXML private Button btnDelete;
    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtAnio;
    @FXML private TextField txtKilometraje;
    @FXML private TextField txtPrecio;
    @FXML private VBox panelFotos;
    
    private Vehiculo vehiculoEditado;
    static Vehiculo vehiculoAnterior;
    private ImageView selectedItem;
    private ArrayListED<String> fotosSubidas;
    static Stage actualStage;
    protected FXMLLoader actualLoader;
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addFotos();
        continuar();
        deleteImg();
        
    }    
      
    private void addFotos() {
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if (fotosSubidas == null) {fotosSubidas = new ArrayListED<>();}
                //Cargar foto del usuario
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
                );
                File fotoFile = fileChooser.showOpenDialog(null);
                //Creacion de los ImageView con la foto cargada
                ImageView imgv = new ImageView();
                imgv.setImage(new Image(fotoFile.toURI().toString()));
                imgv.setFitWidth(200);
                imgv.setPreserveRatio(true);
                imgv.setOnMouseClicked(itemResaltado());
                //Guarda el path completo de la foto cargada
                fotosSubidas.add(fotoFile.getPath());
                panelFotos.getChildren().add(imgv);
                
                System.out.println("clFotos "+fotosSubidas.size()+" -> "+fotosSubidas.print());
            }
        });
    }

    private boolean creacionVehiculo() {
        if (vehiculoEditado == null) {
            vehiculoEditado = new Vehiculo();
        }

        vehiculoEditado.setMarca(txtMarca.getText());
        vehiculoEditado.setModelo(txtModelo.getText());

        try {
            int año = Integer.parseInt(txtAnio.getText().trim());
            if (año < 1800 || año > 2024) {
                throw new IllegalArgumentException("Por favor, ingresa un año válido.");
            }

            // Validar que el campo de kilometraje sea un número válido
            double kilometraje = Double.parseDouble(txtKilometraje.getText().trim());
            if (kilometraje < 0) {
                throw new IllegalArgumentException("Por favor, ingresa un kilometraje válido.");
            }

            // Validar que el campo de precio sea un número válido
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (precio <= 0) {
                throw new IllegalArgumentException("Por favor, ingresa un precio válido.");
            }

            // Si todas las validaciones pasan, asigna los valores al vehículo
            vehiculoEditado.setMarca(txtMarca.getText());
            vehiculoEditado.setModelo(txtModelo.getText());
            vehiculoEditado.setAño(Integer.parseInt(txtAnio.getText()));
            vehiculoEditado.setKilometraje(Double.parseDouble(txtKilometraje.getText()));
            vehiculoEditado.setPrecio(Double.parseDouble(txtPrecio.getText()));
            

            return true;
        } catch (NumberFormatException e) {
            // Si ocurre un error al convertir a número, muestra un mensaje de error
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de formato");
            alert.setContentText("Por favor, ingresa los datos correctamente.");
            alert.showAndWait();
            return false;
        } catch (IllegalArgumentException e) {
            // Si falla alguna validación, muestra un mensaje de error con la descripción del problema
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de entrada");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return false;
        }
    }
    
    private void continuar() {
        btnContinuar.setOnAction((ActionEvent e) -> {
            // Validar si todos los campos están llenos
            if (txtMarca.getText().isEmpty() || txtModelo.getText().isEmpty() || txtPrecio.getText().isEmpty()
                    || txtKilometraje.getText().isEmpty() || panelFotos.getChildren().isEmpty()) {
                return; // Salir del método si no se han completado todos los campos
            }
            if (!creacionVehiculo()) {
                return; // Salir del método si la creación del vehículo falla
            }

            try {
                pasarInfoVehiculo();
            } catch (IOException ex) {
                ex.getMessage();
            }
        });
    }
  
    private void pasarInfoVehiculo() throws IOException{
        FXMLLoader loader;
        Parent p;
        Scene nextScene;

        if (App.historial.hasNext(App.actualFxml)) {
            loader = App.historial.getNext(App.actualFxml);
            p = loader.getRoot();
            nextScene = p.getScene();
        } else {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("EditarVehiculo2.fxml"));
            App.historial.add(loader, App.actualFxml);
            p = loader.load();
            nextScene = new Scene(p);
        }
        for(String f: fotosSubidas){System.out.println(f);}
        App.actualFxml = loader;
        EditarVehiculo2Controller controller = loader.getController();
        controller.cargarVehiculo(vehiculoEditado, fotosSubidas);
        EditarVehiculoController.actualStage.setScene(nextScene);
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
                    int ind = panelFotos.getChildren().indexOf(selectedItem);
                    fotosSubidas.remove(ind);
                    panelFotos.getChildren().remove(selectedItem);
                    selectedItem = null;
                }
                
            System.out.println("clFotos "+fotosSubidas.size()+" -> "+fotosSubidas.print());
        });
    }
    
    public void cargarInfo(Vehiculo v, Stage st, FXMLLoader loader){
        if(actualLoader==null) {actualLoader = loader;}
        EditarVehiculoController.actualStage=st;
        cerrarVentana();
        vehiculoAnterior = v;
        vehiculoEditado = Vehiculo.clonarVehiculo(v);
        String s = vehiculoEditado.getFotos().get(0);
        
        for(int i=0;i<vehiculoEditado.getFotos().size();i++){
            File f = new File(App.pathFotos+s);
            mostrarImagen(f);
            s = v.getFotos().getNext(s);
            fotosSubidas.add(f.getPath());
        }

        System.out.println("fotosSubidas "+fotosSubidas.size()+" -> "+fotosSubidas.print());
        System.out.println("clFotos "+vehiculoEditado.getFotos().size()+" -> "+vehiculoEditado.getFotos().print());
        
        txtMarca.setText(vehiculoEditado.getMarca());
        txtModelo.setText(vehiculoEditado.getModelo());
        txtAnio.setText(String.valueOf(vehiculoEditado.getAño()));
        txtKilometraje.setText(String.valueOf(vehiculoEditado.getKilometraje()));
        txtPrecio.setText(String.valueOf(vehiculoEditado.getPrecio()));
    }
    
    private void mostrarImagen(File f){
        ImageView imgv = new ImageView();
        imgv.setImage(new Image(f.toURI().toString()));
        imgv.setFitWidth(200);
        imgv.setPreserveRatio(true);
        imgv.setOnMouseClicked(itemResaltado());
        if(fotosSubidas==null) { fotosSubidas = new ArrayListED<>();}
        panelFotos.getChildren().add(imgv);
        
    }
    
    private void cerrarVentana(){
        EditarVehiculoController.actualStage.setOnCloseRequest(e->{
            App.historial.removeLast();
            App.actualFxml = App.historial.getLast();
        });
    }
    
}
