/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;
/**
 * Launcher Class for the application
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class HarmonyApplication extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane b = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("menu.fxml")));
        AnchorPane a = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        b.setCenter(a);
        Scene scene = new Scene(b);
        stage.setTitle("Harmony");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/harmony_icon.png"))));
        stage.setScene(scene);
        stage.show();
    }
}