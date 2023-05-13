package com.gmantovi.harmony;

import com.gmantovi.harmony.gsonClasses.artist.Alias;
import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.lyrics.Lyrics;
import com.gmantovi.harmony.gsonClasses.snippet.Snippet;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;

import java.io.IOException;
import java.util.List;

public class SearchController {
    @FXML private ListView<String> listView;
    @FXML private TextField searchField;
    @FXML private TableView<Element> tableView;
    @FXML private Button searchButton;
    @FXML private TableColumn<Element, String> nameColumn;
    @FXML private TableColumn<Element, String> typeColumn;
    @FXML private TableColumn<Element, Integer> idColumn;
    @FXML private ComboBox<String> infoBox;

    @FXML
    public void initialize() throws IOException {
        searchField.setTooltip(new Tooltip("If you are looking for a track, insert both track name and artist name \n separated by '-'. Example: 'Billie Jean-Michael Jackson' "));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showOptions(newValue));
    }

    public void showOptions(Element element){
        if(element==null) {
            System.out.println("Elemento nullo");
            return;
        }
        if(element.getType().equals("track")){
            infoBox.setItems(FXCollections.observableArrayList(
                    "General track infos","Get lyrics","Get snippet","Get other songs of the album"));

        }else{
            infoBox.setItems(FXCollections.observableArrayList(
                    "General artist infos","Get discography","Get related artists"));
        }
        infoBox.getSelectionModel().selectFirst();
        //onInfoBoxUpdated();
    }

    @FXML
    public void onInfoBoxChanged(){
        if(infoBox.getSelectionModel().getSelectedItem()!=null) {
            try {
                listView.setStyle("-fx-border-width: 1px");
                MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
                ObservableList<String> infos = null;
                switch(infoBox.getSelectionModel().getSelectedItem()){
                    case "General track infos":
                        System.out.println("GET TRACK INFOS");
                        Track track = m.getTrack(tableView.getSelectionModel().getSelectedItem().getId());
                        infos = FXCollections.observableArrayList((List.of(
                                "Name: " + track.getTrack().getTrackName(),
                                "Singer: " + track.getTrack().getArtistName(),
                                "Album: " + track.getTrack().getAlbumName(),
                                "Primary genre: " + track.getTrack().getPrimaryGenres().getMusicGenreList().get(0).getMusicGenre().getMusicGenreName(),
                                "Popularity rating (1-100): " + track.getTrack().getTrackRating(),
                                "Song link: " + track.getTrack().getTrackShareUrl()))
                        );
                        break;

                    case "General artist infos":
                        System.out.println("GET ARTIST INFOS");
                        Artist artist = m.getArtist(tableView.getSelectionModel().getSelectedItem().getId());

                        String alias = "Alias: ";
                        if(!artist.getArtist().getAliasList().isEmpty()){
                            for(Alias a: artist.getArtist().getAliasList()){
                                alias = alias.concat(a.getAlias() +", ");
                            }
                            alias = alias.substring(0, alias.length() - 1);
                        }else{
                            alias = alias.concat("no alias");
                        }

                        String country = "Country: ";
                        if(artist.getArtist().getArtistCountry().isEmpty()){
                            country = country.concat("undefined");
                        }else{
                            country = country.concat(artist.getArtist().getArtistCountry());
                        }

                        infos = FXCollections.observableArrayList((List.of(
                                "Name: " + artist.getArtist().getArtistName(),
                                alias,
                                country,
                                "Popularity rating (1-100): " + artist.getArtist().getArtistRating()))
                        );
                        break;

                    case "Get lyrics":
                        Lyrics lyrics = m.getLyrics(tableView.getSelectionModel().getSelectedItem().getId());
                        ObservableList<String> lyricsList = FXCollections.observableArrayList((List.of(
                                "LYRICS:",
                                lyrics.getLyricsBody())));
                        listView.setItems(lyricsList);
                        break;

                    case "Get snippet":
                        Snippet snippet = m.getSnippet(tableView.getSelectionModel().getSelectedItem().getId());
                        infos = FXCollections.observableArrayList((List.of(
                                "Language: " + snippet.getSnippetLanguage(),
                                "Snippet: \n" + snippet.getSnippetBody()))
                        );
                        break;
                    case "Get other songs of the album":
                        break;
                    default:
                }
                if(infos!=null) {
                    listView.setItems(infos);
                    listView.setEditable(true);
                    listView.setCellFactory(TextFieldListCell.forListView());
                }
            }catch (NullPointerException e){
                System.out.println("NULLO");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void onSearchButtonClicked() throws IOException {
        ObservableList<Element> elements = FXCollections.observableArrayList();
        try {
            MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
            if(searchField.getText().contains("-")){
                String[] params = searchField.getText().split("-");
                if((!params[0].isEmpty())&&(!params[1].isEmpty())){
                    Track l = m.getMatchingTrack(params[0],params[1]);
                    if(l!=null){
                        elements.add(new Element(l.getTrack().getTrackId(),l.getTrack().getTrackName(),"track"));
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
                System.out.println("table view vuota");
            }
        }catch (NullPointerException e){
            System.out.println("NULLO");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
