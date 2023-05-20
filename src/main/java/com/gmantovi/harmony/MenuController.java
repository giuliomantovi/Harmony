/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.Objects;
/**
 * Controller for displaying menu and related sections
 */
public class MenuController {
    @FXML private BorderPane borderPane;

    @FXML
    public void initialize(){
    }

    @FXML
    public void switchToHome() throws IOException {
        Parent center = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        borderPane.setCenter(center);
    }

    @FXML
    public void switchToSearch() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("search.fxml")));
        borderPane.setCenter(root);
    }

    @FXML
    public void switchToPlaylist() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playlist.fxml")));
        borderPane.setCenter(root);
    }


}
