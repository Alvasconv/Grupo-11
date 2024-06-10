package com.grupo_11;

import Modelo.LinkedListED;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static FXMLLoader actualFxml;
    public static String pathArchivos = "src/main/resources/archivos/";
    public static String pathFotos = "src/main/resources/archivos/fotos/";
    public static String pathIconos = "src/main/resources/archivos/iconos/";
    public static Stage stage;
    public static LinkedListED<FXMLLoader> historial = new LinkedListED<>();

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Primary.fxml"));
        actualFxml = fxmlLoader;
        historial.add(fxmlLoader);
        Parent p = fxmlLoader.load();
        scene = new Scene(p, 700, 500);
        App.stage.setScene(scene);
        stage.getIcons().add(App.loadImage(pathIconos+"icono.png"));
        stage.setTitle("Catalogo de Vehiculos");
        App.stage.show();
    }
    
    public static Image loadImage(String pathImage){
        File f = new File(pathImage);
        return new Image(f.toURI().toString());
    }
    
    public static Image loadImage(String pathImage,int x, int y){
        File f = new File(pathImage);
        return new Image(f.toURI().toString(),x,y,false,false);
    }

    public static void main(String[] args) {
        launch();
    }

}