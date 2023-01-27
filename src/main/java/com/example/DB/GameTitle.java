package com.example.DB;

import javafx.animation.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameTitle extends Pane {

    // GameTitle constructor -> includes the big Dots & Boxes text in the menu page, and it's underline
    public GameTitle(String name) {
        // puts a gap between each letter
        String spread = "";
        for (char c : name.toCharArray()) {
            spread += c + " ";
        }
        // declare the text and some styling
        Text text = new Text(spread);
        text.setFont(Font.font("Harrington".toUpperCase(), 38));
        text.setFill(Color.rgb(249, 239, 231));
        text.setX(453);
        text.setY(220);
        text.setEffect(new DropShadow(50, Color.BLACK));
        // declare the underline
        Line line = new Line(430, 235, 800, 235);
        getChildren().addAll(line);
        line.setStrokeWidth(3.5);
        line.setStroke(Color.rgb(249, 239, 231));

        DropShadow drop = new DropShadow(50, Color.rgb(249, 239, 231));
        drop.setInput(new Glow());
        line.setEffect(drop);
        // underline sequential transition
        PauseTransition pt = new PauseTransition(Duration.seconds(1));
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5));
        ft.setFromValue(0.8);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);

        ScaleTransition tt1 = new ScaleTransition(Duration.seconds(3));
        tt1.setFromX(0);
        tt1.setToX(1);
        tt1.setCycleCount(1);
        tt1.setAutoReverse(true);

        SequentialTransition seqT = new SequentialTransition(line, tt1, ft, pt);
        seqT.setCycleCount(Animation.INDEFINITE);
        seqT.play();
        getChildren().addAll(text);

    }
}
