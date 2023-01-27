package com.example.DB;

import javafx.animation.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static com.example.DB.Board.*;
import static com.example.DB.Game.*;
import static com.example.DB.Player.bonusPoint;

public class Tile {
    private final File audioFile = new File(path.toAbsolutePath() + "\\click.wav"); // Tile captured audio
    private final Rectangle shape; // rectangle of tile
    private boolean status = false; // tile status(captured or not)
    private int amount = 0; // tile's inner amount -> if amount=4 then captured
    private final Text text = new Text(); // a text that shows bonus point amount when tile is captured

    // the tile's constructor
    public Tile(int recX, int recY) {
        // defines the rectangle
        shape = new Rectangle(recX, recY, tileWidth, tileWidth);
        shape.setFill(Color.TRANSPARENT);
        shape.setOpacity(0.85);
        // put the bonus point text in the middle of tile and styling
        text.setX(recX + tileWidth / 2 - 12);
        text.setY(recY + tileWidth / 2 + 12);
        text.setFont(Font.font("Arial", 21));
        text.setFill(Color.WHITE);
    }

    /*This method checks whether all the squares' sides
    are selected or not and takes the proper action*/
    public void setAmount() {
        this.amount++;
        if (this.amount == 4) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                clip.open(audioInput);
                clip.start();
                clip.loop(0);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
            bonusPoint++;
            updateGameStatus();
            isFinished();
        }
    }

    public void setColor(Color color) {
        shape.setFill(color);
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public Rectangle getShape() {
        return shape;
    }

    // this method updates the game status after capture
    public void updateGameStatus() {
        this.setStatus(true);
        currentPlayer.setScore();
        this.setColor(currentPlayer.getColor());
        //tile color transition
        FadeTransition ft1 = new FadeTransition(Duration.seconds(0.5), shape);
        ft1.setFromValue(0);
        ft1.setToValue(1);
        ft1.setCycleCount(1);
        ft1.setAutoReverse(true);
        ft1.play();
        text.setText("+ " + bonusPoint);
        root.getChildren().add(text);
        // text transition
        PauseTransition pt = new PauseTransition(Duration.seconds(1));
        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.7), text);
        tt.setToY(text.getTranslateY() - 40);
        tt.play();
        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.5));
        ft2.setFromValue(1);
        ft2.setToValue(0);
        ft2.setCycleCount(1);
        ft2.setAutoReverse(true);
        ft2.setOnFinished(evt -> {
            root.getChildren().remove(text);
        });
        SequentialTransition seqT = new SequentialTransition(text, tt, ft2, pt);
        seqT.setCycleCount(1);
        seqT.play();
        TileAnim();
        tileCounter++;
        // this loop updates the score board(score of players)
        for (int i = 0; i < players.length; i++) {
            if (currentPlayer == players[i]) {
                playerHBox.get(i).getChildren().clear();
                Text name = new Text(players[i].getName() + " :");
                Text score = new Text(Integer.toString(players[i].getScore()));
                name.setFont(Font.font("Harrington", 20));
                name.setFill(players[i].getColor());
                name.setOpacity(0.8);
                name.setStroke(Color.WHITE);
                name.setStrokeWidth(0.4);
                score.setFill(Color.web("#F4E2D4"));
                score.setFont(Font.font("Arial", 20));
                playerHBox.get(i).getChildren().addAll(name, score);
            }
        }
    }

    // check if the game is finished
    public static void isFinished() {
        // this loop defines the winner
        if (tileCounter == tilesAmount) {
            countDown.getTimeline().stop();
            int max = 0;
            byte kuchulu = 0;
            Player winner = new Player();
            for (Player player : players) {
                if (player != null) {
                    if (player.getScore() > max) {
                        max = player.getScore();
                        winner = player;
                    }
                }
            }
            kuchulu = 1;
            // this loop defines the draw
            for (int i = 0; i < players.length; i++) {
                for (int j = i + 1; j < players.length; j++) {
                    if (players[i] != null && players[j] != null) {
                        if (players[i].getScore() == players[j].getScore() && max == players[i].getScore()) {
                            kuchulu = 2;
                        }
                    }
                }
            }
            // find out if it's a win-lose or draw
            switch (kuchulu) {
                case 1 -> {
                    //System.out.println(winner.getName() +" is the winner");
                    countDown.getMessage().setText(winner.getName() + " is the winner");
                    countDown.getMessage().setVisible(true);
                }
                case 2 -> {
                    //System.out.println(" it's a draw");
                    countDown.getMessage().setText("It's a draw");
                    countDown.getMessage().setVisible(true);
                }
            }
        }
    }

    // tiles on captured animation
    private static void TileAnim() {
        for (int i = 0; i < boardH; i++) {
            for (int j = 0; j < boardW; j++) {
                if (board[i][j].getShape().getFill() == currentPlayer.getColor()) {
                    Rectangle temp = board[i][j].getShape();
                    PauseTransition pt = new PauseTransition(Duration.seconds(0.7));
                    FadeTransition ft3 = new FadeTransition(Duration.seconds(0.5));
                    ft3.setFromValue(0.85);
                    ft3.setToValue(0.55);
                    ft3.setCycleCount(1);
                    ft3.setAutoReverse(true);
                    FadeTransition ft4 = new FadeTransition(Duration.seconds(0.7));
                    ft4.setFromValue(0.55);
                    ft4.setToValue(0.85);
                    ft4.setCycleCount(1);
                    ft4.setAutoReverse(true);
                    SequentialTransition seqT2 = new SequentialTransition(temp, ft3, ft4, pt);
                    seqT2.setCycleCount(1);
                    seqT2.play();
                }
            }
        }
    }
}