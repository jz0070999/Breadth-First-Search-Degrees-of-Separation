package com.hollywooddos.jessica;

import java.io.IOException;
import java.util.List;

import com.hollywooddos.jessica.views.LoginScreen;

import info.movito.themoviedbapi.tools.TmdbException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception, TmdbException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Hollywood Actors Degrees of Separation");
        // testing the GUI
        LoginScreen loginScreen = new LoginScreen();
        primaryStage.setScene(new Scene(loginScreen.displayPage(), 800, 600));
        // createGUI(primaryStage);
        // showStartScreen();

        primaryStage.show();
    }

    // private void showStartScreen() {
    // // Title Headline
    // Label titleLabel = new Label("Hollywood Actors Degrees of Separation");
    // titleLabel.setStyle("-fx-font-size: 24px; fx-font-weight:bold;");

    // // Game Mode Buttons
    // Button randomGameButton = new Button("Random Game");
    // Button customGameButton = new Button("Custom Game");
    // customGameButton.setOnAction(e -> createGUI(primaryStage));

    // randomGameButton.setStyle("-fx-font-size: 16px; -fx-padding:10px;");
    // customGameButton.setStyle("-fx-font-size: 16px; -fx-padding:10px;");

    // // Event Handlers
    // // randomGameButton.setOnAction(e -> startGame(GameMode.Mode.RANDOM_GAME));
    // // randomGameButton.setOnAction(e -> startGame(GameMode.Mode.RANDOM_GAME));

    // // Layout
    // VBox layout = new VBox(20);
    // layout.getChildren().addAll(titleLabel, randomGameButton, customGameButton);
    // layout.setAlignment(Pos.CENTER);

    // Scene startScene = new Scene(layout, 800, 600);
    // primaryStage.setScene(startScene);
    // }

    // search
    // List<Movie> fetchedMovies = client.searchActor("The Break");

    // public void createGUI(Stage primaryStage) {
    // VBox root = new VBox(); // root node
    // primaryStage.setScene(new Scene(root, 800, 600));
    // root.setSpacing(12);
    // root.getChildren().addAll(createPickerRow("starting"),
    // createPickerRow("target"));

    // // we are just making a flowpane so we add the other one to the root

    // // commented out because it is wrong and makes the program crash
    // // load the login page
    // // Parent loginRoot = showLoginPage();
    // // root.getChildren().add(loginRoot);
    // // primaryStage.setScene(new Scene(root, 800, 600));
    // // primaryStage.show();

    // primaryStage.show();

    // // prompt the user to enter a target actor for custom game

    // }

    // private Pane createPickerRow(String actorType) {
    // HBox topHbox = new HBox(); // top node
    // topHbox.setSpacing(8);

    // VBox leftVbox = new VBox(); // left node
    // leftVbox.setSpacing(8);
    // leftVbox.setMinSize(400, 200);
    // leftVbox.setMaxSize(400, 200);
    // VBox rightVbox = new VBox(); // right node
    // leftVbox.setSpacing(8);

    // HBox propertySelectionHbox = new HBox();

    // Label selectionHeading = new Label("Select a " + actorType + " property to
    // search for");
    // ListView<Entry> searchResults = new ListView<>();
    // ComboBox<String> startingComboBox = new ComboBox<>();
    // startingComboBox.getItems().addAll("Actor", "Movie title");
    // startingComboBox.setPromptText("Select a property");
    // TextField startingTextField = new TextField();
    // startingTextField.setPromptText("Enter search phrase");
    // Button searchButton = new Button("Search");
    // searchButton.setOnAction(event -> {
    // try {
    // handleSearchButtonClick(event, startingTextField.getText(), searchResults,
    // startingComboBox.getSelectionModel().getSelectedItem().toString());
    // } catch (TmdbException e) {
    // e.printStackTrace();
    // }
    // });
    // propertySelectionHbox.getChildren().addAll(startingComboBox,
    // startingTextField, searchButton);

    // searchResults.setCellFactory(lv -> new ListCell<>() {
    // @Override
    // public void updateItem(Entry item, boolean empty) {
    // super.updateItem(item, empty);
    // if (empty) {
    // setText(null);
    // } else {
    // setText(item.getName());
    // }
    // lv.setOnMouseClicked(event -> {
    // if (event.getClickCount() == 2) {
    // Entry selectedEntry = lv.getSelectionModel().getSelectedItem();
    // // actorFlowPane.setOrientation(Orientation.VERTICAL);
    // // handleSearchResultClick(event, selectedEntry, actorFlowPane);
    // handleSearchResultClick(event, selectedEntry, rightVbox);

    // }
    // });
    // }
    // });

    // leftVbox.getChildren().addAll(selectionHeading, propertySelectionHbox,
    // searchResults);

    // Label selectActorTitLabel = new Label("Select an actor");
    // rightVbox.getChildren().add(selectActorTitLabel);

    // topHbox.getChildren().addAll(leftVbox, rightVbox);
    // HBox.setHgrow(leftVbox, Priority.ALWAYS);
    // HBox.setHgrow(rightVbox, Priority.ALWAYS);

    // return topHbox;
    // }

    // private void handleSearchResultClick(MouseEvent e, Entry selectedEntry, VBox
    // actorVBox) {
    // if (actorVBox.getChildren().size() > 1) {
    // actorVBox.getChildren().removeLast();
    // }
    // // private void handleSearchResultClick(MouseEvent e, Entry selectedEntry,
    // // FlowPane actorFlowPane) {
    // if (selectedEntry instanceof Movie) {
    // Movie selectedMovie = (Movie) selectedEntry;
    // try {
    // List<Actor> actors = selectedMovie.getCredits();
    // ListView<Entry> actorListView = new ListView<>();
    // // actorListView.setOrientation(Orientation.VERTICAL);
    // actorListView.setOrientation(Orientation.VERTICAL);
    // actorListView.setPrefSize(400, 100);

    // for (Actor actor : actors) {
    // actorListView.getItems().add(actor);
    // }
    // actorListView.setCellFactory(lv -> new ListCell<>() {
    // @Override
    // public void updateItem(Entry item, boolean empty) {
    // super.updateItem(item, empty);
    // if (empty) {
    // setText(null);
    // } else {
    // setText(item.getName());
    // }
    // }
    // });
    // actorVBox.getChildren().add(actorListView);
    // } catch (TmdbException e1) {
    // e1.printStackTrace();
    // }
    // }
    // }

    // private void handleSearchButtonClick(ActionEvent e, String text,
    // ListView<Entry> searchResultsList,
    // String selectedSearchProperty) throws TmdbException {
    // TmdbClient client = new TmdbClient();

    // if (selectedSearchProperty.equals("Actor")) {
    // List<Actor> fetchedActors = client.searchActors(text);
    // searchResultsList.getItems().clear();
    // for (Actor actor : fetchedActors) {
    // searchResultsList.getItems().add(new Actor(actor.getName(), actor.getId()));
    // }
    // } else if (selectedSearchProperty.equals("Movie title")) {
    // List<Movie> fetchedMovies = client.searchMovies(text);
    // searchResultsList.getItems().clear();
    // for (Movie movie : fetchedMovies) {
    // searchResultsList.getItems().add(new Movie(movie.getName(), movie.getId()));
    // }

    // }
    // }

}