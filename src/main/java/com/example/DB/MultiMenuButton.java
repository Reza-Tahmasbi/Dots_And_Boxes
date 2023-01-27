package com.example.DB;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static com.example.DB.Game.path;

public class MultiMenuButton extends StackPane {
    // MultiMenuButton constructor -> a customized button for multiplayer menu, include a rectangle for background and a text
    public MultiMenuButton(String name) {
        HBox hBox = new HBox(10);
        Text text = new Text(name);
        text.setFont(Font.font("Harrington", 16));
        text.setFill(Color.WHITE);
        hBox.setPadding(new Insets(5, 5, 5, 20));
        hBox.getChildren().add(text);
        Rectangle bg = new Rectangle(170, 30);
        bg.setOpacity(0.5);
        bg.setFill(Color.TRANSPARENT);
        bg.setStrokeWidth(1);
        bg.setStroke(Color.WHITE);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(bg, hBox);
        Image cursorClickImg = new Image(path.toAbsolutePath() + "\\click2.png");
        setCursor(new ImageCursor(cursorClickImg));
        DropShadow drop = new DropShadow(10, Color.WHITE);
        drop.setInput(new Glow());
        setOnMouseEntered(event -> {
            setEffect(drop);
        });
        setOnMouseExited(event -> {
            setEffect(null);
        });
    }

}
