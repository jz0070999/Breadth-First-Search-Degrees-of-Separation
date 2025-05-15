package com.hollywooddos.jessica.views;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.ActorConnectionFinder;
import com.hollywooddos.jessica.Entry;
import com.hollywooddos.jessica.GameMode;
import com.hollywooddos.jessica.GameSession;
import com.hollywooddos.jessica.Movie;
import com.hollywooddos.jessica.Player;
import com.hollywooddos.jessica.data.TmdbClient;
import com.hollywooddos.jessica.data.TmdbDatabaseClient;

import info.movito.themoviedbapi.tools.TmdbException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PickerScreen {

    @FXML
    private TextField startingTextField;
    @FXML
    private ComboBox<String> startingComboBox;
    @FXML
    private ListView<Entry> startingSearchResultsList;
    @FXML
    private ListView<Actor> startingActorListView;
    @FXML
    private TextField targetTextField;
    @FXML
    private ComboBox<String> targetComboBox;
    @FXML
    private ListView<Entry> targetSearchResultsList;
    @FXML
    private ListView<Actor> targetActorListView;

    private TmdbDatabaseClient dbClient;

    public PickerScreen() {
        this.dbClient = new TmdbDatabaseClient();
    }

    public static Parent displayPage() {
        URL location = PickerScreen.class.getResource("picker.fxml");
        FXMLLoader loader = new FXMLLoader(location);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PickerScreen controller = loader.getController();

        return root;
    }

    @FXML
    protected void handleShowLayoutButtonClick(ActionEvent event) throws TmdbException {

        ActorConnectionFinder finder = new ActorConnectionFinder();
        List<SeparationListEntry> entries = null;
        try {
            entries = finder.findConnection(startingActorListView.getSelectionModel().getSelectedItem(),
                    targetActorListView.getSelectionModel().getSelectedItem());
        } catch (TmdbException e) {
            e.printStackTrace();
        }

        // code used to help debug to figure why the bfs is not working
        if (entries != null) {
            for (SeparationListEntry entry : entries) {
                System.out.println(entry.getText());
            }
        } else {
            System.out.println("No entries found.");
        }

        if (entries != null) {
            SeparationListEntry[] entryArray = new SeparationListEntry[entries.size()];
            for (int i = 0; i < entries.size(); i++) {
                entryArray[i] = entries.get(i);
            }
        }

        Parent root = SeparationLayout.displayPage(entries.toArray(new SeparationListEntry[0]));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Hollywood Actors Degrees of Separation");
        stage.show();
    }

    @FXML
    protected void handleSearchResultClick(MouseEvent e, Entry selectedEntry, ListView<Actor> actorListView) {
        // private void handleSearchResultClick(MouseEvent e, Entry selectedEntry,
        // FlowPane actorFlowPane) {
        if (selectedEntry instanceof Movie) {
            Movie selectedMovie = (Movie) selectedEntry;
            try {
                List<Actor> actors = dbClient.getActorsForMovie(selectedMovie);
                // actorListView.setOrientation(Orientation.VERTICAL);
                actorListView.setOrientation(Orientation.VERTICAL);
                actorListView.setPrefSize(400, 100);

                for (Actor actor : actors) {
                    actorListView.getItems().add(actor);
                }
                actorListView.setCellFactory(lv -> new ListCell<>() {
                    @Override
                    public void updateItem(Actor item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    @FXML
    protected void handleStartingSearchButtonClick(ActionEvent e) {
        String text = startingTextField.getText();
        String selectedSearchProperty = startingComboBox.getSelectionModel().getSelectedItem().toString();
        startingSearchResultsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Entry selectedEntry = startingSearchResultsList.getSelectionModel().getSelectedItem();
                handleSearchResultClick(event, selectedEntry, startingActorListView);
            }
        });
        startingSearchResultsList.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Entry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }

            }
        });
        if (selectedSearchProperty.equals("Actor")) {
            List<Actor> fetchedActors = null;
            try {
                fetchedActors = this.dbClient.searchActors(text);
            } catch (TmdbException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            startingSearchResultsList.getItems().clear();
            for (Actor actor : fetchedActors) {
                startingSearchResultsList.getItems().add(new Actor(actor.getName(), actor.getId()));
            }
        } else if (selectedSearchProperty.equals("Movie title")) {
            List<Movie> fetchedMovies = null;
            try {
                fetchedMovies = this.dbClient.searchMovies(text);
            } catch (TmdbException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            startingSearchResultsList.getItems().clear();
            for (Movie movie : fetchedMovies) {
                startingSearchResultsList.getItems().add(new Movie(movie.getName(), movie.getId()));
            }
        }
    }

    @FXML
    protected void handleTargetSearchButtonClick(ActionEvent e) {
        String text = targetTextField.getText();
        String selectedSearchProperty = targetComboBox.getSelectionModel().getSelectedItem().toString();
        targetSearchResultsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Entry selectedEntry = targetSearchResultsList.getSelectionModel().getSelectedItem();
                handleSearchResultClick(event, selectedEntry, targetActorListView);
            }
        });
        targetSearchResultsList.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Entry item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item.getName());
                }

            }
        });
        if (selectedSearchProperty.equals("Actor")) {
            List<Actor> fetchedActors = null;
            try {
                fetchedActors = this.dbClient.searchActors(text);
            } catch (TmdbException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            targetSearchResultsList.getItems().clear();
            for (Actor actor : fetchedActors) {
                targetSearchResultsList.getItems().add(new Actor(actor.getName(), actor.getId()));
            }
        } else if (selectedSearchProperty.equals("Movie title")) {
            List<Movie> fetchedMovies = null;
            try {
                fetchedMovies = this.dbClient.searchMovies(text);
            } catch (TmdbException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            targetSearchResultsList.getItems().clear();
            for (Movie movie : fetchedMovies) {
                targetSearchResultsList.getItems().add(new Movie(movie.getName(), movie.getId()));
            }

        }
    }

    @FXML
    public void goBackToPrevious(ActionEvent event) {
        // load the previous scene/page
        Parent root = StartScreen.displayPage("");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Hollywood Actors Degrees of Separation");
        stage.show();

    }

    // go and play the game
    // @FXML
    // protected void goPlayGame(ActionEvent event) {

    // }
    //

}