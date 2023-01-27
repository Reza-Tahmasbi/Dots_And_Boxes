package com.example.DB;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.example.DB.Game.currentPlayer;
import static com.example.DB.Game.root;
import static com.example.DB.Player.bonusPoint;
import static com.example.DB.TheLines.switchTurn;

public class CountDown {
    public static final int STARTTIME = 10; // The time of each turn (each player's time)
    private final Timeline timeline; // The timeline where counter change
    private final Text timerLabel = new Text(); // this Text shows the counter
    private final Text message = new Text(currentPlayer.getName() + " is out of time"); // This text shows all the messages' in message box
    public static int timeSeconds; // int value of our counter

    public CountDown() {
        // these texts will be shown in message box
        timerLabel.setX(600);
        timerLabel.setY(465);
        message.setX(640);
        message.setY(460);
        // texts styling
        message.setFont(Font.font("Arial", 20));
        timerLabel.setFont(Font.font("Arial", 35));
        message.setVisible(false);
        message.setFill(Color.WHITE);
        timerLabel.setFill(Color.WHITE);
        root.getChildren().addAll(timerLabel, message);
        // set default value to int value
        timeSeconds = STARTTIME;
        // update timerLabel
        timerLabel.setText(Integer.toString(timeSeconds));
        // declare the timeline
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            message.setVisible(false);
                            timeSeconds--;
                            // update timerLabel
                            timerLabel.setText(Integer.toString(timeSeconds));
                            if (timeSeconds == 0) {
                                bonusPoint = 0;
                                message.setText(currentPlayer.getName() + " is out of time");
                                message.setVisible(true);
                                switchTurn();
                            }
                        }));
        timeline.playFromStart();
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public Text getMessage() {
        return message;
    }
}
