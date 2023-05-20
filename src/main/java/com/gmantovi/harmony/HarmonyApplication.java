package com.gmantovi.harmony;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class HarmonyApplication extends Application{
    private Stage stage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage PrimaryStage) throws Exception {
        stage = PrimaryStage;
        BorderPane b = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-page-overview.fxml")));
        AnchorPane a = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        b.setCenter(a);
        Scene scene = new Scene(b);
        stage.setTitle("Harmony");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/harmony_icon.png"))));
        stage.setScene(scene);
        stage.show();
    }
}