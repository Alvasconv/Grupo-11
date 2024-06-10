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
import javafx.scene.control.Label;
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

    @FXML
    private Button btnContinuar;
    @FXML
    private Button btnVolver;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtKilometraje;
    @FXML
    private TextField txtPrecio;
    @FXML
    private VBox panelFotos;
    @FXML
    private Label info;
    private Vehiculo vehiculoCreado;
    private ImageView selectedItem;
    private CircularListED<String> clFotos;
    protected ArrayListED<File> filesFotos;

    /**
     * Initializes the controller class.
     *
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

    private void addFotos() {
        btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent x) {
                if (filesFotos == null) {
                    filesFotos = new ArrayListED<>();
                }
                if (clFotos == null) {
                    clFotos = new CircularListED<>();
                }
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

                panelFotos.getChildren().add(imgv);
                clFotos.add(fotoFile.getName());
                filesFotos.add(fotoFile);
            }
        });
    }

    private boolean creacionVehiculo() {
        if (vehiculoCreado == null) {
            vehiculoCreado = new Vehiculo();
        }

        vehiculoCreado.setMarca(txtMarca.getText());
        vehiculoCreado.setModelo(txtModelo.getText());

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
            vehiculoCreado.setMarca(txtMarca.getText());
            vehiculoCreado.setModelo(txtModelo.getText());
            vehiculoCreado.setAño(Integer.parseInt(txtAnio.getText()));
            vehiculoCreado.setKilometraje(Double.parseDouble(txtKilometraje.getText()));
            vehiculoCreado.setPrecio(Double.parseDouble(txtPrecio.getText()));
            vehiculoCreado.setFotos(clFotos);

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
                info.setText("ⓘ No se han completado todos los campos");
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

//    private void continuar() {
//        btnContinuar.setOnAction((ActionEvent e) -> {
//            creacionVehiculo();
//            try {
//                pasarInfoVehiculo();
//            } catch (IOException ex) {
//                ex.getMessage();
//            }
//        });
//    }
    private void volver() {
        btnVolver.setOnAction((ActionEvent e) -> {
            App.historial.removeLast();
            FXMLLoader backloader = App.historial.getLast();
            Parent p = backloader.getRoot();
            Scene s = p.getScene();
            App.actualFxml = backloader;
            App.stage.setTitle("Catalogo de Vehiculos");
            App.stage.setScene(s);
        });
    }

//    private void pasarInfoVehiculo() throws IOException {
//        FXMLLoader loader;
//        Parent p;
//        Scene nextScene;
//        if (App.historial.hasNext(App.actualFxml)) {
//            loader = App.historial.getNext(App.actualFxml);
//            p = loader.getRoot();
//            nextScene = p.getScene();
//        } else {
//            loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("AddVehiculo2.fxml"));
//            App.historial.add(loader, App.actualFxml);
//            p = loader.load();
//            nextScene = new Scene(p);
//        }
//        App.actualFxml = loader;
//        AddVehiculo2Controller controller = loader.getController();
//        controller.cargarVehiculo(vehiculoCreado, filesFotos);
//        App.stage.setScene(nextScene);
//    }
    private void pasarInfoVehiculo() throws IOException {
        FXMLLoader loader;
        Parent p;
        Scene nextScene;

        if (App.historial.hasNext(App.actualFxml)) {
            loader = App.historial.getNext(App.actualFxml);
            p = loader.getRoot();
            if (p == null) {
                System.out.println("Error: El loader.getRoot() devolvió null.");
            }
            nextScene = p != null ? p.getScene() : null;
        } else {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddVehiculo2.fxml"));
            App.historial.add(loader, App.actualFxml);
            try {
                p = loader.load();
            } catch (IOException e) {
                System.out.println("Error cargando el archivo FXML: " + e.getMessage());
                throw e;
            }
            if (p == null) {
                System.out.println("Error: El loader.load() devolvió null.");
            }
            nextScene = new Scene(p);
        }

        if (nextScene == null) {
            System.out.println("Error: nextScene es null.");
            return;
        }

        App.actualFxml = loader;
        AddVehiculo2Controller controller = loader.getController();
        controller.cargarVehiculo(vehiculoCreado, filesFotos);
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

    public void deleteImg() {
        btnDelete.setOnMouseClicked((MouseEvent e) -> {
            if (selectedItem == null) {
                return;
            }
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText(null);
            alert.setContentText("Seguro de borrar esta foto?");
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.initStyle(StageStyle.UTILITY);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int ind = panelFotos.getChildren().indexOf(selectedItem);
                panelFotos.getChildren().remove(selectedItem);
                clFotos.remove(ind);
                filesFotos.remove(ind);
                selectedItem = null;
            }
        });
    }

}
