package com.grupo_11;

import Modelo.LinkedListED;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static FXMLLoader actualFxml;
    public static String pathArchivos = "src/main/resources/archivos/";
    public static String pathFotos = "src/main/resources/archivos/fotos/";
    public static Stage stage;
    public static LinkedListED<FXMLLoader> historial = new LinkedListED<>();

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        Parent p = loadFXML("primary");
        scene = new Scene(p, 600, 480);
        App.stage.setScene(scene);
        App.stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    //TRATEN DE NO USAR ESTO, SINO LO OTRO QUE HE USADO EN ADDVEHICULOCONTROLLER
    //PORQUE DE LA OTRA FORMA ESTA HECHO PARA QUE SE GUARDEN EN EL HISTORIAL
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        actualFxml = fxmlLoader;
        historial.add(fxmlLoader);
        System.out.println("Si creo");
        System.out.println("Size--> "+App.historial.size());
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}