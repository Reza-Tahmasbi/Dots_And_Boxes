package com.example.DB;

import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MeteorComponent extends Parent {
    // Meteor component constructor -> includes 21 circles with shadow merged together
    public MeteorComponent(double posX, double posY) {
        int shadow = 19;
        for (int i = 0; i < 21; i++) {
            Circle circle = new Circle(posX, posY, 1);
            circle.setFill(Color.WHITE);
            circle.setStrokeWidth(0);
            DropShadow drop = new DropShadow(shadow, Color.WHITE);
            drop.setInput(new Lighting());
            circle.setEffect(drop);
            posX += 1;
            posY += 1;
            shadow -= 1;
            getChildren().add(circle);
        }
    }

}
