package GUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    @FXML
    private Button loadButton;

    @FXML
    private Button saveButton;

    @FXML
    void initialize(){

        EventHandler<ActionEvent> SaveClicked = e ->
        {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*");
            fileChooser.getExtensionFilters().add(extFilter);

            Stage stage = new Stage();
            //Show save file dialog
            File file = fileChooser.showSaveDialog(stage);
            if( file != null){
                   dataFlow.Saver.save("tekst", file);
                System.out.println("zapisano");
            }

        };

        EventHandler<ActionEvent> LoadClicked = e ->
        {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*");
            fileChooser.getExtensionFilters().add(extFilter);

            Stage stage = new Stage();
            File file = fileChooser.showOpenDialog(stage);
            if(file != null)
            {
                System.out.println(dataFlow.Saver.load(file));
                System.out.println("zaladowane");
            }

        };
        loadButton.addEventHandler(ActionEvent.ACTION, LoadClicked);
        saveButton.addEventHandler(ActionEvent.ACTION, SaveClicked);
    }
}
