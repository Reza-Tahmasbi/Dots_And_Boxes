package com.example.DB;

import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.DB.Game.*;
import static com.example.DB.TheLines.*;

public class Board {
    public static Tile[][] board; // A 2 dimensional array of tiles
    public static int tilesAmount; // the number of tiles made by getting surface's width and height
    public static int tileCounter; // the number of tiles which got captured by players
    public static TheLines[][] hoLines; // A 2 dimensional array of horizontal lines
    public static TheLines[][] veLines; // A 2 dimensional array of vertical lines
    public static ArrayList<HBox> playerHBox; // A list of horizontal boxes which includes players' name and score
    public static Circle circle = new Circle(4); // The switch turn circle-> a graphical interface to determine who's turn it is.
    public static int boardH; // An int where we store the height of our surface
    public static int boardW; // An int where we store the width of our surface
    private int fotRadius = 7; // Radius of circles in our surface
    public static CountDown countDown; // the countDown number(timer)
    HBox hBox = new HBox(10);// Main horizontal box where we add game's surface and score board

    // Board constructor(includes game's surface, score board and message box)
    public Board(int height, int width) {
        // create and add board background image
        Image img = new Image(path.toAbsolutePath() + "\\BgImage.png");
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(WIDTH);
        imgView.setFitHeight(HEIGHT);
        root.getChildren().add(imgView);
        // some default value declaration
        hBox.setTranslateX(10);
        hBox.setTranslateY(5);
        tileCounter = 0;
        playerHBox = new ArrayList<>();
        circle.setFill(Color.WHITE);
        circle.setOpacity(0.7);
        turnTransition(turn);
        boardH = height;
        boardW = width;
        gameSurface();
        ScoreBoard();
        MessageBoard();
        root.getChildren().add(hBox);
        countDown = new CountDown();
        root.getChildren().add(circle);
        tilesAmount = boardH * boardW;
        board = new Tile[boardH][boardW];
        hoLines = new TheLines[boardH + 1][boardW];
        veLines = new TheLines[boardH][boardW + 1];
        // drawing methods
        drawTiles();
        drawHoLines();
        drawVeLines();
        drawDots();
    }

    //Draw the [Tiles(squares)]
    private void drawTiles() {
        int recX = startX;
        int recY = startY;
        for (int i = 0; i < boardH; i++) {
            for (int j = 0; j < boardW; j++) {
                board[i][j] = new Tile(recX, recY);
                //board[i][j].setPosition(new int[]{recX,recY});
                root.getChildren().add(board[i][j].getShape());
                recX += tileWidth;
            }
            recX = startX;
            recY += tileWidth;
        }
    }

    //Draw the [Dots]
    private void drawDots() {
        int recX = startX;
        int recY = startY;
        for (int i = 0; i <= boardH; i++) {
            for (int j = 0; j <= boardW; j++) {
                Circle dot = new Circle(recX, recY, fotRadius);
                dot.setFill(Color.web("#F4E2D4"));
                root.getChildren().add(dot);
                recX += tileWidth;
            }
            recX = startX;
            recY += tileWidth;
        }
    }

    //Draw the [Horizontal Lines]
    private void drawHoLines() {
        int recX = startX;
        int recY = startY;
        for (int i = 0; i < boardH + 1; i++) {
            for (int j = 0; j < boardW; j++) {
                hoLines[i][j] = new TheLines(recX, recY, TheLines.type.horizontal);
                if (i == 0) {
                    hoLines[i][j].setRelatedTiles(new Tile[]{board[i][j], null});
                } else if (i == boardH) {
                    hoLines[i][j].setRelatedTiles(new Tile[]{board[i - 1][j], null});
                } else {
                    hoLines[i][j].setRelatedTiles(new Tile[]{board[i][j], board[i - 1][j]});
                }
                root.getChildren().add(hoLines[i][j].getMainLine());
                recX += tileWidth;
            }
            recX = startX;
            recY += tileWidth;
        }
    }

    //Draw the [Vertical Lines]
    private void drawVeLines() {
        int recX = startX;
        int recY = startY;
        for (int i = 0; i < boardH; i++) {
            for (int j = 0; j < boardW + 1; j++) {
                veLines[i][j] = new TheLines(recX, recY, TheLines.type.vertical);
                if (j == 0) {
                    veLines[i][j].setRelatedTiles(new Tile[]{board[i][j], null});
                } else if (j == boardW) {
                    veLines[i][j].setRelatedTiles(new Tile[]{board[i][j - 1], null});
                } else {
                    veLines[i][j].setRelatedTiles(new Tile[]{board[i][j], board[i][j - 1]});
                }
                root.getChildren().add(veLines[i][j].getMainLine());
                recX += tileWidth;
            }
            recX = startX;
            recY += tileWidth;
        }
    }

    // make and draw game surface(includes all lines,dots and tiles)
    private void gameSurface() {
        // adjust lines' width and stroke, depended on the width and height
        setTileWidth();
        // background rectangle
        Rectangle surface = new Rectangle(0, 10, 520, 530);
        // background rectangle styling
        DropShadow ds1 = new DropShadow();
        ds1.setOffsetY(1);
        ds1.setOffsetX(-1);
        ds1.setColor(Color.BLACK);
        surface.setStrokeWidth(1);
        surface.setStroke(Color.WHITE);
        surface.setEffect(new GaussianBlur(2));
        surface.setEffect(ds1);
        surface.setCache(true);
        surface.setFill(Color.rgb(61, 56, 94));
        surface.setOpacity(0.4);
        surface.setArcWidth(15);
        surface.setArcHeight(15);
        root.getChildren().add(surface);
        // these two variables declare the starting point of our lines, dots, and tiles.
        startX = 270 - ((boardW * tileWidth) / 2);
        startY = 270 - ((boardH * tileWidth) / 2);
        hBox.getChildren().add(surface);
    }

    // make and draw game score board(includes player's names,scores and grid(the grid includes some text-fields and buttons))
    private void ScoreBoard() {
        // declare a vertical box to add player's info(playerHBox values) vertically
        VBox vBox = new VBox(10);
        vBox.setTranslateX(40);
        vBox.setTranslateY(20);
        // background rectangle
        Rectangle scoreBoard = new Rectangle(550, 5, 330, 370);
        root.getChildren().add(scoreBoard);
        // box title
        Text title = new Text("Score Board");
        title.setFont(Font.font("Harrington", 20));
        title.setFill(Color.WHITE);
        vBox.getChildren().addAll(title);
        // this loop adds all existing players to our Vbox
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                    playerHBox.add(new HBox(5));
                Text name = new Text(players[i].getName() + " :");
                Text score = new Text(Integer.toString(players[i].getScore()));
                name.setFont(Font.font("Harrington", 20));
                name.setFill(players[i].getColor());
                name.setOpacity(0.8);
                name.setStroke(Color.WHITE);
                name.setStrokeWidth(0.4);
                score.setFont(Font.font("Arial", 20));
                score.setFill(Color.web("#F4E2D4"));
                playerHBox.get(i).getChildren().addAll(name, score);
                vBox.getChildren().add(playerHBox.get(i));
            }
        }
        // the grid declaration(which includes some text-fields and buttons)
        InfoGrid grid = new InfoGrid(vBox);
        // background rectangle styling
        DropShadow ds1 = new DropShadow();
        ds1.setOffsetY(1);
        ds1.setOffsetX(-1);
        ds1.setColor(Color.BLACK);
        scoreBoard.setStrokeWidth(1);
        scoreBoard.setStroke(Color.WHITE);
        scoreBoard.setEffect(new GaussianBlur(2));
        scoreBoard.setEffect(ds1);
        scoreBoard.setCache(true);
        scoreBoard.setFill(Color.rgb(61, 56, 94));
        scoreBoard.setOpacity(0.4);
        scoreBoard.setArcWidth(15);
        scoreBoard.setArcHeight(15);
        // here we add the Vbox to the main hBox
        hBox.getChildren().add(vBox);
    }

    // make and draw game message box(where we show messages)
    private void MessageBoard() {
        // background rectangle
        Rectangle messageBoard = new Rectangle(550, 380, 330, 150);
        root.getChildren().add(messageBoard);
        // box title
        Text title = new Text(560, 405, "Message Box");
        title.setFont(Font.font("Harrington", 20));
        title.setFill(Color.WHITE);
        root.getChildren().add(title);
        // background rectangle styling
        DropShadow ds1 = new DropShadow();
        ds1.setOffsetY(1);
        ds1.setOffsetX(-1);
        ds1.setColor(Color.BLACK);
        messageBoard.setStrokeWidth(1);
        messageBoard.setStroke(Color.WHITE);
        messageBoard.setEffect(new GaussianBlur(2));
        messageBoard.setEffect(ds1);
        messageBoard.setCache(true);
        messageBoard.setFill(Color.rgb(61, 56, 94));
        messageBoard.setOpacity(0.4);
        messageBoard.setArcWidth(15);
        messageBoard.setArcHeight(15);

    }

    // adjust lines' width and stroke, depended on the width and height
    private void setTileWidth() {
        if (boardH >= 7 || boardW >= 7) {
            tileWidth = 65;
        }
        if (boardH >= 8 || boardW >= 8) {
            tileWidth = 59;
            lineStroke = 5;
        }
        if (boardH >= 9 || boardW >= 9) {
            tileWidth = 53;
        }
        if (boardH >= 10 || boardW >= 10) {
            tileWidth = 48;
            fotRadius = 4;
        }
        if (boardH >= 11 || boardW >= 11) {
            tileWidth = 45;
            lineStroke = 4;
        }
        if (boardH >= 12 || boardW >= 12) {
            tileWidth = 40;
        }
        if (boardH >= 13 || boardW >= 13) {
            tileWidth = 37;

        }
        if (boardH >= 14 || boardW >= 14) {
            tileWidth = 35;
        }
        if (boardH >= 15 || boardW >= 15) {
            tileWidth = 33;
        }
    }
}
