package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Project: Evacuator
 * Name: Controller3.java
 * Purpose: Handles actions predicted in "window3.fxml" window, such as clicking a buttons.
 * This class predicts actions like opening a simulation and statistical windows
 * Existence of this class was forced by javafx.
 * @author Karolina Czapla "kyczor"
 * @version 0.1 16.11.2018
 */
public class Controller3 {

    @FXML
    private Button visButton;

    @FXML
    private Button statButton;

    @FXML
    private Button backButton;

    @FXML
    private Button infoButton;

    /**
     * Initializes a window with all options.
     */
    @FXML
    void initialize() {
        EventHandler<ActionEvent> BackClicked = e ->
        {
            try {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_1.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                info_window_scene.getStylesheets().add(getClass().getResource("karo_style.css").toExternalForm());
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };

        EventHandler<ActionEvent> InfoClicked = e ->
        {
            try {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_4A.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };

        EventHandler<ActionEvent> VisualClicked = e ->
        {
            try {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_s.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1)
            {
                e1.printStackTrace();
            }
        };

        EventHandler<ActionEvent> StatClicked = e ->
        {
            //todo: co sie dzieje gdy klikne "statystyczny"

        };

        visButton.addEventHandler(ActionEvent.ACTION, VisualClicked);
        statButton.addEventHandler(ActionEvent.ACTION, StatClicked);
        backButton.addEventHandler(ActionEvent.ACTION, BackClicked);
        infoButton.addEventHandler(ActionEvent.ACTION, InfoClicked);
    }
}
