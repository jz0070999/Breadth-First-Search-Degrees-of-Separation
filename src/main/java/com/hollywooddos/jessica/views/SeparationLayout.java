package com.hollywooddos.jessica.views;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.Entry;
import com.hollywooddos.jessica.EntryCell;
import com.hollywooddos.jessica.Movie;
import com.hollywooddos.jessica.views.EntryCellItem.ItemContainerType;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SeparationLayout {
    @FXML
    private VBox entryContainer;

    @FXML
    private ListView<SeparationListEntry> entryList;

    // public void addItemms(SeparationListEntry[] entries) {
    // // for each loop
    // for (SeparationListEntry entry : entries) {
    // HBox step = EntryCell.loadEntryStep(entry);
    // entryContainer.getChildren().add(step);
    // }
    // }

    // changec from ListView to VBox
    // private ListView<SeparationListEntry> entryList;

    public static Parent displayPage(SeparationListEntry[] entries) {
        URL location = SeparationLayout.class.getResource("separation_layout.fxml");
        FXMLLoader loader = new FXMLLoader(location);

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SeparationLayout controller = loader.getController();
        controller.setIsLast(entries);
        controller.createCellFactory();
        controller.addItems(entries);
        return root;
    }

    public void setIsLast(SeparationListEntry[] entries) {
        entries[entries.length - 1].setIsLast(true);
    }

    public void createCellFactory() {
        // Set a custom cell factory for the ListView to use the EntryCell
        entryList.setCellFactory(listView -> new ListCell<SeparationListEntry>() {
            @Override
            protected void updateItem(SeparationListEntry item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    if (item instanceof Actor) {
                        Actor actor = (Actor) item;
                        setGraphic(EntryCellItem.loadFXML(item.getText(), ItemContainerType.Actor, actor.isLast));
                    } else if (item instanceof Movie) {
                        Movie movie = (Movie) item;
                        setGraphic(EntryCellItem.loadFXML(item.getText(), ItemContainerType.Movie, movie.isLast));
                    }
                }
            }
        });
    }

    public void addItems(SeparationListEntry[] entries) {
        // Add items to the ListView
        entryList.getItems().addAll(Arrays.asList(entries));
    }

    // if it is not working this is why
    // @FXML
    // private void handleNextButtonClick(ActionEvent event) {
    // // 1) Compute score (you’ll need to pass it in or compute from your entries)
    // int score = computeScoreFromEntries();
    // // 2) Decide badge level
    // BADGE badge = BADGE.forScore(score); // you’d write a method in your enum
    // // 3) Load the results screen
    // Parent root;
    // try {
    // FXMLLoader loader = new FXMLLoader(getClass().getResource("results.fxml"));
    // root = loader.load();
    // ResultsController ctrl = loader.getController();
    // ctrl.init(score, badge); // pass in the data
    // } catch (IOException e) {
    // e.printStackTrace();
    // return;
    // }
    // // 4) Swap scenes
    // Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    // stage.setScene(new Scene(root, 800, 600));
    // stage.setTitle("Your Score & Leaderboard");
    // stage.show();
    // }

    @FXML
    public void goBackToPickerScreen(ActionEvent event) {
        // load the previous scene/page
        Parent root = PickerScreen.displayPage();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Hollywood Actors Degrees of Separation");
        stage.show();

        // }
    }

}
