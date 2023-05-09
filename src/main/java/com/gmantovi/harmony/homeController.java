package com.gmantovi.harmony;

import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class homeController {
    @FXML private AnchorPane homeAnchorPane;
    @FXML private Button topArtists;
    @FXML private Button topSongs;
    @FXML private ListView<String> listView;
    @FXML private Label listTitle;
    @FXML private ComboBox<String> countryBox;



    @FXML
    public void initialize() throws IOException {

    }

    @FXML
    public void showTopArtists(ActionEvent event) throws IOException {
        listTitle.setText("Top 5 artists in");
        countryBox.setItems(FXCollections.observableArrayList(
                "IT","GB","FR","US","DE"));
        countryBox.getSelectionModel().selectFirst();
        try {
            MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
            List<Track> l = m.getTracksChart("it",2,"top");

        }catch (NullPointerException e){
            System.out.println("NULLO");
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    public void showTopSongs() throws IOException {
        listTitle.setText("Top 5 songs in");
        countryBox.setItems(FXCollections.observableArrayList(
                "IT","GB","FR","US","DE"));
        countryBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onCountryUpdate() throws IOException {

    }

}
