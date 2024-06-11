package com.grupo_11;

import Metodos.FiltroVehiculos;
import Metodos.OrdenarKilometraje;
import Metodos.OrdenarPrecio;
import Modelo.ArrayListED;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PrimaryController implements Initializable {

    @FXML
    private VBox lstvehiculos;
    @FXML
    private Button botonAñadir;
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
    @FXML
    private ComboBox<String> ordenar;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            mostrarVehiculos(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
        } catch (Exception e) {
            System.out.println(e);
        }
        rellenarComboBoxs(Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos));
        modelo.setDisable(true);
        precioMin.setDisable(true);
        precioMax.setDisable(true);
        kilometrajeMin.setDisable(true);
        kilometrajeMax.setDisable(true);
        marca.setOnAction(event -> onMarcaSelected());
        modelo.setOnAction(event -> onModeloSelected());
        
        inicializarComboBox();
        ordenar.setOnAction(event -> ordenarVehiculos());  // Manejar la selección de ordenación

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

        try {
            if (!precioMin.getText().isEmpty() && !precioMax.getText().isEmpty()) {
                double precioMinVal = Double.parseDouble(precioMin.getText());
                double precioMaxVal = Double.parseDouble(precioMax.getText());
                lstFiltrada = FiltroVehiculos.filtrarPorRangoDePrecio(lstFiltrada, precioMinVal, precioMaxVal);
            }
        } catch (NumberFormatException e) {
            mostrarError("Por favor, ingresa valores numéricos válidos para el rango de precios.");
            return;
        }

        try {
            if (!kilometrajeMin.getText().isEmpty() && !kilometrajeMax.getText().isEmpty()) {
                int kilometrajeMinVal = Integer.parseInt(kilometrajeMin.getText());
                int kilometrajeMaxVal = Integer.parseInt(kilometrajeMax.getText());
                lstFiltrada = FiltroVehiculos.filtrarPorRangoDeKilometraje(lstFiltrada, kilometrajeMinVal, kilometrajeMaxVal);
            }
        } catch (NumberFormatException e) {
            mostrarError("Por favor, ingresa valores numéricos válidos para el rango de kilometraje.");
            return;
        }

        mostrarVehiculos(lstFiltrada);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de formato");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int numColumns = 2;
        int row = 0;
        int col = 0;

        for (Vehiculo v : lst) {
            HBox hboxVehiculo = new HBox();
            hboxVehiculo.setSpacing(10);

            String imagePath = Vehiculo.carpetaFotos + v.getFotos().first.getContent();
            File imageFile = new File(imagePath);

            ImageView imageView = new ImageView(new Image(imageFile.toURI().toString()));
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            VBox vboxDatos = new VBox();
            vboxDatos.setSpacing(5);

            vboxDatos.getChildren().addAll(
                    new Label(v.getMarca()),
                    new Label(v.getKilometraje() + " km - " + v.getAño()),
                    new Label(v.getUbicacion())
            );

            hboxVehiculo.getChildren().addAll(imageView, vboxDatos);
            
            //Asigna la accion de cambiar a la ventana Detalles
            hboxVehiculo.setOnMouseClicked((MouseEvent e)-> {
                try{
                    pasarInfoVehiculo(v);
                }catch(IOException ex){ 
                    System.out.println("NO se cargo la pagina detalles");
                    ex.printStackTrace();
                }
            });
            gridPane.add(hboxVehiculo, col, row);
            col++;
            if (col >= numColumns) {
                col = 0;
                row++;
            }
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        lstvehiculos.getChildren().clear();
        lstvehiculos.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        scrollPane.setFitToWidth(false); 
        lstvehiculos.getChildren().clear(); 
        lstvehiculos.getChildren().add(scrollPane);
        actualizarDatos();

    }

    private void actualizarDatos() {
        for (Vehiculo v : Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos)) {
            marca.getItems().add(v.getMarca());
            modelo.getItems().add(v.getModelo());

        }
    }


    private void pasarInfoVehiculo(Vehiculo v) throws IOException{
        FXMLLoader loader;
        Parent p;
        Scene nextScene;
        if(App.historial.hasNext(App.actualFxml)){
            loader = App.historial.getNext(App.actualFxml);
            p = loader.getRoot();
            nextScene = p.getScene();
        }else{
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
    
    private void inicializarComboBox() {
    ObservableList<String> opciones = FXCollections.observableArrayList( "Menor Precio","Mayor Precio", "Menor recorrido", "Mayor recorrido");
    ordenar.setItems(opciones);
}

 private void ordenarVehiculos() {
    String criterioSeleccionado = ordenar.getValue();
    ArrayListED<Vehiculo> vehiculos = Vehiculo.leerListaVehiculos(Vehiculo.archivoVehiculos);

    Comparator<Vehiculo> comparador = null;

    switch (criterioSeleccionado) {
        case "Menor Precio":
            comparador = new OrdenarPrecio();
            break;
        case "Mayor Precio":
            comparador = new OrdenarPrecio().reversed();
            break;
        case "Menor recorrido":
            comparador = new OrdenarKilometraje();
            break;
        case "Mayor recorrido":
            comparador = new OrdenarKilometraje().reversed();
            break;
    }

    if (comparador != null) {
        vehiculos.sort(comparador);
    }
    mostrarVehiculos(vehiculos);
}

}
