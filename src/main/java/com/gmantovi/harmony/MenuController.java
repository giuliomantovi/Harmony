/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony;

import com.gmantovi.harmony.controllers.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.Objects;
/**
 * Controller for displaying menu and related sections
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class MenuController implements Controller {
    @FXML private BorderPane borderPane;

    @FXML
    public void initialize(){
    }

    /**
     * Updates the borderPane center when the Home menu button is clicked
     * @throws IOException if resources fails to load
     */
    @FXML
    public void switchToHome() throws IOException {
        Parent center = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
        borderPane.setCenter(center);
    }

    /**
     * Updates the borderPane center when the search menu button is clicked
     * @throws IOException if resources fails to load
     */
    @FXML
    public void switchToSearch() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("search.fxml")));
        borderPane.setCenter(root);
    }

    /**
     * Updates the borderPane center when the playlist menu button is clicked
     * @throws IOException if resources fails to load
     */
    @FXML
    public void switchToPlaylist() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playlist.fxml")));
        borderPane.setCenter(root);
    }
}
