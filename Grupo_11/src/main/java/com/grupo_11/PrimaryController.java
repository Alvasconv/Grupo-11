package com.grupo_11;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class PrimaryController {

    @FXML
    private Button botonAñadir;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonRemover;
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
    

}
