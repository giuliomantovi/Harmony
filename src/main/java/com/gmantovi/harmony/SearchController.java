package com.gmantovi.harmony;

import com.gmantovi.harmony.gsonClasses.artist.Artist;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class SearchController {
    @FXML private ListView<?> listView;
    @FXML private TextField searchField;
    @FXML private TableView<?> tableView;
    @FXML private TextField searchButton;

    @FXML
    public void initialize() throws IOException {

    }

    @FXML
    public void onSearchButtonClicked() throws IOException {
        try {

        }catch (NullPointerException e){
            System.out.println("NULLO");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
