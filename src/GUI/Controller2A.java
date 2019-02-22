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
 * Name: Controller3A.java
 * Purpose: Handles actions predicted in "window2A.fxml" window, such as clicking a buttons.
 * This class predicts attempts of opening a start window.
 * Existence of this class was forced by javafx.
 * @author Karolina Czapla "kyczor"
 * @version 0.1 16.11.2018
 */
public class Controller2A
{
    @FXML
    private Button backButton;

    /**
     * Initializes a window with all options.
     */
    @FXML
    void initialize()
    {
        EventHandler<ActionEvent> BackClicked = e ->
        {
            try {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_1.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                info_window_scene.getStylesheets().add(getClass().getResource("karo_style.css").toExternalForm());
                app_stage.hide();
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };

        backButton.addEventHandler(ActionEvent.ACTION, BackClicked);
    }
}