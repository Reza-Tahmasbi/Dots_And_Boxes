package com.example.DB;

import javafx.scene.control.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static com.example.DB.Board.*;
import static com.example.DB.Board.countDown;
import static com.example.DB.Game.*;
import static com.example.DB.GameMenu.savedFiles;

public class SaveGame {
    // Create a combo box
    public static File makeDirectory() {
        // Creating new directory in Java, if it doesn't exist
        File directory = new File(path.toAbsolutePath() + "\\Saves");
        if (!directory.exists()) {
            directory.mkdir();
            //System.out.println("Directory not exists, creating now");
        } else {
            //System.out.printf("Failed to create new directory: Saves");
        }
        return directory;
    }

    public static void fillSavedFiles(final File folder) {
        savedFiles = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            savedFiles.add(fileEntry.getName());
        }
    }

    public static void save(String fileName) throws IOException {
        System.out.println(savedFiles);
        if (!savedFiles.contains(fileName + ".txt")) {
            saveFile(fileName);
        } else {
            ButtonType yup = new ButtonType("Yup", ButtonBar.ButtonData.OK_DONE);
            ButtonType nope = new ButtonType("No dude", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.NONE, "File Exists," +
                    " Do you want to replace it?", yup, nope);
            alert.setTitle("Override Warning");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(nope) == yup) {
                saveFile(fileName);
            }
        }
    }

    private static void saveFile(String fileName) throws IOException {
        savedFiles.add(fileName + ".txt");
        PrintWriter writer = new PrintWriter(path.toAbsolutePath() + "\\Saves\\" + fileName + ".txt", StandardCharsets.UTF_8);
        writer.println(Arrays.deepToString(board));
        writer.println(turn);
        //get players
//        FileOutputStream f = new FileOutputStream(path.toAbsolutePath() + "\\Saves\\" + fileName + ".txt");
//        ObjectOutputStream o = new ObjectOutputStream(f);
//        f.write(turn);
//        o.writeObject(b);
//         //Write objects to file
//         for (Player player:players){
//             if (player!=null){
//                 o.writeObject(player);
//             }
//         }
//        o.close();
//        f.close();
        //writer.println(currentPlayer.getName());
        for (int i = 0; i < players.length; i++) {
            if (players[i] != null) {
                System.out.println(players[i].getColor());
                writer.println(i + "," + players[i].getName() + "," + players[i].getColor() + "," + players[i].getScore());
            }
        }
        writer.println("p_end");
        //get board
        for (int i = 0; i < boardH; i++) {
            for (int j = 0; j < boardW; j++) {
                writer.print(board[i][j].getStatus());
            }
        }
        writer.print("\n");
        writer.println("b_end");
        //get lines
        for (int i = 0; i < boardH + 1; i++) {
            for (int j = 0; j < boardW; j++) {
                writer.print(hoLines[i][j].getStatus());
            }
        }
        writer.print("\n");
        for (int i = 0; i < boardH; i++) {
            for (int j = 0; j < boardW + 1; j++) {
                writer.print(veLines[i][j].getStatus());
            }
        }
        writer.close();
        countDown.getMessage().setText("Game Saved");
        countDown.getMessage().setVisible(true);
    }
}