package com.example.DB;

import javafx.animation.StrokeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static com.example.DB.CountDown.STARTTIME;
import static com.example.DB.CountDown.timeSeconds;
import static com.example.DB.Player.bonusPoint;
import static com.example.DB.Board.circle;
import static com.example.DB.Game.*;
import static com.example.DB.Game.turn;

public class TheLines {
    private final File audioFile = new File(path.toAbsolutePath() + "\\click2.wav"); // Line captured audio
    private Tile[] relatedTiles = new Tile[]{null, null}; // array of related tile
    private final Line mainLine; // line
    public static int lineStroke = 6; // stroke of line
    public boolean status = false; // status of line

    public enum type {vertical, horizontal} // type of line vertical or horizontal

    //The constructor defines a line and it's captured action
    public TheLines(int lineX, int lineY, type t) {
        // find out the line's type
        if (t == type.horizontal)
            mainLine = new Line(lineX, lineY, lineX + tileWidth, lineY);
        else
            mainLine = new Line(lineX, lineY, lineX, lineY + tileWidth);
        // line stylinng
        mainLine.setStrokeWidth(lineStroke);
        mainLine.setStroke(Color.web("#BABABA"));
        mainLine.setCursor(Cursor.HAND);
        // mouse entered transition
        mainLine.setOnMouseEntered(mouseEvent -> {
            StrokeTransition ft1 = new StrokeTransition(Duration.seconds(0.15), (Line) mouseEvent.getSource(), Color.web("#cccccc"), currentPlayer.getColor());
            ft1.setCycleCount(1);
            ft1.setAutoReverse(false);
            ft1.play();
        });
        // mouse exited translation
        mainLine.setOnMouseExited(mouseEvent -> {
            //((Line)mouseEvent.getSource()).setStroke(Color.web("#cccccc"));
            StrokeTransition ft2 = new StrokeTransition(Duration.seconds(0.15), (Line) mouseEvent.getSource(), currentPlayer.getColor(), Color.web("#cccccc"));
            ft2.setCycleCount(1);
            ft2.setAutoReverse(false);
            ft2.play();
        });
        // mouse clicked
        mainLine.setOnMouseClicked(mouseEvent -> {
            this.setStatus(true);
            // changes the color when it got clicked
            ((Line) mouseEvent.getSource()).setStroke(Color.web("#454258"));
            ((Line) mouseEvent.getSource()).setCursor(Cursor.DEFAULT);
            // sound effect
            try {
                // play the captured audio
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                clip.open(audioInput);
                clip.start();
                clip.loop(0);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            // reset time
            timeSeconds = STARTTIME;
            // blocks all the events when it got clicked
            EventHandler<MouseEvent> handler = MouseEvent::consume;
            ((Line) mouseEvent.getSource()).addEventFilter(MouseEvent.ANY, handler);
            // checks the related tiles when it got clicked
            relatedTiles[0].setAmount();
            if (relatedTiles[1] != null) {
                relatedTiles[1].setAmount();
            }
            // check if we can switch turn between players
            if (!relatedTiles[0].getStatus() && relatedTiles[1] != null) {
                if (!relatedTiles[1].getStatus()) {
                    bonusPoint = 0;
                    switchTurn();
                }
            }
            if (!relatedTiles[0].getStatus() && relatedTiles[1] == null) {
                bonusPoint = 0;
                switchTurn();
            }
        });
    }

    public void setRelatedTiles(Tile[] relatedTiles) {
        this.relatedTiles = relatedTiles;
    }

    public Line getMainLine() {
        return mainLine;
    }

    // switch turn between players
    public static void switchTurn() {
        switch (turn) {
            case 0:
                turn = 1;
                break;
            case 1:
                if (players[2] != null)
                    turn = 2;
                else
                    turn = 0;
                break;
            case 2:
                if (players[3] != null)
                    turn = 3;
                else
                    turn = 0;
                break;
            case 3:
                if (players[4] != null)
                    turn = 4;
                else
                    turn = 0;
                break;
            case 4:
                if (players[5] != null)
                    turn = 5;
                else
                    turn = 0;
                break;
            case 5:
                turn = 0;
                break;
        }
        turnTransition(turn);
        currentPlayer = players[turn];
        timeSeconds = STARTTIME;
    }

    // switch turn transition -> used by circle in declared in board
    public static void turnTransition(int turn) {
        circle.setTranslateX(570);
        circle.setFill(players[turn].getColor());
        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), circle);
        switch (turn) {
            case 0 -> tt1.setToY(71);
            case 1 -> tt1.setToY(106);
            case 2 -> tt1.setToY(140);
            case 3 -> tt1.setToY(176);
            case 4 -> tt1.setToY(212);
            case 5 -> tt1.setToY(248);
        }
        tt1.play();
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
