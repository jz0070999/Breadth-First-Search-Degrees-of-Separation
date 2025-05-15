package com.hollywooddos.jessica.views;

import java.io.IOException;
import java.security.InvalidParameterException;

import com.hollywooddos.jessica.Actor;
import com.hollywooddos.jessica.Movie;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class EntryCellItem {
    @FXML
    public Label entryName;

    @FXML
    public Rectangle entryRectangle;

    @FXML
    public Label pointsToName;

    @FXML
    public VBox arrow;

    public EntryCellItem() {
        super();
    }

    public void hidePointsToArrow(boolean isHidden) {
        arrow.setVisible(!isHidden);
    }

    public void setLabelText(String labelText) {
        entryName.setText(labelText);
    }

    public void setArrowLabelText(ItemContainerType type) {
        if (type == ItemContainerType.Actor) {
            pointsToName.setText("starred in");
        } else if (type == ItemContainerType.Movie) {
            pointsToName.setText("with co-star");
        }
    }

    public void setRectangleColor(ItemContainerType type) {
        if (type == ItemContainerType.Actor) {
            entryRectangle.setFill(Color.web("#facd68"));
        } else if (type == ItemContainerType.Movie) {
            entryRectangle.setFill(Color.web("#fc76b3"));
        }
    }

    enum ItemContainerType {
        Actor, Movie
    }

    // method to load the corresponding FXML file

    protected static Parent loadFXML(String str, ItemContainerType type, boolean isLastItem) {
        try {
            FXMLLoader loader = new FXMLLoader(EntryCellItem.class.getResource("entry_cell_item.fxml"));
            Parent p = loader.load();
            EntryCellItem controller = loader.getController();
            controller.setLabelText(str);
            controller.setRectangleColor(type);
            controller.setArrowLabelText(type);

            controller.hidePointsToArrow(isLastItem);

            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
