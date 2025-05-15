package com.hollywooddos.jessica.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import com.hollywooddos.jessica.data.TmdbClient;
import com.hollywooddos.jessica.data.TmdbDatabaseClient;
import javafx.scene.Scene;
import java.io.IOException;

public class RegisterScreen {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    public void registerUser(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Username and password cannot be empty.");
            errorLabel.setVisible(true);
            return;
        }

        // Add logic to save the user to the database
        TmdbDatabaseClient dbService = new TmdbDatabaseClient();
        if (dbService.validateExistingPlayerInfo(username, password)) {
            errorLabel.setText("Username already exists. Please choose a different one.");
            errorLabel.setVisible(true);
            return;
        }
        dbService.addNewPlayer(username, password);

        // Show success message and redirect to login screen
        errorLabel.setText("Registration successful! Please go back to the login page.");
        errorLabel.setVisible(true);
    }

    @FXML
    public void goBackToLogin(ActionEvent event) {
        try {
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
