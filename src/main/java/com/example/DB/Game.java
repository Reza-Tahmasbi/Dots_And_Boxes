/*
   Dots and Boxes project
   Java OpenJDK version 17.0.2
   UI implemented by javafx
   Created by Reza Tahmasbi Birgani
   1401/03/10
*/
package com.example.DB;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Game extends Application {
    public static Board b; // stores board :)
    public static Player[] players = new Player[]{new Player(), new Player(), null, null, null, null}; // An array of players, maximum 6
    public static Player currentPlayer = players[0]; // stores the current player,(the player whose turn it is)
    public static int turn = 0; // stores turn number[range from 0 to 5, depended on number of players]
    public static final int WIDTH = 900; // the stage width
    public static final int HEIGHT = 540; // the stage height
    public static int startX; //starting point is the point where all lines, dots, and tiles are drawn
    public static int startY; //starting point is the point where all lines, dots, and tiles are drawn
    public static int tileWidth = 70; // default value of tile's width
    public static int surfaceWidth = 2; // default value of surface width
    public static int surfaceHeight = 2; // default value of surface height
    public static Group root = new Group(); // includes all surface box,score board and message box
    public static Group menuRoot = new Group(); // includes all menus and menu buttons
    public static Path path = Paths.get("src"); // includes the path of our src folder

    @Override
    public void start(Stage stage) {
        //Build and Draw the entry scene(MainMenu scene)
        // make directories
        File directory = SaveGame.makeDirectory();
        SaveGame.fillSavedFiles(directory);
        // make the menu scene
        Scene scene2 = new Scene(menuRoot, WIDTH, HEIGHT);
        // set stage title
        stage.setTitle("Dots & Boxes!");
        // make and draw background image of menu scene
        Image img = new Image(path.toAbsolutePath() + "\\BgImage.png");
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(WIDTH);
        imgView.setFitHeight(HEIGHT);
        // set menu scene mouse cursor
        Image cursorImg = new Image(path.toAbsolutePath() + "\\pointer.png");
        scene2.setCursor(new ImageCursor(cursorImg));
        // make a game menu
        GameMenu gameMenu = new GameMenu(stage);
        menuRoot.getChildren().addAll(imgView);
        // start the meteor rain timeline
        MeteorRain.meteorRain();
        menuRoot.getChildren().add(gameMenu);
        // stage styling
        stage.setScene(scene2);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
    }

    public static void main(String[] args) {
        // play the background music
        File audioFile = new File(path.toAbsolutePath() + "\\music3.wav");
        try {
            Clip clip = AudioSystem.getClip();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        launch();
    }
}