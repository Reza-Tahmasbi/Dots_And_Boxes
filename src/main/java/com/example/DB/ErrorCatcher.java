package com.example.DB;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import static com.example.DB.Game.surfaceWidth;
import static com.example.DB.Game.surfaceHeight;
import static com.example.DB.Game.players;

public class ErrorCatcher {
    private static boolean result; // the result value, defines whether we have any errors on entry-scene or not

    public static Boolean CatchErrors(Label errorLabel) {
        // set result's default value to true and if we detect any errors, it would become false. so the  user can't play
        result = true;
        CheckMapSizeOutOfRange(errorLabel);
        CheckColors(errorLabel);
        CheckNames(errorLabel);
        CheckEmptiness(errorLabel);
        return result;
    }

    // check if TextFields are empty
    public static void CheckEmptiness(Label errorLabel) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                if (players[i].getName().equals("")) {
                    errorLabel.setText("Player" + (i + 1) + "'s name is empty");
                    result = false;
                    break;
                }
            }
        }
    }

    // check if surface width and height are standard[from 2 to 15]
    public static void CheckMapSizeOutOfRange(Label errorLabel) {
        if (surfaceWidth > 15 || surfaceHeight > 15) {
            errorLabel.setText("The Map width or height can't be more than 15");
            result = false;
        }
        if (surfaceWidth <= 1 || surfaceHeight <= 1) {
            errorLabel.setText("The Map width or height can't be less than 2");
            result = false;
        }
    }

    // check if names are the same
    public static void CheckNames(Label errorLabel) {
        for (int i = 0; i < players.length; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (players[i] != null && players[j] != null) {
                    if (players[i].getName().equals(players[j].getName())) {
                        errorLabel.setText("Names can not be the same");
                        result = false;
                        break;
                    }
                }
            }
        }
    }

    // check if Colors are the same
    public static void CheckColors(Label errorLabel) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null)
                break;
            if (players[i].getColor() == Color.WHITE || players[i].getColor() == Color.BLACK) {
                result = false;
                errorLabel.setText("Colors can not be White or Black");
                break;
            }
            for (int j = i + 1; j < players.length; j++) {
                if (players[j] != null) {
                    if (players[i].getColor() == (players[j].getColor())) {
                        errorLabel.setText("Colors can not be the same");
                        result = false;
                        break;
                    }
                }
            }
        }
    }
}
