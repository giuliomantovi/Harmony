package com.gmantovi.harmony;

import com.gmantovi.harmony.config.Constants;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;

public class HomeController {
    @FXML private AnchorPane homeAnchorPane;
    @FXML private Button topArtists;
    @FXML private Button topSongs;
    @FXML private ListView<String> listView;
    @FXML private Label listTitle;
    @FXML private ComboBox<String> countryBox;

    @FXML
    public void initialize() throws IOException {
        countryBox.setItems(FXCollections.observableArrayList(
                "IT","GB","FR","US","DE"));
        countryBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void showTopArtists() throws IOException {
        listTitle.setText("Top 10 artists in");
        listView.setStyle("-fx-border-width: 1px");
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            List<Artist> l = m.getArtistsList(countryBox.getSelectionModel().getSelectedItem(),10,"top","get_artists_chart",-1);
            List<String> artists = l.stream()
                    .map(artist -> artist.getArtist().getArtistName())
                    .toList();
            listView.setItems(FXCollections.observableArrayList(artists));
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Couldn't establish connection").showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void showTopSongs() throws IOException {
        listTitle.setText("Top 10 songs in");
        listView.setStyle("-fx-border-width: 2px");
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            List<Track> l = m.getTracksChart(countryBox.getSelectionModel().getSelectedItem(),10,"top");
            List<String> tracks = l.stream()
                    .map(t -> t.getTrack().getTrackName() + " - " + t.getTrack().getArtistName())
                    .toList();
            listView.setItems(FXCollections.observableArrayList(tracks));
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Couldn't establish connection").showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void onCountryUpdate() throws IOException {
        if (listTitle.getText().equals("Top 10 artists in")) {
            showTopArtists();
        }
        if(listTitle.getText().equals("Top 10 songs in")){
            showTopSongs();
        }
    }

}
