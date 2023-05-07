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
    @FXML private AnchorPane homeAnchorPane2;


    @FXML
    public void initialize() throws IOException {
        //borderPane.setCenter(homeAnchorPane2);
        //borderPane.centerProperty().set(homeAnchorPane2);
        
    }

    @FXML
    public void switchToHome(ActionEvent event) throws IOException {
        System.out.println("EVENTO ATTIVATO");
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home2.fxml")));
        borderPane.setCenter(root);
    }

    public void switchToSearch(ActionEvent event){
        borderPane.setCenter(homeAnchorPane2);
    }

}
