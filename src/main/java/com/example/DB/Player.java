package com.example.DB;

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.Random;

public class Player implements Serializable {
    private int id;// player's id
    public static int bonusPoint = 1; // keeps player's bonus point
    private String name; // player's name
    private int score = 0; // player's score
    private Color color; // player's color
    Random rand = new Random(); // random -> is used to generate random number

    public int getId() {
        return id;
    }

    // generates a random ID for a player
    public void setId() {
        int max = 999999;
        int min = 100000;
        this.id = rand.nextInt((max - min) + 1) + min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore() {
        this.score += bonusPoint;
    }

    public void setScore(int a) {
        this.score = a;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}