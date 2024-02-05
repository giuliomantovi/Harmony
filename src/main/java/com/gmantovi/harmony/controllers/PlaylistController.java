/*
 *GNU GENERAL PUBLIC LICENSE
 *Version 3, 29 June 2007
 *
 * Copyright (C) 2007 by Giulio Mantovi
 * Everyone is permitted to copy and distribute verbatim copies
 * of this license document, but changing it is not allowed.
 */
package com.gmantovi.harmony.controllers;

import com.gmantovi.harmony.API.MusicElement;
import com.gmantovi.harmony.API.Proxy;
import com.gmantovi.harmony.config.Constants;
import com.gmantovi.harmony.config.Methods;
import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.track.MusicGenreList;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Controller for the playlist/third section of the menu
 * @author Giulio Mantovi
 * @version 2023.05.21
 */
public class PlaylistController implements Controller {

    @FXML private Button addButton;
    @FXML private TableColumn<MusicElement, String> playlistIDColumn;
    @FXML private TableColumn<MusicElement, String> playlistSingerColumn;
    @FXML private TableColumn<MusicElement, String> playlistSongColumn;
    @FXML private TableView<MusicElement> playlistTableView;
    @FXML private Button removeButton;
    @FXML private TableColumn<MusicElement, String> suggestedIDColumn;
    @FXML private TableColumn<MusicElement, String> suggestedSingerColumn;
    @FXML private TableColumn<MusicElement, String> suggestedSongColumn;
    @FXML private TableView<MusicElement> suggestedTableView;

    @FXML
    public void initialize() {
        //setting up playlist tableView
        playlistIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        playlistSingerColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        playlistSongColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        try{playlistTableView.setItems(getPlaylistData());}catch(SQLException e){e.printStackTrace();} //displays the tracks retrieved in the playlist tableview
        playlistTableView.getSelectionModel().selectedItemProperty().addListener((observable) -> onPlaylistSelected());
        //setting up suggested tableView
        suggestedSongColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        suggestedIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        suggestedSingerColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        executeTask(); //displays the suggested songs in the playlist tableview
        suggestedTableView.getSelectionModel().selectedItemProperty().addListener((observable) -> onSuggestedSelected());
    }

    /**
     * Enables addButton after selecting an item
     */
    private void onSuggestedSelected() {
        addButton.setDisable(false);
    }

    /**
     * Enables removeButton after selecting an item
     */
    private void onPlaylistSelected() {
        removeButton.setDisable(false);
    }

    /**
     * Creates a task for computing suggested songs and putting them in the tableview
     */
    private void executeTask (){
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executor.submit(() -> {
            try {
                suggestedTableView.setItems(getSuggestedData());
                if (suggestedTableView.getItems().isEmpty()) new Alert(Alert.AlertType.ERROR, "Couldn't find suggestions").showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Couldn't find suggestions").showAndWait();
            }
        });
    }

    /**
     * Retrieves all the songs of the playlist from mysql database
     * @throws SQLException if connection to the mysql database fails
     * @return an observable list of Elements containing the playlist tracks
     */
    private ObservableList<MusicElement> getPlaylistData() throws SQLException {
        ObservableList<MusicElement> playlist =FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection(Constants.MYSQL_CONNECTION_URL);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist");
            while(rs.next()) {
                int id = rs.getInt("IDsong");
                String song = rs.getString("song");
                String singer= rs.getString("singer");
                playlist.add(new MusicElement(id,song,"track,",singer));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Couldn't establish connection with databse").showAndWait();
        } finally {
            if (connection != null) {
                assert statement != null;
                statement.close();
                connection.close();
            }
        }
        if(!playlist.isEmpty()){
            playlistTableView.setStyle("-fx-border-width: 1px");
        }
        return playlist;
    }

    /**
     * Returns the index of the selected song in the TableView component
     * @param tableView is the TableView to get the index from
     * @return an integer that defines one MusicElement of the tableview
     */
    int selectedIndex(TableView<MusicElement> tableView) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            showNoSongSelectedAlert();
            throw new NoSuchElementException();

        }
        return selectedIndex;
    }

    /**
     * Shows a simple warning dialog
     */
    void showNoSongSelectedAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("No Selection");
        alert.setHeaderText("No Song Selected");
        alert.setContentText("Please select a song in the table.");
        alert.showAndWait();
    }

    /**
     * removes playlist TableView selected song from the database and the tableview itself
     */
    @FXML
    private void handleRemoveSong() {
        try {
            Connection connection;
            int selectedIndex = selectedIndex(playlistTableView);
            connection = DriverManager.getConnection(Constants.MYSQL_CONNECTION_URL);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM playlist WHERE IDsong=?");
            preparedStatement.setInt(1, playlistTableView.getItems().get(selectedIndex).getId());
            preparedStatement.executeUpdate();
            playlistTableView.getItems().remove(selectedIndex);
        } catch (NoSuchElementException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * searches for track suggestions by finding attributes in common with songs in the playlist
     * @return an Observable List of MusicElement containing the suggested tracks
     * @throws SQLException if connection to mysql database fails
     */
    private ObservableList<MusicElement> getSuggestedData() throws SQLException {

        if (playlistTableView.getItems().isEmpty()) return null;
        //final list to return
        ObservableList<MusicElement> suggested =FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        try{
            Proxy proxy = new Proxy(Constants.PERSONAL_API_KEY);
            connection = DriverManager.getConnection(Constants.MYSQL_CONNECTION_URL);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT IDsong FROM playlist");
            //maps containing Name (genres, countries) or ID (singer) as the key and the number of times they
            // appear in the playlist as the value
            HashMap<String,Integer> genresOccurrences = new HashMap<>();
            HashMap<Integer,Integer> singersOccurrences = new HashMap<>();
            HashMap<String,Integer> countriesOccurrences = new HashMap<>();

            while(rs.next()) {
                int id = rs.getInt("IDsong");
                Track track = proxy.getTrack(id);
                //counting genres occurences of the playlist
                List<MusicGenreList> genre = track.getTrack().getPrimaryGenres().getMusicGenreList();
                if(!genre.isEmpty()){
                    String genreName = genre.get(0).getMusicGenre().getMusicGenreName();
                    if(genresOccurrences.containsKey(genreName)){
                        genresOccurrences.put(genreName, genresOccurrences.get(genreName) + 1);
                    }else{
                        genresOccurrences.put(genreName,1);
                    }
                }
                //counting singers occurences of the playlist
                Integer singer = track.getTrack().getArtistId();
                if(singer != null){
                    if(singersOccurrences.containsKey(singer)){
                        singersOccurrences.put(singer, singersOccurrences.get(singer) + 1);
                    }else{
                        singersOccurrences.put(singer,1);
                    }
                }
                //counting countries occurences of the playlist
                String country = proxy.getArtist(track.getTrack().getArtistId()).getArtist().getArtistCountry();
                if(country != null && !country.equals("")){
                    if(countriesOccurrences.containsKey(country)){
                        countriesOccurrences.put(country, countriesOccurrences.get(country) + 1);
                    }else{
                        countriesOccurrences.put(country,1);
                    }
                }
            }
            //getting most frequent artist in the user playlist
            Integer topArtist;
            String topGenre;
            String topCountry;
            if(!singersOccurrences.isEmpty()) {
                topArtist = Collections.max(singersOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            } else {
                topArtist = null;
            }
            //getting most frequent genre in the user playlist
            if(!genresOccurrences.isEmpty()) {
                topGenre = Collections.max(genresOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            } else {
                topGenre = null;
            }
            //getting most frequent country in the user playlist
            if(!countriesOccurrences.isEmpty()) {
                topCountry = Collections.max(countriesOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            }else{
                topCountry=null;
            }

            List<Track> suggestedTracks = new ArrayList<>();
            //adding at most 3 suggested tracks selecting them from the top 50 songs from the most frequent country on the playlist
            if(topCountry != null){
                List<Track> topTracks = proxy.getTracksChart(topCountry,50,"top");
                for(Track t: topTracks){
                    List<MusicGenreList> genresList = t.getTrack().getPrimaryGenres().getMusicGenreList();
                    if(!genresList.isEmpty()&&genresList.get(0).getMusicGenre().getMusicGenreName().equals(topGenre)){
                        if(suggestedTracks.size()<3){
                            suggestedTracks.add(t);
                        }else{
                            break;
                        }
                    }
                }
            }
            //adding one song of the most frequent artist in the playlist, selecting the most popular song of a random album
            if(topArtist != null) {
                List<Album> albumsList = proxy.getArtistAlbums(topArtist, 20);
                if (!albumsList.isEmpty()) {
                    int min = 0;
                    int max = albumsList.size() - 1;
                    int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                    Album randomAlbum = albumsList.get(random_int);
                    List<Track> albumTracks = proxy.getAlbumTracks(randomAlbum.getAlbum().getAlbumId(), 50);
                    Track bestTrack = Collections.max(albumTracks, Comparator.comparingInt(o -> o.getTrack().getTrackRating()));
                    suggestedTracks.add(bestTrack);
                }

                //adding song from most popular albums of 2 artists related to the most frequent artist in the playlist
                List<Artist> relatedArtists = proxy.getArtistsList("", 2, "", Methods.ARTIST_RELATED_GET, topArtist);
                List<Album> relatedAlbums = new ArrayList<>();
                //adds 1 random album for each of the 2 related artists to the list
                for (Artist artist : relatedArtists) {
                    List<Album> artistAlbums = proxy.getArtistAlbums(artist.getArtist().getArtistId(), 50);
                    if (!artistAlbums.isEmpty()) {
                        int min = 0;
                        int max = artistAlbums.size() - 1;
                        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                        relatedAlbums.add(artistAlbums.get(random_int));
                    }
                }
                //gets a random track for each of the album and adds them to the suggested tracks
                for (Album album : relatedAlbums) {
                    List<Track> albumTracks = proxy.getAlbumTracks(album.getAlbum().getAlbumId(), 30);
                    if (!albumTracks.isEmpty()) {
                        int min = 0;
                        int max = albumTracks.size() - 1;
                        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                        suggestedTracks.add(albumTracks.get(random_int));
                    }
                }
            }
            //populates the observable list of elements, converting Track instances into MusicElement and assuring there are no duplicates
            for(Track t : suggestedTracks){
                MusicElement musicElement = new MusicElement(t.getTrack().getTrackId(),t.getTrack().getTrackName(),"track",t.getTrack().getArtistName());
                //.contains() here refers to overriden equals method in MusicElement class to assure the same MusicElement ID can't appear twice between the tables
                if(!suggested.contains(musicElement)&&!playlistTableView.getItems().contains(musicElement)) {
                    suggested.add(musicElement);
                }
            }
        } catch(Exception e) {
            new Alert(Alert.AlertType.WARNING, "Couldn't find suggestions").showAndWait();
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (connection != null) {
                assert statement != null;
                statement.close();
                connection.close();
            }
        }
        return suggested;
    }

    /**
     * Adds a song selected from the suggestions table view to the database and to the playlist table view
     * @throws SQLException if connection to mysql database fails
     */
    @FXML
    private void handleAddSong() throws SQLException {
        int selectedIndex = selectedIndex(suggestedTableView);
        Integer ID = suggestedTableView.getSelectionModel().getSelectedItem().getId();
        String song = suggestedTableView.getSelectionModel().getSelectedItem().getName();
        String singer = suggestedTableView.getSelectionModel().getSelectedItem().getAuthorName();
        Connection connection = null;
        Statement statement = null;
        try {
                connection = DriverManager.getConnection(Constants.MYSQL_CONNECTION_URL);
                statement = connection.createStatement();
                //Assuring that the song isn't already present in the playlist checking all the song IDs
                ResultSet rs = statement.executeQuery("SELECT IDsong FROM playlist");
                boolean present = false;
                while(rs.next()) {
                    int id = rs.getInt("IDsong");
                    if(id == ID){
                        present = true;
                    }
                }
                if(!present){
                    PreparedStatement insertPlaylist = connection.prepareStatement("INSERT INTO playlist (IDsong, song, singer) VALUES (?, ?, ?)");
                    insertPlaylist.setInt(1, ID);
                    insertPlaylist.setString(2,song);
                    insertPlaylist.setString(3, singer);
                    insertPlaylist.executeUpdate();
                    new Alert(Alert.AlertType.INFORMATION, "The song has been successfully added to the playlist").showAndWait();
                    playlistTableView.getItems().add(suggestedTableView.getItems().get(selectedIndex));
                }else{
                    new Alert(Alert.AlertType.WARNING, "The song is already in your playlist").showAndWait();
                }
                suggestedTableView.getItems().remove(selectedIndex);
        } catch (NoSuchElementException | SQLException e) {
            showNoSongSelectedAlert();
            e.printStackTrace();
        } finally {
            if (connection != null) {
                assert statement != null;
                statement.close();
                connection.close();
            }
        }
    }
}
