module com.grupo_11 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.grupo_11 to javafx.fxml;
    exports com.grupo_11;
}
