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

    public void showOptions(Element element){
        if(element==null) {
            return;
        }
        if(element.getType().equals("track")){
            infoBox.setItems(FXCollections.observableArrayList(
                    "General track infos","Get lyrics","Get snippet","Get album songs","Add to playlist"));
        }else{
            infoBox.setItems(FXCollections.observableArrayList(
                    "General artist infos","Get discography","Get related artists"));
        }
        infoBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onInfoBoxChanged(){
        if(infoBox.getSelectionModel().getSelectedItem()!=null) {
            try {
                listView.setStyle("-fx-border-width: 1px");
                MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
                ObservableList<String> infos = null;
                Integer ID = tableView.getSelectionModel().getSelectedItem().getId();
                String song = tableView.getSelectionModel().getSelectedItem().getName();
                String singer = tableView.getSelectionModel().getSelectedItem().getAuthorName();
                switch (infoBox.getSelectionModel().getSelectedItem()) {
                    case "General track infos" -> {
                        System.out.println("GET TRACK INFOS");
                        Track track = m.getTrack(ID);
                        String genres = "Primary genres: ";
                        List<MusicGenreList> genreList = track.getTrack().getPrimaryGenres().getMusicGenreList();
                        if(!genreList.isEmpty()){
                            for(MusicGenreList mg : genreList){
                                genres = genres.concat(mg.getMusicGenre().getMusicGenreName() + ", ");
                            }
                        }else{
                            genres = genres.concat("undefined  ");
                        }
                        genres = genres.substring(0, genres.length() - 2);

                        infos = FXCollections.observableArrayList((List.of(
                                "Name: " + track.getTrack().getTrackName(),
                                "Singer: " + track.getTrack().getArtistName(),
                                "Album: " + track.getTrack().getAlbumName(),
                                genres,
                                "Popularity rating (1-100): " + track.getTrack().getTrackRating(),
                                "Song link: " + track.getTrack().getTrackShareUrl()))
                        );
                    }
                    case "General artist infos" -> {
                        System.out.println("GET ARTIST INFOS");
                        Artist artist = m.getArtist(ID);
                        String alias = "Alias: ";
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
                        infos = FXCollections.observableArrayList((List.of(
                                "Name: " + artist.getArtist().getArtistName(),
                                alias,
                                country,
                                "Popularity rating (1-100): " + artist.getArtist().getArtistRating()))
                        );
                    }
                    case "Get lyrics" -> {
                        Lyrics lyrics = m.getLyrics(ID);
                        infos = FXCollections.observableArrayList((List.of(
                                "LYRICS:",
                                lyrics.getLyricsBody())));
                    }
                    case "Get snippet" -> {
                        Snippet snippet = m.getSnippet(ID);
                        infos = FXCollections.observableArrayList((List.of(
                                "Language: " + snippet.getSnippetLanguage(),
                                "Snippet: \n" + snippet.getSnippetBody()))
                        );
                    }
                    case "Get album songs" -> {
                        List<Track> tracks = m.getAlbumTracks(m.getTrack(ID).getTrack().getAlbumId(), 50);
                        String tracksList = "SONGS LIST: \n";
                        for (Track t : tracks) {
                            tracksList = tracksList.concat(t.getTrack().getTrackName() + ", \n");
                        }
                        tracksList = tracksList.substring(0, tracksList.length() - 3);
                        infos = FXCollections.observableArrayList((List.of(
                                "ALBUM NAME: " + tracks.get(0).getTrack().getAlbumName(),
                                tracksList))
                        );
                    }
                    case "Get discography" -> {
                        List<Album> albums = m.getArtistAlbums(ID, 200);
                        String albumsList = "ALBUMS LIST: \n";
                        for (Album a : albums) {
                            albumsList = albumsList.concat(a.getAlbum().getAlbumName() + ", \n");
                        }
                        albumsList = albumsList.substring(0, albumsList.length() - 3);
                        infos = FXCollections.observableArrayList((List.of(
                                albumsList))
                        );
                    }

                    case "Get related artists" ->{
                        List<Artist> artists = m.getArtistsList("",10,"","get_related_artist",ID);
                        String relatedList = "RELATED ARTISTS: \n";
                        for (Artist a : artists) {
                            relatedList = relatedList.concat(a.getArtist().getArtistName() + ", \n");
                        }
                        relatedList = relatedList.substring(0, relatedList.length() - 3);
                        infos = FXCollections.observableArrayList((List.of(
                                relatedList))
                        );
                    }
                    case "Add to playlist" ->{
                        try {
                            Connection connection;
                            Statement statement;
                            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=");
                            statement = connection.createStatement();
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
                                new Alert(Alert.AlertType.CONFIRMATION, "This song has been successfully added to the playlist").showAndWait();
                            } else {
                                new Alert(Alert.AlertType.WARNING, "This song is already in your playlist").showAndWait();
                            }
                        }catch(SQLException e){
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "Couldn't add song to the playlist").showAndWait();
                        }finally {
                            infoBox.getSelectionModel().selectFirst();
                        }
                    }
                    default -> {
                    }
                }
                if(infos!=null) {
                    listView.setItems(infos);
                    listView.setEditable(true);
                    listView.setCellFactory(TextFieldListCell.forListView());
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Searches for a track or an artist (API call), given the textfield string.
     * A track must contain '-' as the separator between track name and artist
     * Puts results in the tableview
     */
    @FXML
    public void onSearchButtonClicked(){
        ObservableList<Element> elements = FXCollections.observableArrayList();
        try {
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            if(searchField.getText().contains("-")){
                String[] params = searchField.getText().split("-");
                if((!params[0].isEmpty())&&(!params[1].isEmpty())){
                    Track l = m.getMatchingTrack(params[0],params[1]);
                    if(l!=null){
                        elements.add(new Element(l.getTrack().getTrackId(),l.getTrack().getTrackName(),"track",l.getTrack().getArtistName()));
                    }
                }
            }else{
                List<Artist> artists = m.searchArtists(searchField.getText(),5);
                if(!artists.isEmpty()){
                    elements.add(new Element(artists.get(0).getArtist().getArtistId(),artists.get(0).getArtist().getArtistName(),"artist"));
                    for(Artist a : artists){
                        if(!a.getArtist().getArtistName().equals(artists.get(0).getArtist().getArtistName())){
                            elements.add(new Element(a.getArtist().getArtistId(),a.getArtist().getArtistName(),"artist"));
                        }
                    }
                }
            }
            if((!elements.isEmpty())&&(!searchField.getText().equals(""))){
                tableView.setStyle("-fx-border-color: white");
                tableView.setItems(elements);
                System.out.println(tableView.getItems().get(0).getId());
            }else{
                tableView.setItems(null);
                new Alert(Alert.AlertType.WARNING, "Item not found").showAndWait();
            }
        } catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING, "Item not found").showAndWait();
        }
    }
}
