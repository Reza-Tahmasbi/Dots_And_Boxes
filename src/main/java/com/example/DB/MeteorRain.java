package com.example.DB;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

import static com.example.DB.Game.*;

public class MeteorRain {
    public static Timeline MenuTimeline; // a timeline for background rain
    static double posX = 0; // default starting X value
    static byte count = 0; // a variable for adjusting rain
    static Image img = new Image(path.toAbsolutePath() + "\\Mountains.png"); // mountains image
    static ImageView imgView = new ImageView(img);
    static Random rand = new Random(); // random is used to generate random Y
    public static Group mRoot = new Group(); // will be added menuRoot and includes meteor components

    // meteorRain constructor
    public static void meteorRain() {
        imgView.setFitWidth(WIDTH);
        imgView.setFitHeight(HEIGHT);
        menuRoot.getChildren().addAll(mRoot, imgView);
        // declare menu timeline
        MenuTimeline = new Timeline();
        MenuTimeline.setCycleCount(Timeline.INDEFINITE);
        MenuTimeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(0.3),
                        event -> {
                            count++;
                            posX = rand.nextInt((WIDTH + 200) + 1) - 200;
                            MeteorComponent meteorComponent = new MeteorComponent(posX, -30);
                            mRoot.getChildren().add(meteorComponent);
                            Action(meteorComponent);
                            if (count == 3) {
                                posX = rand.nextInt((WIDTH + 200) + 1) - 200;
                                MeteorComponent meteorComponent2 = new MeteorComponent(posX, -30);
                                mRoot.getChildren().add(meteorComponent2);
                                Action(meteorComponent);
                            }
                            if (count == 5) {
                                for (int i = 0; i < 2; i++) {
                                    posX = rand.nextInt((WIDTH + 200) + 1) - 200;
                                    MeteorComponent meteorComponent2 = new MeteorComponent(posX, -30);
                                    mRoot.getChildren().add(meteorComponent2);
                                    Action(meteorComponent);
                                }
                                count = 0;
                            }
                        }));
        MenuTimeline.playFromStart();
    }

    // meteor falling transition
    private static void Action(MeteorComponent meteorComponent) {
        FadeTransition ftIn = new FadeTransition(Duration.seconds(0.5));
        ftIn.setFromValue(0);
        ftIn.setToValue(0.6);
        ftIn.setAutoReverse(true);
        ScaleTransition tt1 = new ScaleTransition(Duration.seconds(2.5));
        tt1.setFromX(0.5);
        tt1.setToX(1);
        tt1.setFromY(0.5);
        tt1.setToY(1);
        TranslateTransition tt = new TranslateTransition(Duration.seconds(3), meteorComponent);
        tt.setToX(meteorComponent.getTranslateX() + HEIGHT + 30);
        tt.setToY(meteorComponent.getTranslateY() + HEIGHT + 30);
        tt.play();
        tt.setOnFinished(evt -> {
            menuRoot.getChildren().remove(meteorComponent);
        });
        SequentialTransition seqT2 = new SequentialTransition(meteorComponent, ftIn, tt1, tt);
        seqT2.setCycleCount(1);
        seqT2.play();
    }
}
