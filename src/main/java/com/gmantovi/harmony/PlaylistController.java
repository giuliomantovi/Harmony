package com.gmantovi.harmony;

import com.gmantovi.harmony.config.Constants;
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

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlaylistController {

    @FXML private Button addButton;
    @FXML private TableColumn<Element, String> playlistIDColumn;
    @FXML private TableColumn<Element, String> playlistSingerColumn;
    @FXML private TableColumn<Element, String> playlistSongColumn;
    @FXML private TableView<Element> playlistTableView;
    @FXML private Button removeButton;
    @FXML private TableColumn<Element, String> suggestedIDColumn;
    @FXML private TableColumn<Element, String> suggestedSingerColumn;
    @FXML private TableColumn<Element, String> suggestedSongColumn;
    @FXML private TableView<Element> suggestedTableView;

    @FXML
    public void initialize() throws IOException, SQLException {
        playlistIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        playlistSingerColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        playlistSongColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistTableView.setItems(getPlaylistData());
        suggestedSongColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        suggestedIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        suggestedSingerColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        executeTask();
        playlistTableView.getSelectionModel().selectedItemProperty().addListener((observable) -> onPlaylistSelected());
        suggestedTableView.getSelectionModel().selectedItemProperty().addListener((observable) -> onSuggestedSelected());
    }

    private void onSuggestedSelected() {
        addButton.setDisable(false);
    }

    private void onPlaylistSelected() {
        removeButton.setDisable(false);
    }

    private void executeTask () throws SQLException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executor.submit(() -> {
            try {
                suggestedTableView.setItems(getSuggestedData());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private ObservableList<Element> getSuggestedData() throws SQLException {
        if (playlistTableView.getItems().isEmpty()) return null;
        ObservableList<Element> suggested =FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        try{
            MusixMatchAPI m = new MusixMatchAPI(Constants.PERSONAL_API_KEY);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=");
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT IDsong FROM playlist");
            HashMap<String,Integer> genresOccurrences = new HashMap<>();
            HashMap<Integer,Integer> singersOccurrences = new HashMap<>();
            HashMap<String,Integer> countriesOccurrences = new HashMap<>();

            while(rs.next()) {
                int id = rs.getInt("IDsong");
                Track track = m.getTrack(id);
                //getting genres, singers and countries occurences in the playlist
                List<MusicGenreList> genre = track.getTrack().getPrimaryGenres().getMusicGenreList();
                if(!genre.isEmpty()){
                    String genreName = genre.get(0).getMusicGenre().getMusicGenreName();
                    if(genresOccurrences.containsKey(genreName)){
                        genresOccurrences.put(genreName, genresOccurrences.get(genreName) + 1);
                    }else{
                        genresOccurrences.put(genreName,1);
                    }
                }

                Integer singer = track.getTrack().getArtistId();
                if(singer != null){
                    if(singersOccurrences.containsKey(singer)){
                        singersOccurrences.put(singer, singersOccurrences.get(singer) + 1);
                    }else{
                        singersOccurrences.put(singer,1);
                    }
                }

                String country = m.getArtist(track.getTrack().getArtistId()).getArtist().getArtistCountry();
                //System.out.println("STATO: "+ country);
                if(country != null && !country.equals("")){
                    if(countriesOccurrences.containsKey(country)){
                        countriesOccurrences.put(country, countriesOccurrences.get(country) + 1);
                    }else{
                        countriesOccurrences.put(country,1);
                    }
                }

                //FARE THREAD PER QUESTO METODO?
            }

            //getting most frequent artist, genre and country in the user playlist
            Integer topArtist;
            String topGenre;
            String topCountry;
            if(!singersOccurrences.isEmpty()) {
                topArtist = Collections.max(singersOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            } else {
                topArtist = null;
            }
            if(!genresOccurrences.isEmpty()) {
                topGenre = Collections.max(genresOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            } else {
                topGenre = null;
            }
            if(!countriesOccurrences.isEmpty()) {
                topCountry = Collections.max(countriesOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();
            }else{
                topCountry=null;
            }

            System.out.printf("TOP ARTISTI: " + topArtist + " TOP GENRE: " + topGenre + " TOP COUNTRY: " + topCountry);
            List<Track> suggestedTracks = new ArrayList<>();
            if(topCountry != null){
                //adding at most 3 suggested tracks selecting them from the top 50 songs from the most frequent country on the playlist
                List<Track> topTracks = m.getTracksChart(topCountry,50,"top");
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

            if(topArtist != null) {
                //adding one song of the most frequent artist in the playlist
                List<Album> albumsList = m.getArtistAlbums(topArtist, 20);
                if (!albumsList.isEmpty()) {
                    int min = 0;
                    int max = albumsList.size() - 1;
                    int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                    Album randomAlbum = albumsList.get(random_int);
                    List<Track> albumTracks = m.getAlbumTracks(randomAlbum.getAlbum().getAlbumId(), 50);
                    Track bestTrack = Collections.max(albumTracks, Comparator.comparingInt(o -> o.getTrack().getTrackRating()));
                    suggestedTracks.add(bestTrack);
                }

                //adding song from most popular albums of 2 artists related to the most frequent artist in the playlist
                List<Artist> relatedArtists = m.getArtistsList("", 2, "", "get_related_artist", topArtist);
                List<Album> relatedAlbums = new ArrayList<>();
                for (Artist artist : relatedArtists) {
                    List<Album> artistAlbums = m.getArtistAlbums(artist.getArtist().getArtistId(), 50);
                    if (!artistAlbums.isEmpty()) {
                        int min = 0;
                        int max = artistAlbums.size() - 1;
                        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                        relatedAlbums.add(artistAlbums.get(random_int));
                    }
                }
                for (Album album : relatedAlbums) {
                    List<Track> albumTracks = m.getAlbumTracks(album.getAlbum().getAlbumId(), 30);
                    if (!albumTracks.isEmpty()) {
                        int min = 0;
                        int max = albumTracks.size() - 1;
                        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
                        suggestedTracks.add(albumTracks.get(random_int));
                    }
                }

            }

            for(Track t : suggestedTracks){
                suggested.add(new Element(t.getTrack().getTrackId(),t.getTrack().getTrackName(),"track",t.getTrack().getArtistName()));
            }
        } catch(Exception e) {
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

    private ObservableList<Element> getPlaylistData() throws SQLException {
        ObservableList<Element> playlist =FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=");
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM playlist");
            while(rs.next()) {
                int id = rs.getInt("IDsong");
                String song = rs.getString("song");
                String singer= rs.getString("singer");
                System.out.println("ID: "+id + "SONG: "+song+" singer"+singer);
                playlist.add(new Element(id,song,"track,",singer));
            }
        } catch(SQLException e) {
            e.printStackTrace();
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
     * @return the index of the selected song
     */
    int selectedIndex(TableView<Element> tableView) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
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

    @FXML
    private void handleRemoveSong() {
        try {
            Connection connection = null;
            int selectedIndex = selectedIndex(playlistTableView);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=");
            PreparedStatement deleteSong = connection.prepareStatement("DELETE FROM playlist WHERE IDsong=?");
            deleteSong.setInt(1, playlistTableView.getItems().get(selectedIndex).getId());
            deleteSong.executeUpdate();
            playlistTableView.getItems().remove(selectedIndex);
        } catch (NoSuchElementException | SQLException e) {
            showNoSongSelectedAlert();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddSong() throws SQLException {
        Integer ID = suggestedTableView.getSelectionModel().getSelectedItem().getId();
        String song = suggestedTableView.getSelectionModel().getSelectedItem().getName();
        String singer = suggestedTableView.getSelectionModel().getSelectedItem().getAuthorName();
        int selectedIndex = selectedIndex(suggestedTableView);
        Connection connection = null;
        Statement statement = null;
        try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=");
                statement = connection.createStatement();
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
                    new Alert(Alert.AlertType.CONFIRMATION, "The song has been successfully added to the playlist").showAndWait();
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
