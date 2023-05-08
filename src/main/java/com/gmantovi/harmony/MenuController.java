package com.gmantovi.harmony;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    @FXML private BorderPane borderPane;
    @FXML private Button homeButton;
    @FXML private Button playListButton;
    @FXML private Button searchButton;
    @FXML private AnchorPane homeAnchorPane;


    @FXML
    public void initialize() throws IOException {
        //borderPane.setCenter(homeAnchorPane2);
        //borderPane.centerProperty().set(homeAnchorPane2);
        //switchToHome(new ActionEvent());

    }

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        System.out.println("HOME");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        borderPane.setCenter(root);

    }

    @FXML
    public void switchToSearch(ActionEvent event) throws IOException {
        System.out.println("SEARCH");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("search.fxml")));
        borderPane.setCenter(root);
    }

    @FXML
    public void switchToPlaylist(ActionEvent event) throws IOException {
        System.out.println("PLAYLIST");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playlist.fxml")));
        borderPane.setCenter(root);
    }

}
