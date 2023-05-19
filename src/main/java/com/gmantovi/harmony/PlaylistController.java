package com.gmantovi.harmony;

import com.gmantovi.harmony.config.Constants;
import com.gmantovi.harmony.gsonClasses.album.Album;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.gmantovi.harmony.gsonClasses.track.MusicGenre;
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
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.io.IOException;
import java.sql.*;
import java.util.*;

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
        //chiamata per popolare tv con db call
        playlistIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        playlistSingerColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        playlistSongColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistTableView.setItems(getPlaylistData());
        suggestedSongColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        suggestedIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        suggestedSingerColumn.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        suggestedTableView.setItems(getSuggestedData());

        playlistTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onPlaylistSelected(newValue));
        suggestedTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onSuggestedSelected(newValue));
    }

    private ObservableList<Element> getSuggestedData() throws SQLException {
        ObservableList<Element> suggested =FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
            MusixMatch m = new MusixMatch(Constants.PERSONAL_API_KEY);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/harmony?user=root&password=");
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT IDsong FROM playlist");
            HashMap<String,Integer> genresOccurrences = new HashMap<>();
            HashMap<Integer,Integer> singersOccurrences = new HashMap<>();
            HashMap<String,Integer> countriesOccurrences = new HashMap<>();
            //consigliare una track dell'album più visto, una delle top 10 della nazionalità più vista dello stile più visto, uno del primo artista correlato a quello più visto,
            //track più popolare dell'album più visto?
            while(rs.next()) {
                int id = rs.getInt("IDsong");
                Track track = m.getTrack(id);
                //getting genres, singers and countries occurences in the playlist
                List<MusicGenreList> genre = track.getTrack().getPrimaryGenres().getMusicGenreList();
                //SI POTREBBE OTTIMIZZARE CON UNA FUNZIONE UNICA (?)
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
                System.out.println("STATO: "+ country);
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
            Integer topArtist = null;
            String topGenre = null;
            String topCountry;
            if(!singersOccurrences.isEmpty()) {topArtist = Collections.max(singersOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();}
            if(!genresOccurrences.isEmpty()) {topGenre = Collections.max(genresOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();}
            if(!countriesOccurrences.isEmpty()) {topCountry = Collections.max(countriesOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();}else{topCountry=null;}
            System.out.println("ARTISTA TOP: "+topArtist+" GENERE TOP: "+topGenre+ " LINGUA TOP: "+topCountry);

            //adding at most 3 suggested tracks selecting them from the top 50 songs from the most frequent country on the playlist
            List<Track> topTracks = m.getTracksChart(topCountry,50,"top");
            List<Track> suggestedTracks = new ArrayList<>();
            //System.out.println("TRACK TROVATE NELLA TOP 20:");
            for(Track t: topTracks){
                List<MusicGenreList> genresList = t.getTrack().getPrimaryGenres().getMusicGenreList();
                if(!genresList.isEmpty()&&genresList.get(0).getMusicGenre().getMusicGenreName().equals(topGenre)){
                    if(suggestedTracks.size()<3){
                        suggestedTracks.add(t);
                        //System.out.println(t.getTrack().getTrackName());
                    }else{
                        break;
                    }
                }
            }

            if(topArtist != null) {
                //adding one song of the most frequent artist in the playlist
                List<Album> albumsList = m.getArtistAlbums(topArtist,20);
                if(!albumsList.isEmpty()){
                    int min = 0; // Minimum value of range
                    int max = albumsList.size()-1; // Maximum value of range
                    int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
                    Album randomAlbum = albumsList.get(random_int);
                    List<Track> albumTracks = m.getAlbumTracks(randomAlbum.getAlbum().getAlbumId(),50);
                    Track bestTrack = Collections.max(albumTracks, Comparator.comparingInt(o -> o.getTrack().getTrackRating()));
                    suggestedTracks.add(bestTrack);
                    //System.out.println("BEST TRACK: " + bestTrack.getTrack().getTrackName() + " RATING: " + bestTrack.getTrack().getTrackRating());
                }
                //adding song from most popular albums of 2 artists related to the most frequent artist in the playlist
                List<Artist> relatedArtists = m.getArtistsList("",2,"","get_related_artist",topArtist);
                List<Album> relatedAlbums = new ArrayList<>();
                for(Artist artist : relatedArtists){
                    //System.out.println("LISTA ALBUM CORRELATI PER " + artist.getArtist().getArtistName() + ": ");
                    List<Album> artistAlbums = m.getArtistAlbums(artist.getArtist().getArtistId(),50);
                    if(!artistAlbums.isEmpty()){
                        int min = 0; // Minimum value of range
                        int max = artistAlbums.size()-1; // Maximum value of range
                        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
                        relatedAlbums.add(artistAlbums.get(random_int));
                    }
                }
                for(Album album : relatedAlbums){
                    List<Track> albumTracks = m.getAlbumTracks(album.getAlbum().getAlbumId(),30);
                    if(!albumTracks.isEmpty()){
                        int min = 0; // Minimum value of range
                        int max = albumTracks.size()-1; // Maximum value of range
                        int random_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
                        suggestedTracks.add(albumTracks.get(random_int));
                        //System.out.println("Track scelta random: " + albumTracks.get(random_int).getTrack().getTrackName());
                    }
                }
            }

            for(Track t : suggestedTracks){
                suggested.add(new Element(t.getTrack().getTrackId(),t.getTrack().getTrackName(),"track",t.getTrack().getArtistName()));
                //System.out.println("TRACK: " + t.getTrack().getTrackName() + "ARTISTA: " + t.getTrack().getArtistName());
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }catch(Exception e){e.printStackTrace();} finally {
            if (connection != null) {
                assert statement != null;
                statement.close();
                connection.close();
            }
        }


        return suggested;
    }

    private void onSuggestedSelected(Element newValue) {
        addButton.setDisable(false);
    }

    private void onPlaylistSelected(Element newValue) {
        removeButton.setDisable(false);
    }

    ObservableList<Element> getPlaylistData() throws SQLException {
        ObservableList<Element> playlist =FXCollections.observableArrayList();
        Connection connection = null;
        Statement statement = null;
        try{
            //Class.forName("com.mysql.cj.jdbc.Driver");
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
            System.out.println("OGGETTI " + playlist.get(0).getName());
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
            //removeButton.setDisable(true);
            //Rimozione dal database
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
                //Class.forName("com.mysql.cj.jdbc.Driver");
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
                    addButton.setDisable(true);
                }else{
                    showNoSongSelectedAlert();
                }
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
