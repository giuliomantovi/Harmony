/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony;

import com.gmantovi.harmony.config.Constants;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;

import java.util.List;
/**
 * Controller for the home/first section of the menu
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class HomeController {
    @FXML private ListView<String> listView;
    @FXML private Label listTitle;
    @FXML private ComboBox<String> countryBox;

    @FXML
    public void initialize(){
        countryBox.setItems(FXCollections.observableArrayList(
                "IT","GB","FR","US","DE"));
        countryBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onCountryUpdate(){
        if (listTitle.getText().equals("Top 10 artists in")) {
            showTopArtists();
        }
        if(listTitle.getText().equals("Top 10 songs in")){
            showTopSongs();
        }
    }

    private void makeEditable() {
        if(!listView.getItems().isEmpty()) {
            listView.setEditable(true);
            listView.setCellFactory(TextFieldListCell.forListView());
        }
    }

    @FXML
    public void showTopArtists(){
        listTitle.setText("Top 10 artists in");
        listView.setStyle("-fx-border-width: 1px");
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            List<Artist> l = m.getArtistsList(countryBox.getSelectionModel().getSelectedItem(),10,"top","get_artists_chart",-1);
            List<String> artists = l.stream()
                    .map(artist -> artist.getArtist().getArtistName())
                    .toList();
            listView.setItems(FXCollections.observableArrayList(artists));
            makeEditable();
        } catch (Exception e){
            listView.setStyle("-fx-background-color: #242325");
            new Alert(Alert.AlertType.ERROR, "Couldn't establish connection").showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    public void showTopSongs(){
        listTitle.setText("Top 10 songs in");
        listView.setStyle("-fx-border-width: 2px");
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            List<Track> l = m.getTracksChart(countryBox.getSelectionModel().getSelectedItem(),10,"top");
            List<String> tracks = l.stream()
                    .map(t -> t.getTrack().getTrackName() + " - " + t.getTrack().getArtistName())
                    .toList();
            listView.setItems(FXCollections.observableArrayList(tracks));
            makeEditable();
        } catch (Exception e){
            listView.setStyle("-fx-background-color: #242325");
            new Alert(Alert.AlertType.ERROR, "Couldn't establish connection").showAndWait();
            e.printStackTrace();
        }
    }
}
