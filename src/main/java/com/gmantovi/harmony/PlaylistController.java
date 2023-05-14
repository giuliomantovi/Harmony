package com.gmantovi.harmony;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class PlaylistController {

    @FXML private Button addButton;
    @FXML private TableColumn<?, ?> playlistIDColumn;
    @FXML private TableColumn<?, ?> playlistSingerColumn;
    @FXML private TableColumn<?, ?> playlistSongColumn;
    @FXML private TableView<?> playlistTableView;
    @FXML private Button removeButton;
    @FXML private TableColumn<?, ?> suggestedIDColumn;
    @FXML private TableColumn<?, ?> suggestedSingerColumn;
    @FXML private TableColumn<?, ?> suggestedSongColumn;
    @FXML private TableView<?> suggestedTableView;

    @FXML
    public void initialize() throws IOException {

    }


}
