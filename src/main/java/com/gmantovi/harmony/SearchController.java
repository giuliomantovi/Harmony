package com.gmantovi.harmony;

import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class SearchController {
    @FXML private ListView<?> listView;
    @FXML private TextField searchField;
    @FXML private TableView<Element> tableView;
    @FXML private Button searchButton;
    @FXML private TableColumn<Element, String> nameColumn;
    @FXML private TableColumn<Element, String> typeColumn;
    @FXML private TableColumn<Element, Integer> idColumn;

    @FXML
    public void initialize() throws IOException {
        searchField.setTooltip(new Tooltip("If you are looking for a track, insert both track name and artist name \n separated by '-'. Example: 'Billie Jean-Michael Jackson' "));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
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
            if(!elements.isEmpty()){
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
