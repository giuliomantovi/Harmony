package com.gmantovi.harmony;

import com.gmantovi.harmony.config.Constants;
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
            Integer topArtist = null;
            String topGenre = null;
            String topCountry = null;
            if(!singersOccurrences.isEmpty()) {topArtist = Collections.max(singersOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();}
            if(!genresOccurrences.isEmpty()) {topGenre = Collections.max(genresOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();}
            if(!countriesOccurrences.isEmpty()) {topCountry = Collections.max(countriesOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey();}
            System.out.println("ARTISTA TOP: "+topArtist+" GENERE TOP: "+topGenre+ " LINGUA TOP: "+topCountry);


            System.out.println(Collections.max(singersOccurrences.entrySet(), Map.Entry.comparingByValue()).getKey());
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
            removeButton.setDisable(true);
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
