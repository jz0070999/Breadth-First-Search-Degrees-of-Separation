package com.hollywooddos.jessica.views;

import com.hollywooddos.jessica.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import com.hollywooddos.jessica.data.TmdbDatabaseClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LeaderBoardScreen {

    @FXML
    private TableView<Score> tableView;
    @FXML
    private TableColumn<Score, Integer> rankColumn;
    @FXML
    private TableColumn<Score, String> usernameColumn;
    @FXML
    private TableColumn<Score, Integer> scoreColumn;

    // This will back your TableView
    private final ObservableList<Score> scores = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Bind table columns to Score properties
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Hook up the ObservableList to the TableView
        tableView.setItems(scores);

        // Load real data from the database
        loadScoresFromDatabase();
    }

    private void loadScoresFromDatabase() {
        TmdbDatabaseClient db = new TmdbDatabaseClient();
        try {
            // fetchTopScores returns a List<Score>
            List<Score> topList = db.fetchTopScores(10);
            // replace the contents of the ObservableList
            scores.setAll(topList);
        } catch (SQLException e) {
            e.printStackTrace();
            // you could also show an alert to the user here
        }

    }

    @FXML
    private void backButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Hollywood Actors Degrees of Separation");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
