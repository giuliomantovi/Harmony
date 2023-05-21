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
import com.gmantovi.harmony.config.Methods;
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
    @FXML private Label labelTitle;
    @FXML private ComboBox<String> countryBox;

    @FXML
    public void initialize(){
        countryBox.setItems(FXCollections.observableArrayList(
                "IT","GB","FR","US","DE"));
        countryBox.getSelectionModel().selectFirst();
    }

    /**
     * Updates the top artists/tracks listview when the country combobox is updated
     */
    @FXML
    public void onCountryUpdate(){
        if (labelTitle.getText().equals("Top 10 artists in")) {
            showTopArtists();
        }
        if(labelTitle.getText().equals("Top 10 songs in")){
            showTopSongs();
        }
    }

    /**
     * Makes the top artists/tracks listview editable
     */
    private void makeEditable() {
        if(!listView.getItems().isEmpty()) {
            listView.setEditable(true);
            listView.setCellFactory(TextFieldListCell.forListView());
        }
    }

    /**
     * Retrieves top 10 artists of the selected country from the API and displays them in the listview
     */
    @FXML
    public void showTopArtists(){
        labelTitle.setText("Top 10 artists in");
        listView.setStyle("-fx-border-width: 1px");
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            //use selected country value of the combobox to make the API call
            List<Artist> l = m.getArtistsList(countryBox.getSelectionModel().getSelectedItem(),10,"top", Methods.CHART_ARTISTS_GET,-1);
            //list of artists' names
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

    /**
     * Retrieves top 10 songs of the selected country from the API and displays them in the listview
     */
    @FXML
    public void showTopSongs(){
        labelTitle.setText("Top 10 songs in");
        listView.setStyle("-fx-border-width: 2px");
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            //use selected country value of the combobox to make the API call
            List<Track> l = m.getTracksChart(countryBox.getSelectionModel().getSelectedItem(),10,"top");
            //list of tracks names concatenated with the singers names
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
