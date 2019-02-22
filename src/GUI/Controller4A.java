package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Project: Evacuator
 * Name: Controller4.java
 * Purpose: Handles actions predicted in "window4A.fxml" window, such as clicking a buttons.
 * This window prints information about differences between statistical and visualization window.
 * This class predicts actions like opening previous windows
 * Existence of this class was forced by javafx.
 * @author Karolina Czapla "kyczor"
 * @version 0.8 16.11.2018
 */
public class Controller4A
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
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_3.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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