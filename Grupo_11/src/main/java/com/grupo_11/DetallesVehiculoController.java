/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.grupo_11;

import Modelo.ArrayListED;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCambiarIzq;
    @FXML
    private Button btnCambiarDer;
    @FXML
    private ImageView ftEstelar;
    @FXML
    private ImageView ftAnterior;
    @FXML
    private ImageView ftActual;
    @FXML
    private ImageView ftSiguiente;
    @FXML
    private VBox panelInfo;
    @FXML
    private Label titulo;
    @FXML
    private Label precio;
    @FXML
    private Label marca;
    @FXML
    private Label modelo;
    @FXML
    private Label anio;
    @FXML
    private Label ubicacion;
    @FXML
    private Label kilometraje;
    @FXML
    private Label motor;
    @FXML
    private Label transmision;
    @FXML
    private Label peso;
    @FXML
    private Button favorito;
    @FXML
    ImageView iconoFavorito;
    private boolean esFavorito = false;
    private Vehiculo vehiculo;

    /**
     * Initializes the controller class.
     *
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

    public void cargarVehiculo(Vehiculo v) {
        vehiculo = v;
        System.out.println(v.getFotos().size());
        cargarFotos();
        titulo.setText(vehiculo.getMarca() + " " + vehiculo.getModelo() + " - " + vehiculo.getPrecio());
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
        if (isFavorito(vehiculo)) {
            iconoFavorito.setImage(new Image(getClass().getResourceAsStream("/archivos/iconos/favoritoTRUE.png")));
        } else {
            iconoFavorito.setImage(new Image(getClass().getResourceAsStream("/archivos/iconos/fav.png")));
        }
    }

    private void cambiarFotoSig() {
        btnCambiarDer.setOnAction(e -> {
            String[] urlActual1 = ftActual.getImage().getUrl().split("/");
            String actual1 = urlActual1[urlActual1.length - 1];
            setImage(ftEstelar, vehiculo.getFotos().getNext(actual1), 300);
            setImage(ftActual, vehiculo.getFotos().getNext(actual1), 80);
            setImage(ftAnterior, actual1, 80);

            String[] urlActual2 = ftActual.getImage().getUrl().split("/");
            String actual2 = urlActual2[urlActual2.length - 1];
            setImage(ftSiguiente, vehiculo.getFotos().getNext(actual2), 80);
        });
    }

    private void cambiarFotoAnt() {
        btnCambiarIzq.setOnAction(e -> {
            String[] urlActual1 = ftActual.getImage().getUrl().split("/");
            String actual1 = urlActual1[urlActual1.length - 1];
            setImage(ftEstelar, vehiculo.getFotos().getBack(actual1), 300);
            setImage(ftActual, vehiculo.getFotos().getBack(actual1), 80);
            setImage(ftSiguiente, actual1, 80);

            String[] urlActual2 = ftActual.getImage().getUrl().split("/");
            String actual2 = urlActual2[urlActual2.length - 1];
            setImage(ftAnterior, vehiculo.getFotos().getBack(actual2), 80);
        });
    }

    private void cargarListaReparaciones() {
        if (!panelInfo.getChildren().isEmpty()) {
            panelInfo.getChildren().clear();
        }
        for (String s : vehiculo.getReparaciones()) {
            Label valor = new Label(s);
            valor.getStyleClass().add("texto");
            HBox hb = new HBox();
            ImageView imv = new ImageView(App.loadImage(App.pathIconos + "viñeta.png"));
            imv.setFitWidth(18);
            imv.setPreserveRatio(true);
            hb.getChildren().add(imv);
            hb.getChildren().add(valor);
            panelInfo.getChildren().add(hb);
        }
    }

    private void cargarFotos() {
        String actual = vehiculo.getFotos().get(0);
        setImage(ftEstelar, actual, 300);
        setImage(ftActual, actual, 300);
        setImage(ftSiguiente, vehiculo.getFotos().getNext(actual), 80);
        setImage(ftAnterior, vehiculo.getFotos().getBack(actual), 80);
    }

    private void setImage(ImageView imv, String foto, int ancho) {
        imv.setImage(App.loadImage(App.pathFotos + foto));
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
                        if (!datos[1].trim().equals(vehiculo.getMarca())
                                || !datos[2].trim().equals(vehiculo.getModelo())
                                || !datos[3].trim().equals(String.valueOf(vehiculo.getAño()))) {
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

    @FXML
    private void marcarFavorito(ActionEvent event) {
        if (isFavorito(vehiculo)) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("Este vehículo ya se encuentra como favorito, ¿desea deshacer la acción?");

            ButtonType buttonTypeYes = new ButtonType("Sí");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                removeFavorito(vehiculo);
                iconoFavorito.setImage(new Image(getClass().getResourceAsStream("/archivos/iconos/fav.png")));
                mostrarMensaje("Se ha deshecho la acción de marcar como favorito.");
            }
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText(null);
            alert.setContentText("¿Está seguro de añadir este vehículo a favoritos?");

            ButtonType buttonTypeYes = new ButtonType("Sí");
            ButtonType buttonTypeNo = new ButtonType("No");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeYes) {
                guardarFavorito(vehiculo);
                iconoFavorito.setImage(new Image(getClass().getResourceAsStream("/archivos/iconos/favoritoTRUE.png")));
                mostrarMensaje("Se ha agregado a favoritos correctamente.");
            }
        }
    }

    private boolean isFavorito(Vehiculo vehiculo) {
        String filePath = "src/main/resources/archivos/favoritos.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(vehiculo.getMarca()) && line.contains(vehiculo.getModelo())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void guardarFavorito(Vehiculo vehiculo) {
        String filePath = "src/main/resources/archivos/favoritos.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String reparaciones = String.join(";", vehiculo.getReparaciones().toStringArray());
            String fotos = String.join(";", vehiculo.getFotos().toStringArray());

            writer.write(vehiculo.getPrecio() + "," + vehiculo.getMarca() + "," + vehiculo.getModelo() + "," + vehiculo.getAño() + ","
                    + vehiculo.getKilometraje() + "," + vehiculo.getMotor() + "," + vehiculo.getTransmision() + "," + vehiculo.getPeso() + ","
                    + vehiculo.getUbicacion() + "," + reparaciones + "," + fotos + "\n");
        } catch (IOException e) {
            mostrarMensaje("Error al guardar en favoritos: " + e.getMessage());
        }
    }

    private void removeFavorito(Vehiculo vehiculo) {
        String filePath = "src/main/resources/archivos/favoritos.txt";
        File inputFile = new File(filePath);
        File tempFile = new File("src/main/resources/archivos/favoritos_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile)); BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(vehiculo.getMarca()) && line.contains(vehiculo.getModelo())) {
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }

            if (!inputFile.delete()) {
                mostrarMensaje("Error al eliminar el archivo original.");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                mostrarMensaje("Error al renombrar el archivo temporal.");
            }

        } catch (IOException e) {
            mostrarMensaje("Error al deshacer favorito: " + e.getMessage());
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void editarVehiculo() {
        btnEditar.setOnMouseClicked((MouseEvent e) -> {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditarVehiculo.fxml"));
                Parent p = loader.load();
                Stage newStage = new Stage();
                newStage.getIcons().add(App.loadImage(App.pathIconos + "icono.png"));
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
