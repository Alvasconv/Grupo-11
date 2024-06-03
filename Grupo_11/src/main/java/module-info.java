module com.grupo_11 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.grupo_11 to javafx.fxml;
    exports com.grupo_11;
}
