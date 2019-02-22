package dataFlow;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Saver {

    public static void save(String text,File file) {

        try (PrintWriter out = new PrintWriter(file.getAbsolutePath())) {
            out.print(text);
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File error");
            alert.setHeaderText("I can't do it for you master :(");
            alert.setContentText("File doesn't exist or can't be created!");
            alert.showAndWait();
        }
    }

    public static String load(File file) {
        StringBuilder message = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            String word;
            while (scanner.hasNext()) {
                word = scanner.next();
                message.append(" ");
                message.append((word));
            }
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File error");
            alert.setHeaderText("I can't do it for you master :(");
            alert.setContentText("File doesn't exist or can't be created!");
            alert.showAndWait();
        }
        return message.toString();
    }
}