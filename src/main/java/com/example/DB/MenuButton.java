package com.example.DB;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static com.example.DB.Game.path;

public class MenuButton extends StackPane {
    // MenuButton constructor -> a customized button for menus, include a rectangle for background and a text
    public MenuButton(String name) {
        HBox hBox = new HBox(10);
        Text text = new Text(name);
        text.setFont(Font.font("Harrington", 19));
        text.setFill(Color.rgb(249, 239, 231));
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.getChildren().add(text);
        Rectangle bg = new Rectangle(220, 30);
        bg.setOpacity(0.8);
        bg.setFill(Color.rgb(61, 56, 94));
        bg.setEffect(new GaussianBlur(3.5));
        setAlignment(Pos.CENTER_LEFT);
        setRotate(-0.6);
        getChildren().addAll(bg, hBox);
        Image cursorClickImg = new Image(path.toAbsolutePath() + "\\click2.png");
        setCursor(new ImageCursor(cursorClickImg));
        setOnMouseEntered(event -> {
            bg.setTranslateX(12);
            text.setTranslateX(12);
            bg.setFill(Color.rgb(249, 239, 231));
            text.setFill(Color.rgb(61, 56, 94));
            text.setFont(Font.font("Harrington", FontWeight.BOLD, 19));
        });
        setOnMouseExited(event -> {
            bg.setTranslateX(0);
            text.setTranslateX(0);
            bg.setFill(Color.rgb(61, 56, 94));
            text.setFill(Color.rgb(249, 239, 231));
            text.setFont(Font.font("Harrington", FontWeight.MEDIUM, 19));
        });
        DropShadow drop = new DropShadow(30, Color.rgb(249, 239, 231));
        drop.setInput(new Glow());
        setOnMousePressed(event -> {
            setEffect(drop);
        });
        setOnMouseReleased(event -> {
            setEffect(null);
        });
    }

}
