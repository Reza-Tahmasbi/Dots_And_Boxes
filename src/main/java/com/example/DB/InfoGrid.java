package com.example.DB;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

import static com.example.DB.Board.*;
import static com.example.DB.Game.*;
import static com.example.DB.GameMenu.starter;
import static com.example.DB.Player.bonusPoint;
import static com.example.DB.TheLines.switchTurn;

public class InfoGrid extends Parent {
    public InfoGrid(VBox vBox) {
        // creating a GridPane container
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 0, 10));
        grid.setHgap(5); // horizontal gap in pixels
        grid.setVgap(5); // vertical gap in pixels
        // defining the Console Commands text field
        final TextField consoleTextField = new TextField();
        consoleTextField.setPromptText("Console commands here");
        GridPane.setConstraints(consoleTextField, 0, 0);
        grid.getChildren().add(consoleTextField);
        consoleTextField.setStyle("-fx-focus-color:rgba(61,56,94,0.6);");
        // defining the File name text field
        final TextField saveTextField = new TextField();
        saveTextField.setPromptText("File name");
        GridPane.setConstraints(saveTextField, 0, 1);
        grid.getChildren().add(saveTextField);
        saveTextField.setStyle("-fx-focus-color:rgba(61,56,94,0.6);");
        // defining the reset-game button
        Button resetBtn = new Button("           Reset Game           ");
        resetBtn.setCursor(Cursor.HAND);
        GridPane.setConstraints(resetBtn, 0, 2);
        grid.getChildren().add(resetBtn);
        resetBtn.setOnAction(event -> {
            for (Player player : players) {
                if (player != null) {
                    player.setScore(0);
                }
            }
            turn = starter;
            countDown.getTimeline().stop();
            currentPlayer = players[turn];
            root.getChildren().clear();
            vBox.getChildren().clear();
            playerHBox = null;
            b = new Board(surfaceHeight, surfaceWidth);
        });
        // defining the Submit button
        Button submitBtn = new Button(" Submit ");
        submitBtn.setCursor(Cursor.HAND);
        GridPane.setConstraints(submitBtn, 1, 0);
        grid.getChildren().add(submitBtn);
        // console commands declared here
        submitBtn.setOnAction(event -> {
            switch (consoleTextField.getText()) {
                case "console-reset":
                    resetBtn.fire();
                    break;
                case "console-exit":
                    Platform.exit();
                    break;
                case "console-cheat-next-turn":
                    bonusPoint = 0;
                    switchTurn();
                    break;
                case "console-end":
                    tileCounter = tilesAmount;
                    Tile.isFinished();
                    for (TheLines[] row : hoLines)
                        for (TheLines line : row) {
                            EventHandler<MouseEvent> handler = MouseEvent::consume;
                            line.getMainLine().addEventFilter(MouseEvent.ANY, handler);
                            line.getMainLine().setCursor(Cursor.DEFAULT);
                        }
                    for (TheLines[] row : veLines)
                        for (TheLines line : row) {
                            EventHandler<MouseEvent> handler = MouseEvent::consume;
                            line.getMainLine().addEventFilter(MouseEvent.ANY, handler);
                            line.getMainLine().setCursor(Cursor.DEFAULT);
                        }
                    break;
                case "console-reset-time":
                    break;
                case "console.undo":
                    break;
                case "console-increase-surface-size":
                    break;
            }
            consoleTextField.setText("");
        });
        // if Enter key is pressed
        consoleTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitBtn.fire();
            }
        });
        // defining the save button(saves the board in a file)
        Button saveBtn = new Button("   Save   ");
        saveBtn.setCursor(Cursor.HAND);
        GridPane.setConstraints(saveBtn, 1, 1);
        grid.getChildren().add(saveBtn);
        saveBtn.setOnAction(event -> {
            SaveGame.makeDirectory();
            try {
                SaveGame.save(saveTextField.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveTextField.setText("");
        });
        vBox.getChildren().add(grid);
    }
}
