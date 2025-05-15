package com.hollywooddos.jessica;

import com.hollywooddos.jessica.views.SeparationListEntry;
import com.hollywooddos.jessica.data.TmdbDatabaseClient;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EntryCell {

    // do an HBox
    public static HBox loadEntryStep(SeparationListEntry entry) {
        Label label = new Label(entry.getText());
        label.setStyle("-fx-font-size: 16px; -fx-padding: 10px;");

        if (entry instanceof Actor) {
            label.setStyle(label.getStyle() + "-fx-background-color: #a0f5a0; -fx-text-fill: black;");
        } else if (entry instanceof Movie) {
            label.setStyle(label.getStyle() + "-fx-background-color: #f5a0a0; -fx-text-fill: black;");
        } else {
            label.setStyle(label.getStyle() + "-fx-background-color: #a0a0f5; -fx-text-fill: black;");
        }
        HBox box = new HBox(label);
        box.setSpacing(10);
        return box;
    }

}
