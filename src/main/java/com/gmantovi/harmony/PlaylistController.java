package com.gmantovi.harmony;

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
import java.util.NoSuchElementException;

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

        playlistTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onPlaylistSelected(newValue));
        suggestedTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> onSuggestedSelected(newValue));
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
            //Rimozione dal database
        } catch (NoSuchElementException | SQLException e) {
            showNoSongSelectedAlert();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddSong() {
        try {

            int selectedIndex = selectedIndex(suggestedTableView);
            playlistTableView.getItems().add(suggestedTableView.getItems().get(selectedIndex));
            //Rimozione dal database

        } catch (NoSuchElementException e) {
            showNoSongSelectedAlert();
        }
    }
}
