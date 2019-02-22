package GUI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Project: Evacuator
 * Name: Controller1.java
 * Purpose: Handles actions predicted in "window1.fxml" window, such as clicking a buttons.
 * This class predicts attempts of opening a info window and Editor.
 * Existence of this class was forced by javafx.
 * @author Karolina Czapla "kyczor"
 * @version 0.1 16.11.2018
 */
public class Controller1
{
    @FXML
    private Button startButton;

    @FXML
    private Button infoButton;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView imgView, imgViewTitle;

    /**
     * Initializes settings of a window.
     */
    @FXML
    void initialize() throws URISyntaxException {
        Image img = new Image(getClass()
                .getResource("running_man.png")
                .toURI().toString());
        imgView.setImage(img);

        Image imgTit = new Image(getClass()
                .getResource("evacuator_title.png").toURI().toString());
        imgViewTitle.setImage(imgTit);

        EventHandler<ActionEvent> InfoClicked = e ->
        {
            try
            {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_2A.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                info_window_scene.getStylesheets().add(getClass().getResource("karo_style.css").toExternalForm());
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };

        EventHandler<ActionEvent> StartClicked = e ->
        {
            try
            {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_2B.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        };
        startButton.addEventHandler(ActionEvent.ACTION, StartClicked);
        infoButton.addEventHandler(ActionEvent.ACTION, InfoClicked);
    }
}