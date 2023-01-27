package com.example.DB;

import javafx.scene.ImageCursor;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.example.DB.Game.path;
import static com.example.DB.Game.players;

public class PlayerInfoBox extends StackPane {
    private final TextField textField; //player's name text-field
    private final ColorPicker colorPicker; // is used to get player's color
    private final RadioButton radioButton; // is used to get first player starter
    private static final ToggleGroup tg = new ToggleGroup(); // groups the radio buttons

    // PlayerInfoBox constructor
    public PlayerInfoBox(int playerNo) {
        // the horizontal box includes a text, a player's name text-field, a color picker and a radio button
        HBox playerBox = new HBox(10);
        // player text
        Text text = new Text("Player " + (playerNo + 1) + " Name");
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Harrington", 14));
        textField = new TextField();
        textField.setStyle("-fx-background-color: transparent;" +
                " -fx-border-width:0.5;" +
                " -fx-border-color:white;" +
                " -fx-text-inner-color: white; -fx-focus-color: blue;");
        textField.setFont(Font.font("Drawing-Rainbow.ttf"));
        textField.setPrefWidth(100);
        textField.setPromptText("name here");
        // set text-field mouse cursor
        Image cursorClickImg = new Image(path.toAbsolutePath() + "\\text.png");
        textField.setCursor(new ImageCursor(cursorClickImg));
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.WHITE);
        colorPicker.setStyle("-fx-color-label-visible: false;" +
                "-fx-background-color: lightgray;" +
                "-fx-background-radius: 2;" +
                "-fx-padding: 0;" +
                "-fx-background-insets: 0, 0;" +
                "-fx-opacity: 0.9");
        colorPicker.setOnAction(e -> {
            players[0].setColor(colorPicker.getValue());
        });
        DropShadow drop = new DropShadow(10, Color.WHITE);
        drop.setInput(new Glow());
        textField.setOnMouseEntered(event -> {
            textField.setEffect(drop);
        });
        textField.setOnMouseExited(event -> {
            textField.setEffect(null);
        });
        colorPicker.setOnMouseEntered(event -> {
            colorPicker.setEffect(drop);
        });
        colorPicker.setOnMouseExited(event -> {
            colorPicker.setEffect(null);
        });
        // set the hand mouse cursor for radio button and color picker
        cursorClickImg = new Image(path.toAbsolutePath() + "\\click2.png");
        colorPicker.setCursor(new ImageCursor(cursorClickImg));
        radioButton = new RadioButton();
        radioButton.setToggleGroup(tg);
        radioButton.setCursor(new ImageCursor(cursorClickImg));
        playerBox.getChildren().addAll(text, textField, colorPicker, radioButton);
        getChildren().add(playerBox);
    }

    public String getTextFieldText() {
        return textField.getText();
    }

    public Color getColorPickerValue() {
        return colorPicker.getValue();
    }

    public RadioButton getRadioButton() {
        return radioButton;
    }
}
