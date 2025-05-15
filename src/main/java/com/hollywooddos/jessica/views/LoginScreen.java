package com.hollywooddos.jessica.views;

import java.io.IOException;
import com.hollywooddos.jessica.CurrentPlayerInfo;
import com.hollywooddos.jessica.Player;
import java.net.URL;

import com.hollywooddos.jessica.data.TmdbDatabaseClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginScreen {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public Parent displayPage() {
        URL location = getClass().getResource("/com/hollywooddos/jessica/views/login.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        Parent loginRoot = null;
        try {
            loginRoot = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loginRoot;
    }

    @FXML
    public void showStartPageWithGreeting(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // 1) Validate non‐empty
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password are required.");
            errorLabel.setVisible(true);
            return;
        }

        // 2) Check against the database
        TmdbDatabaseClient dbClient = new TmdbDatabaseClient();
        boolean valid = dbClient.validateExistingPlayerInfo(username, password);
        if (!valid) {
            errorLabel.setText("Invalid username or password.");
            errorLabel.setVisible(true);
            return;
        }

        // 3) Credentials are good
        errorLabel.setVisible(false);
        // 4) Set the current player
        // Player currentPlayer = dbClient.getPlayerInfo(username);
        // CurrentPlayerInfo.setCurrentPlayer(currentPlayer);
        // 5) Load the menu screen

        Parent root = MenuScreen.displayPage(usernameField.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Hollywood Actors Degrees of Separation");
        stage.show();
    }

    @FXML
    public void showRegisterPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.setTitle("Register");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
