package com.gmantovi.harmony;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class HarmonyApplication extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane b = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-page-overview.fxml")));
        AnchorPane a = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        b.setCenter(a);
        //FXMLLoader loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home-page-overview.fxml")));
        //Parent view =  b.load();
        Scene scene = new Scene(b);
        stage.setTitle("Harmony");
        //stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/address_book_512.png"))));
        stage.setScene(scene);
        stage.show();
    }
}
