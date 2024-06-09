package com.grupo_11;

import Metodos.FiltroVehiculos;
import Modelo.ArrayListED;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {

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

    private Vehiculo vSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mostrarVehiculos(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
        } catch (Exception e) {
            System.out.println("Error catastrófico");
        }
        rellenarComboBoxs(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
        modelo.setDisable(true);
        precioMin.setDisable(true);
        precioMax.setDisable(true);
        kilometrajeMin.setDisable(true);
        kilometrajeMax.setDisable(true);
        marca.setOnAction(event -> onMarcaSelected());
        modelo.setOnAction(event -> onModeloSelected());
    }

    @FXML
    private void añadirVehiculo(ActionEvent event) throws IOException {
        FXMLLoader loader;
        Parent p;
        Scene nextScene;
        if (App.historial.hasNext(App.actualFxml)) {
            loader = App.historial.getNext(App.actualFxml);
            p = loader.getRoot();
            nextScene = p.getScene();
        } else {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AddVehiculo.fxml"));
            App.historial.add(loader, App.actualFxml);
            p = loader.load();
            nextScene = new Scene(p);
        }
        App.actualFxml = loader;
        App.stage.setScene(nextScene);
    }

    @FXML
    private void editarVehiculo(ActionEvent event) {
    }

    @FXML
    private void RemoverVehiculo(ActionEvent event) {
    }

    private void onMarcaSelected() {
        if (marca.getValue() != null) {
            modelo.setDisable(false);
            llenarModelos(marca.getValue());
        }
    }

    private void onModeloSelected() {
        if (modelo.getValue() != null) {
            precioMin.setDisable(false);
            precioMax.setDisable(false);
            kilometrajeMin.setDisable(false);
            kilometrajeMax.setDisable(false);
        }
    }

    @FXML
    private void filtrarVehiculos(ActionEvent event) {
        ArrayListED<Vehiculo> lst = Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos);
        ArrayListED<Vehiculo> lstFiltrada = FiltroVehiculos.filtrarPorMarcaYModelo(lst, marca.getValue(), modelo.getValue());

        if (!precioMin.getText().isEmpty() && !precioMax.getText().isEmpty()) {
            lstFiltrada = FiltroVehiculos.filtrarPorRangoDePrecio(lstFiltrada,
                    Double.parseDouble(precioMin.getText()),
                    Double.parseDouble(precioMax.getText()));
        }

        if (!kilometrajeMin.getText().isEmpty() && !kilometrajeMax.getText().isEmpty()) {
            lstFiltrada = FiltroVehiculos.filtrarPorRangoDeKilometraje(lstFiltrada,
                    Integer.parseInt(kilometrajeMin.getText()),
                    Integer.parseInt(kilometrajeMax.getText()));
        }

        mostrarVehiculos(lstFiltrada);
    }

    private void rellenarComboBoxs(ArrayListED<Vehiculo> vehiculos) {
        Set<String> marcas = new HashSet<>();
        for (Vehiculo v : vehiculos) {
            marcas.add(v.getMarca());
        }
        marca.setItems(FXCollections.observableArrayList(marcas));
    }

    private void llenarModelos(String marcaSeleccionada) {
        Set<String> modelos = new HashSet<>();
        ArrayListED<Vehiculo> vehiculos = Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos);
        for (Vehiculo v : vehiculos) {
            if (v.getMarca().equals(marcaSeleccionada)) {
                modelos.add(v.getModelo());
            }
        }
        modelo.setItems(FXCollections.observableArrayList(modelos));
    }
//    public void rellenarComboBoxs(ArrayListED<Vehiculo> listVehiculo) {
//        ArrayListED<String> marcas = new ArrayListED<>();
//        Set<String> uniqueMarcas = new HashSet<>();
//
//        for (Vehiculo v : listVehiculo) {
//            uniqueMarcas.add(v.getMarca());
//        }
//
//        marcas.addAll(uniqueMarcas);
//
//        String[] marcasArray = marcas.toArray();
//        ObservableList<String> options1 = FXCollections.observableArrayList(marcasArray);
//        marca.setItems(options1);
//        modelo.setDisable(true);
//        marca.setOnAction(event -> {
//            if (marca.getSelectionModel().getSelectedItem() != null) {
//                modelo.setDisable(false);
//                rellenarModelos(listVehiculo);
//            } else {
//                modelo.setDisable(true);
//            }
//        });
//    }

    public void rellenarModelos(ArrayListED<Vehiculo> listVehiculo) {
        ArrayListED<String> modelos = new ArrayListED<>();
        Set<String> uniqueModelos = new HashSet<>();

        for (Vehiculo v : listVehiculo) {
            uniqueModelos.add(v.getModelo());
        }

        modelos.addAll(uniqueModelos);

        String[] modelosArray = modelos.toArray();
        ObservableList<String> options2 = FXCollections.observableArrayList(modelosArray);
        modelo.setItems(options2);

    }

    public void mostrarVehiculos(ArrayListED<Vehiculo> lst) {
        for (Vehiculo v : lst) {
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
            hboxVehiculo.getChildren().addAll(imageView, vboxDatos);
            lstvehiculos.getChildren().add(hboxVehiculo);
        }

    }

//    @FXML
//    private void filtrarVehiculos(ActionEvent event) {
//        ArrayListED<Vehiculo> lst = Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos);
//        ArrayListED<Vehiculo> lstf1 = FiltroVehiculos.filtrarPorMarcaYModelo(lst, marca.getValue(), modelo.getValue());
//        ArrayListED<Vehiculo> lstf2;
//        lstf2 = FiltroVehiculos.filtrarPorRangoDePrecio(lst, Double.parseDouble(precioMin.getText()), Double.parseDouble(precioMax.getText()));
//        mostrarVehiculos(lstf2);
//
//    }
    private void actualizarDatos() {
        for (Vehiculo v : Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos)) {
            marca.getItems().add(v.getMarca());
            modelo.getItems().add(v.getModelo());

        }
    }

    //ESTO SIRVE PARA PASAR EL VEHICULO SELECCIONADO A LA VENTANA DETALLES VEHICULO
    // REMPLAZA EL CODIGO ACTUAL QUE ESTA EN EL METODO AÑADIRVEHICULO()
//    private void pasarInfoVehiculo() throws IOException{
//        FXMLLoader loader;
//        Parent p;
//        Scene nextScene;
//        if(App.historial.hasNext(App.actualFxml)){
//            loader = App.historial.getNext(App.actualFxml);
//            p = loader.getRoot();
//            nextScene = p.getScene();
//        }else{
//            loader = new FXMLLoader();
//            loader.setLocation(getClass().getResource("DetallesVehiculo.fxml"));
//            App.historial.add(loader, App.actualFxml);
//            p = loader.load();
//            nextScene = new Scene(p);
//        }
//        App.actualFxml = loader;
//        DetallesVehiculoController controller = loader.getController();
//        controller.cargarVehiculo(vSeleccionado);
//        App.stage.setScene(nextScene);
//    }
}
