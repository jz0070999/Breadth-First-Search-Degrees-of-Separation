package com.hollywooddos.jessica.views;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.hollywooddos.jessica.Entry;

public class StartScreen {

    @FXML
    private Label greetingLabel;

    public String greetingLabelText;

    public static Parent displayPage(String username) {
        URL location = StartScreen.class.getResource("start.fxml");
        FXMLLoader loader = new FXMLLoader(location);
        // loader.setController(new StartScreen());

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StartScreen controller = loader.getController();
        controller.greetingLabel.setText("Hello there, " + username + ".");
        return root;
    }

    @FXML
    protected void handleStartRandom() {

    }

    @FXML
    protected void handleStartCustom(ActionEvent event) {
        Parent root = PickerScreen.displayPage();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Hollywood Actors Degrees of Separation");
        stage.show();
        // createGUI(stage);
    }

    @FXML
    public void goBackToPrevious(ActionEvent event) {
        Parent root = new LoginScreen().displayPage();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();

    }

    public void createGUI(Stage primaryStage) {
        VBox root = new VBox(); // root node
        primaryStage.setScene(new Scene(root, 800, 600));
        root.setSpacing(12);
        root.getChildren().addAll(createPickerRow("starting"), createPickerRow("target"));

        // we are just making a flowpane so we add the other one to the root

        // commented out because it is wrong and makes the program crash
        // load the login page
        // Parent loginRoot = showLoginPage();
        // root.getChildren().add(loginRoot);
        // primaryStage.setScene(new Scene(root, 800, 600));
        // primaryStage.show();

        primaryStage.show();

        // prompt the user to enter a target actor for custom game

    }

    private Pane createPickerRow(String actorType) {
        HBox topHbox = new HBox(); // top node
        topHbox.setSpacing(8);

        VBox leftVbox = new VBox(); // left node
        leftVbox.setSpacing(8);
        leftVbox.setMinSize(400, 200);
        leftVbox.setMaxSize(400, 200);
        VBox rightVbox = new VBox(); // right node
        leftVbox.setSpacing(8);

        HBox propertySelectionHbox = new HBox();

        Label selectionHeading = new Label("Select a " + actorType + " property to search for");
        ListView<Entry> searchResults = new ListView<>();
        ComboBox<String> startingComboBox = new ComboBox<>();
        startingComboBox.getItems().addAll("Actor", "Movie title");
        startingComboBox.setPromptText("Select a property");
        TextField startingTextField = new TextField();
        startingTextField.setPromptText("Enter search phrase");
        Button searchButton = new Button("Search");
        // searchButton.setOnAction(event -> {
        // try {
        // handleSearchButtonClick(event, startingTextField.getText(), searchResults,
        // startingComboBox.getSelectionModel().getSelectedItem().toString());
        // } catch (TmdbException e) {
        // e.printStackTrace();
        // }
        // });
        propertySelectionHbox.getChildren().addAll(startingComboBox, startingTextField, searchButton);

        leftVbox.getChildren().addAll(selectionHeading, propertySelectionHbox, searchResults);

        Label selectActorTitLabel = new Label("Select an actor");
        rightVbox.getChildren().add(selectActorTitLabel);

        topHbox.getChildren().addAll(leftVbox, rightVbox);
        HBox.setHgrow(leftVbox, Priority.ALWAYS);
        HBox.setHgrow(rightVbox, Priority.ALWAYS);

        return topHbox;
    }
}
