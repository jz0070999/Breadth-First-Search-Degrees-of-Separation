package com.hollywooddos.jessica.views;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MenuScreen {

    public static Parent displayPage(String usernameField) {
        URL location = MenuScreen.class.getResource("menu.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void handleStartGame(ActionEvent event) {
        try {
            // Load picker screen
            Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Pick Your Actors");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLeaderboard(ActionEvent event) {
        try {
            // Load your leaderboard FXML
            Parent root = FXMLLoader.load(getClass().getResource("leaderboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Leaderboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // adding logic for the back button
    @FXML
    private void goBackToPrevious(ActionEvent event) {
        try {
            // Load the previous screen, e.g. login or start screen
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
