package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

/**
 * Project: Evacuator
 * Name: InitWindow.java
 * Purpose: class initializes "window_1.fxml" to start the Application
 * @author Karolina Czapla "kyczor"
 * @version 0.1 19.11.2018
 */
public class InitWindow extends Application
{

    /**
     * Main method of Application. Runs the entire project
     * @param args arguments of start-up
     */
    public static void main(String[] args)
    {
//        String[] arguments = new String[] {"/bin/bash", "-c", "echo", "with"};
////        String[] arguments = new String[] {"ping", "www.google.com"};
//        try {
//            Process proc = new ProcessBuilder(arguments).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        launch(args);
    }

    /**
     * Method extended from Application class. Runs the entire project
     * @param primaryStage stage of a window to print
     * @throws Exception Exception of any unexpected actions
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Evacuator");
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("window_1.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("karo_style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Evacuator");
        primaryStage.show();
    }
}