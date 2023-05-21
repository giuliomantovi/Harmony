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
import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.artist.Alias;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.gmantovi.harmony.gsonClasses.snippet.Snippet;
import com.gmantovi.harmony.gsonClasses.track.MusicGenreList;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;

import java.sql.*;
import java.util.List;
/**
 * Controller for the search/second section of the menu
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class SearchController {
    @FXML private ListView<String> listView;
    @FXML private TextField searchField;
    @FXML private TableView<Element> tableView;
    @FXML private TableColumn<Element, String> nameColumn;
    @FXML private TableColumn<Element, String> typeColumn;
    @FXML private ComboBox<String> infoBox;

    @FXML
    public void initialize(){
        searchField.setTooltip(new Tooltip("If you are looking for a track, insert both track name and artist name \n separated by '-'. Example: 'Billie Jean-Michael Jackson' "));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showOptions(newValue));
    }

    /**
     * Populates the features combobox, differentiating track and artist features
     * @param element Element instance of the search tableview selected, could be an artist or a track
     */
    public void showOptions(Element element){
        if(element==null) {
            return;
        }
        if(element.getType().equals("track")){
            infoBox.setItems(FXCollections.observableArrayList(
                    "General track info","Get lyrics","Get snippet","Get album songs","Add to playlist"));
        }else{
            infoBox.setItems(FXCollections.observableArrayList(
                    "General artist info","Get discography","Get related artists"));
        }
        infoBox.getSelectionModel().selectFirst();
    }

    /**
     * Defines the functions each feature of the combobox is associated with
     */
    @FXML
    public void onInfoBoxChanged(){
        if(infoBox.getSelectionModel().getSelectedItem()!=null) {
            try {
                listView.setStyle("-fx-border-width: 1px");
                MusixMatchAPI musixMatchAPI = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
                ObservableList<String> info = null;
                Integer ID = tableView.getSelectionModel().getSelectedItem().getId();
                String song = tableView.getSelectionModel().getSelectedItem().getName();
                String singer = tableView.getSelectionModel().getSelectedItem().getAuthorName();
                switch (infoBox.getSelectionModel().getSelectedItem()) {
                    //displays in the listview the general information about a track
                    case "General track info" -> {
                        Track track = musixMatchAPI.getTrack(ID);
                        String genres = "Primary genres: ";
                        List<MusicGenreList> genresList = track.getTrack().getPrimaryGenres().getMusicGenreList();
                        //concatenates the list of genres in a single string
                        if(!genresList.isEmpty()){
                            for(MusicGenreList mg : genresList){
                                genres = genres.concat(mg.getMusicGenre().getMusicGenreName() + ", ");
                            }
                        }else{
                            genres = genres.concat("undefined  ");
                        }
                        genres = genres.substring(0, genres.length() - 2);

                        info = FXCollections.observableArrayList((List.of(
                                "Name: " + track.getTrack().getTrackName(),
                                "Singer: " + track.getTrack().getArtistName(),
                                "Album: " + track.getTrack().getAlbumName(),
                                genres,
                                "Popularity rating (1-100): " + track.getTrack().getTrackRating(),
                                "Song link: " + track.getTrack().getTrackShareUrl()))
                        );
                    }
                    //displays in the listview the general information about an artist
                    case "General artist info" -> {
                        Artist artist = musixMatchAPI.getArtist(ID);
                        String alias = "Alias: ";
                        //concatenates list of aliases
                        if (!artist.getArtist().getAliasList().isEmpty()) {
                            for (Alias a : artist.getArtist().getAliasList()) {
                                alias = alias.concat(a.getAlias() + ", ");
                            }
                            alias = alias.substring(0, alias.length() - 2);
                        } else {
                            alias = alias.concat("no alias");
                        }
                        String country = "Country: ";
                        if (artist.getArtist().getArtistCountry().isEmpty()) {
                            country = country.concat("undefined");
                        } else {
                            country = country.concat(artist.getArtist().getArtistCountry());
                        }
                        info = FXCollections.observableArrayList((List.of(
                                "Name: " + artist.getArtist().getArtistName(),
                                alias,
                                country,
                                "Popularity rating (1-100): " + artist.getArtist().getArtistRating()))
                        );
                    }
                    //Displays the lyrics of a track in the listview
                    case "Get lyrics" -> {
                        Lyrics lyrics = musixMatchAPI.getLyrics(ID);
                        info = FXCollections.observableArrayList((List.of(
                                "LYRICS:",
                                lyrics.getLyricsBody())));
                    }
                    //Displays the snippet of a track in the listview
                    case "Get snippet" -> {
                        Snippet snippet = musixMatchAPI.getSnippet(ID);
                        info = FXCollections.observableArrayList((List.of(
                                "Language: " + snippet.getSnippetLanguage(),
                                "Snippet: \n" + snippet.getSnippetBody()))
                        );
                    }
                    //Displays in the listview at most 50 songs that are in the same album of the song selected
                    case "Get album songs" -> {
                        List<Track> tracks = musixMatchAPI.getAlbumTracks(musixMatchAPI.getTrack(ID).getTrack().getAlbumId(), 50);
                        String tracksList = "SONGS LIST: \n";
                        for (Track t : tracks) {
                            tracksList = tracksList.concat(t.getTrack().getTrackName() + ", \n");
                        }
                        //removes the last comma of the concatenation
                        tracksList = tracksList.substring(0, tracksList.length() - 3);
                        info = FXCollections.observableArrayList((List.of(
                                "ALBUM NAME: " + tracks.get(0).getTrack().getAlbumName(),
                                tracksList))
                        );
                    }
                    //Displays in the listview the last 200 albums of the artist selected
                    case "Get discography" -> {
                        List<Album> albums = musixMatchAPI.getArtistAlbums(ID, 200);
                        String albumsList = "ALBUMS LIST: \n";
                        for (Album a : albums) {
                            albumsList = albumsList.concat(a.getAlbum().getAlbumName() + ", \n");
                        }
                        //removes the last comma of the concatenation
                        albumsList = albumsList.substring(0, albumsList.length() - 3);
                        info = FXCollections.observableArrayList((List.of(
                                albumsList))
                        );
                    }
                    //Displays in the listview 10 artists related to the selected one
                    case "Get related artists" ->{
                        List<Artist> artists = musixMatchAPI.getArtistsList("",10,"", Methods.ARTIST_RELATED_GET,ID);
                        String relatedArtistsList = "RELATED ARTISTS: \n";
                        for (Artist a : artists) {
                            relatedArtistsList = relatedArtistsList.concat(a.getArtist().getArtistName() + ", \n");
                        }
                        //removes the last comma of the concatenation
                        relatedArtistsList = relatedArtistsList.substring(0, relatedArtistsList.length() - 3);
                        info = FXCollections.observableArrayList((List.of(
                                relatedArtistsList))
                        );
                    }
                    //Adds the song selected to the Database
                    case "Add to playlist" ->{
                        try {
                            Connection connection;
                            Statement statement;
                            connection = DriverManager.getConnection(Constants.MYSQL_CONNECTION_URL);
                            statement = connection.createStatement();
                            //Assuring that the song isn't already present in the playlist checking all the song IDs
                            ResultSet rs = statement.executeQuery("SELECT IDsong FROM playlist");
                            boolean present = false;
                            while (rs.next()) {
                                int id = rs.getInt("IDsong");
                                if (id == ID) {
                                    present = true;
                                }
                            }
                            if (!present) {
                                PreparedStatement insertPlaylist = connection.prepareStatement("INSERT INTO playlist (IDsong, song, singer) VALUES (?, ?, ?)");
                                insertPlaylist.setInt(1, ID);
                                insertPlaylist.setString(2, song);
                                insertPlaylist.setString(3, singer);
                                insertPlaylist.executeUpdate();
                                new Alert(Alert.AlertType.INFORMATION, "This song has been successfully added to the playlist").showAndWait();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "This song is already in your playlist").showAndWait();
                            }
                        }catch(SQLException e){
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Couldn't add song to the playlist").showAndWait();
                        }finally {
                            //after adding the song updates the combobox value to the general info feature
                            infoBox.getSelectionModel().selectFirst();
                        }
                    }
                    default -> {
                    }
                }
                if(info!=null) {
                    listView.setItems(info);
                    listView.setEditable(true);
                    listView.setCellFactory(TextFieldListCell.forListView());
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }

    }

    /**
     * Searches for a track or an artist (API call), given the textfield string.
     * A track research string must contain '-' as the separator between track name and artist name
     * Puts results in the tableview
     */
    @FXML
    public void onSearchButtonClicked(){
        ObservableList<Element> elements = FXCollections.observableArrayList();
        infoBox.setItems(null);
        try {
            MusixMatchAPI musixMatchAPI = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            if(searchField.getText().contains("-")){
                //splits track name and artist name and looks for matches
                String[] params = searchField.getText().split("-");
                if((!params[0].isEmpty())&&(!params[1].isEmpty())){
                    Track track = musixMatchAPI.getMatchingTrack(params[0],params[1]);
                    if(track !=null){
                        elements.add(new Element(track.getTrack().getTrackId(), track.getTrack().getTrackName(),"track", track.getTrack().getArtistName()));
                    }
                }
            }else{
                //displays at most 10 artists that matches the research
                List<Artist> artists = musixMatchAPI.searchArtists(searchField.getText(),10);
                if(!artists.isEmpty()){
                    elements.add(new Element(artists.get(0).getArtist().getArtistId(),artists.get(0).getArtist().getArtistName(),"artist"));
                    for(Artist a : artists){
                        //check to insert the same artist name only once because the api call may return the same artist many times for different albums or collaborations.
                        if(!a.getArtist().getArtistName().equals(artists.get(0).getArtist().getArtistName())){
                            elements.add(new Element(a.getArtist().getArtistId(),a.getArtist().getArtistName(),"artist"));
                        }
                    }
                }
            }
            if((!elements.isEmpty())&&(!searchField.getText().equals(""))){
                tableView.setStyle("-fx-border-color: white");
                tableView.setItems(elements);
            }else{
                tableView.setItems(null);
                listView.setItems(null);
                infoBox.setItems(null);
                listView.setStyle("-fx-background-color:  #242325");
                new Alert(Alert.AlertType.WARNING, "Item not found").showAndWait();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING, "Item not found").showAndWait();
        }
    }
}
