package com.gmantovi.harmony;

import com.gmantovi.harmony.gsonClasses.artist.Artist;
import com.gmantovi.harmony.gsonClasses.track.Track;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

public class SearchController {
    @FXML private ListView<?> listView;
    @FXML private TextField searchField;
    @FXML private TableView<?> tableView;
    @FXML private Button searchButton;

    @FXML
    public void initialize() throws IOException {

    }

    @FXML
    public void onSearchButtonClicked() throws IOException {
        try {

            MusixMatch m = new MusixMatch("391689594f1ad1d992b2efd5fc5862ef");
            Track l = m.getMatchingTrack(searchField.getText(),"");
            System.out.println("NOME CANZONE = " + l.getTrack().getTrackName() + "\nNOME ALBUM = " + l.getTrack().getAlbumName() + "\nNome Cantante = " + l.getTrack().getArtistName());


        }catch (NullPointerException e){
            System.out.println("NULLO");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
