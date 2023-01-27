package com.example.DB;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static com.example.DB.Board.*;
import static com.example.DB.Game.*;

public class GameMenu extends Parent {
    public static final int offset = 1800;// It's the menu changer pixel value
    private static final TextField textFieldHeight = new TextField(); // A text-field that gets the surface height
    private static final TextField textFieldWidth = new TextField();// A text-field that gets the surface width
    public static int starter; // Keeps the starting player index -> used by reset-button in grid
    public MenuButton btnMultiplayer = new MenuButton("Multiplayer"); // multiplayer button in main menu
    public MenuButton btnLoad = new MenuButton("Load"); // Load button in main menu
    public MenuButton btnTutorial = new MenuButton("Tutorial"); // Tutorial button in main menu
    public MenuButton btnCredits = new MenuButton("Credits"); // Credits button in main menu
    public MenuButton btnExit = new MenuButton("Exit"); // Exit button in main menu
    public static ArrayList<String> savedFiles = new ArrayList(); // A list of saved files' names
    Button btnDoLoad = new Button("Load"); // Button that fires loading...
    ComboBox comboBox; // combo box filled width all existing files' names
    VBox menu0 = new VBox(10); // the main menu
    VBox menu1 = new VBox(10); // the loading menu
    VBox menu2 = new VBox(20); // the credits menu
    VBox menu3 = new VBox(15); // the multiplayer menu
    VBox menu4 = new VBox(20); // the Tutorial menu

    // Game menu constructor
    public GameMenu(Stage stage) {
        // some menus styling
        menu0.setTranslateX(80);
        menu0.setTranslateY(100);
        menu1.setTranslateX(offset);
        menu1.setTranslateY(100);
        menu2.setTranslateX(offset);
        menu2.setTranslateY(100);
        menu3.setTranslateX(offset);
        menu4.setTranslateX(offset);
        menu4.setTranslateY(100);
        // make the game title -> appears in menu scene
        GameTitle title = new GameTitle("Dots & Boxes");
        getChildren().add(title);
        // Multiplayer button onClick
        btnMultiplayer.setOnMouseClicked(event -> {
            menu3.getChildren().clear();
            MultiplayerMenu(menu3, stage);
            getChildren().add(menu3);
            BtnBack(menu0, menu3);
            ChangeMenuTransition(menu0, menu3);
        });
        // Load button onClick
        btnLoad.setOnMouseClicked(event -> {
            menu1.getChildren().clear();
            comboBox = new ComboBox(FXCollections.observableArrayList(savedFiles));
            comboBox.getSelectionModel().selectFirst();
            Image cursorClickImg = new Image(path.toAbsolutePath() + "\\click2.png");
            comboBox.setCursor(new ImageCursor(cursorClickImg));
            btnDoLoad.setCursor(new ImageCursor(cursorClickImg));
            HBox hBox = new HBox(5);
            hBox.getChildren().addAll(comboBox, btnDoLoad);
            menu1.getChildren().add(hBox);
            getChildren().add(menu1);
            BtnBack(menu0, menu1);
            ChangeMenuTransition(menu0, menu1);
        });
        // DoLoad button onClick
            btnDoLoad.setOnAction(event -> {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(path.toAbsolutePath() +
                        "/Saves/" + comboBox.getSelectionModel().getSelectedItem()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String line = null; // <-- read whole line
            try {
//                while (line!="l_end"){
                //line = in.readLine();
                FileInputStream fi = new FileInputStream(path.toAbsolutePath() +
                        "/Saves/" + comboBox.getSelectionModel().getSelectedItem());
                ObjectInputStream oi = new ObjectInputStream(fi);
                turn = fi.read();
                b = (Board) oi.readObject();
                while ((line = in.readLine()) != null) {
                    for (Player player : players)
                        player = (Player) oi.readObject();
                }

                System.out.println(turn);
                System.out.println(tileCounter);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
                StringTokenizer tk = new StringTokenizer(line);
            System.out.println(tk.nextToken()); // <-- read single word on line and parse to int
        });
        // Tutorial button onClick
        btnTutorial.setOnMouseClicked(event -> {
            menu4.getChildren().clear();
            Text txtTitle = new Text("Just play and Learn!");
            txtTitle.setRotate(-0.6);
            DropShadow drop = new DropShadow(20, Color.rgb(249, 239, 231));
            drop.setInput(new Glow());
            txtTitle.setEffect(drop);
            txtTitle.setFont(Font.font("Harrington", 20));
            txtTitle.setFill(Color.WHITE);
            menu4.getChildren().addAll(txtTitle);
            BtnBack(menu0, menu4);
            getChildren().add(menu4);
            ChangeMenuTransition(menu0, menu4);
        });
        // Credits button onClick
        btnCredits.setOnMouseClicked(event -> {
            menu2.getChildren().clear();
            Text txtCreditTitle = new Text("Created with Love and Passion!");
            DropShadow drop = new DropShadow(20, Color.rgb(249, 239, 231));
            drop.setInput(new Glow());
            txtCreditTitle.setEffect(drop);
            Text txtCreditMem = new Text("Reza Tahmasbi Birgani\n\nAli Rahimi\n\nSetayesh Ezati");
            txtCreditTitle.setFont(Font.font("Harrington", 20));
            txtCreditMem.setFont(Font.font("Harrington", 16));
            txtCreditTitle.setFill(Color.WHITE);
            txtCreditMem.setFill(Color.WHITE);
            menu2.getChildren().addAll(txtCreditTitle, txtCreditMem);
            BtnBack(menu0, menu2);
            getChildren().add(menu2);
            ChangeMenuTransition(menu0, menu2);
        });
        // Exit button onClick
        btnExit.setOnMouseClicked(event -> {
            Platform.exit();
        });
        // default menu
        menu0.getChildren().addAll(btnMultiplayer, btnLoad, btnTutorial, btnCredits, btnExit);
        // a rectangle that covers the background image with oopacity of 0.2
        Rectangle bg = new Rectangle(WIDTH, HEIGHT);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.2);
        getChildren().addAll(bg, menu0);
        //opening transition of main menu
        MainMenuTransition();
    }

    // transition of main menu
    public void MainMenuTransition() {
        PauseTransition pt = new PauseTransition(Duration.seconds(0.7));
        FadeTransition ft1 = new FadeTransition(Duration.seconds(0.1), btnMultiplayer);
        FadeTransition ft2 = new FadeTransition(Duration.seconds(0.1), btnLoad);
        FadeTransition ft3 = new FadeTransition(Duration.seconds(0.1), btnTutorial);
        FadeTransition ft4 = new FadeTransition(Duration.seconds(0.1), btnCredits);
        FadeTransition ft5 = new FadeTransition(Duration.seconds(0.1), btnExit);
        TransitionSettings(ft1);
        TransitionSettings(ft2);
        TransitionSettings(ft3);
        TransitionSettings(ft4);
        TransitionSettings(ft5);
        SequentialTransition seqT = new SequentialTransition(pt, ft1, ft2, ft3, ft4, ft5);
        seqT.play();
    }

    // used in MainMenuTransition method
    public void TransitionSettings(FadeTransition ft) {
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);
    }

    // transition of main menu to other menus
    public void ChangeMenuTransition(VBox menu0, VBox menu1) {
        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.7), menu0);
        tt1.setToX(menu0.getTranslateX() - offset);
        TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.7), menu1);
        tt2.setToX(menu0.getTranslateX());
        tt1.play();
        tt2.play();
        tt1.setOnFinished(evt -> {
            getChildren().remove(menu0);
        });
    }

    // declaration of back button-> fires to get back to the main menu
    public void BtnBack(VBox menu0, VBox menu1) {
        MenuButton btnBack = new MenuButton("Back");
        menu1.getChildren().add(btnBack);
        btnBack.setOnMouseClicked(event -> {
            players[2] = null;
            players[3] = null;
            players[4] = null;
            players[5] = null;
            getChildren().add(menu0);
            TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.7), menu1);
            tt1.setToX(menu1.getTranslateX() + GameMenu.offset);
            TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.7), menu0);
            tt2.setToX(menu1.getTranslateX());
            tt1.play();
            tt2.play();
            tt1.setOnFinished(evt -> {
                getChildren().remove(menu1);
            });
            //MainMenuTransition();
        });
    }

    // multiplayer menu constructor(includes text-fields, radio buttons and buttons)
    public void MultiplayerMenu(VBox box, Stage stage) {
        ArrayList<PlayerInfoBox> playersInfoList = new ArrayList<>();
        MultiMenuButton startBtn = new MultiMenuButton("      Let's Play ");
        Label errorLabel = new Label("");
        errorLabel.setTextFill(Color.RED);
        for (int i = 0; i < 2; i++) {
            if (players[i] != null) {
                playersInfoList.add(new PlayerInfoBox(i));
            }
        }
        Text MenuTitleText = new Text("Player's Info");
        MenuTitleText.setFill(Color.WHITE);
        MenuTitleText.setFont(Font.font("Harrington", 17));
        playersInfoList.get(0).getRadioButton().setSelected(true);
        MultiMenuButton newPlayerBtn = new MultiMenuButton("      New Player");
        newPlayerBtn.setOnMouseClicked(event -> {
            for (int i = 0; i < players.length; i++) {
                if (players[i] == null) {
                    playersInfoList.add(new PlayerInfoBox(i));
                    players[i] = new Player();
                    box.getChildren().clear();
                    addPIBoxes(playersInfoList, box, MenuTitleText, errorLabel, startBtn, newPlayerBtn);
                    BtnBack(menu0, menu3);
                    break;
                }
            }
        });
        addPIBoxes(playersInfoList, box, MenuTitleText, errorLabel, startBtn, newPlayerBtn);
        box.setPadding(new Insets(50));
        startBtn.setOnMouseClicked(e -> {
            setPlayersData(playersInfoList);
            setMapSize();
            if (ErrorCatcher.CatchErrors(errorLabel)) {
                for (Player player : players) {
                    if (player != null)
                        player.setId();
                }
                CheckRadioButtons(playersInfoList);
                setPlayersIds();
                b = new Board(surfaceHeight, surfaceWidth);
                stage.setScene(new Scene(root, WIDTH, HEIGHT));
            }
        });
    }

    // set player's name & color
    public static void setPlayersData(ArrayList<PlayerInfoBox> playersInfoList) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                players[i].setName(playersInfoList.get(i).getTextFieldText());
                players[i].setColor(playersInfoList.get(i).getColorPickerValue());
            }
        }
    }

    //set Map size
    public static void setMapSize() {
        try {
            surfaceWidth = Integer.parseInt(textFieldWidth.getText());
            surfaceHeight = Integer.parseInt(textFieldHeight.getText());
        } catch (Exception ignored) {
        }
    }

    //generate random id's for players
    public static void setPlayersIds() {
        for (int i = 0; i < players.length; i++) {
            for (int j = i + 1; j < players.length; j++) {
                if (players[i] != null && players[j] != null) {
                    if (players[i].getId() == players[j].getId())
                        setPlayersIds();
                }
            }
        }
    }

    // finds the selected radiobutton and switch the turn
    public static void CheckRadioButtons(ArrayList<PlayerInfoBox> playersInfoList) {
        for (int i = 0; i < playersInfoList.size(); i++) {
            if (playersInfoList.get(i).getRadioButton().isSelected()) {
                turn = i;
                //starter = i;
                System.out.println(i);
                currentPlayer = players[i];
            }
        }
    }

    // add our player's information-boxes such as text-field, color-picker, labels and radio-button
    public static void addPIBoxes(ArrayList<PlayerInfoBox> playersInfoList, VBox box, Text MenuTitleText, Label errorLabel, MultiMenuButton button, MultiMenuButton newPlayerBtn) {
        box.getChildren().add(MenuTitleText);
        for (PlayerInfoBox playerInfoBox : playersInfoList) {
            box.getChildren().add(playerInfoBox);
        }
        if (players[5] == null)
            box.getChildren().addAll(newPlayerBtn);
        box.getChildren().add(surfaceMapSize());
        box.getChildren().addAll(button);
        box.getChildren().addAll(errorLabel);
    }

    // A horizontal box that includes surface height & width text-fields
    public static HBox surfaceMapSize() {
        HBox hBox = new HBox(5);
        Text text1 = new Text("Map size ");
        text1.setFill(Color.WHITE);
        Text text2 = new Text(" X ");
        text2.setFill(Color.WHITE);
        text1.setFont(Font.font("Harrington", 15));
        text2.setFont(Font.font("Harrington", 15));
        textFieldHeight.setStyle("-fx-background-color: transparent;" +
                " -fx-border-width:0.5;" +
                " -fx-border-color:white;" +
                " -fx-text-inner-color: white; ");
        textFieldHeight.setFont(Font.font("Drawing-Rainbow.ttf"));
        textFieldWidth.setStyle("-fx-background-color: transparent;" +
                " -fx-border-width:0.5;" +
                " -fx-border-color:white;" +
                " -fx-text-inner-color: white;");
        textFieldHeight.setFont(Font.font("Drawing-Rainbow.ttf"));
        textFieldWidth.setPrefWidth(40);
        textFieldHeight.setPrefWidth(40);
        textFieldWidth.setText("2");
        textFieldHeight.setText("2");
        Image cursorClickImg = new Image(path.toAbsolutePath() +"\\text.png");
        textFieldHeight.setCursor(new ImageCursor(cursorClickImg));
        textFieldWidth.setCursor(new ImageCursor(cursorClickImg));
        DropShadow drop = new DropShadow(30, Color.WHITE);
        drop.setInput(new Glow());
        textFieldWidth.setOnMouseEntered(event -> {
            textFieldWidth.setEffect(drop);
        });
        textFieldWidth.setOnMouseExited(event -> {
            textFieldWidth.setEffect(null);
        });
        textFieldHeight.setOnMouseEntered(event -> {
            textFieldHeight.setEffect(drop);
        });
        textFieldHeight.setOnMouseExited(event -> {
            textFieldHeight.setEffect(null);
        });
        hBox.getChildren().addAll(text1, textFieldWidth, text2, textFieldHeight);
        return hBox;
    }
}