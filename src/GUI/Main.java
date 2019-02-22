package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Project: Evacuator
 * Name: InitWindow.java
 * Purpose: class initializes "controller.fxml" to start the test Application
 * @author Jakub Bryniarski "Draco Malfoy"
 * @version 0.1 19.11.2018
 */
public class Main extends Application {

    /**
     * Main method of Application. Runs the test project
     * @param args arguments of start-up
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Method extended from Application class. Runs the test project
     * @param primaryStage stage of a window to print
     * @throws Exception Exception of any unexpected actions
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Evacuator");
        Parent root = FXMLLoader.load(getClass().getResource("controller.fxml"));
        //primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.jpg"))); ustawienie ikony dla okna
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setTitle("Evacuator");
        primaryStage.show();

    }
}
