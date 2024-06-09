package com.grupo_11;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SecondaryController {
    
    @FXML
    private Button regresar;

    @FXML
    private void turnBack() throws IOException {
        App.setRoot("primary");
    }
}