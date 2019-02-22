package GUI;

import EditorController.*;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import org.jgrapht.graph.DefaultWeightedEdge;


import java.io.IOException;

/**
 * Project: Evacuator
 * Name: Controller2B.java
 * Purpose: Handles actions in "window.2B".fxml window, such as clicking a buttons,
 * menu options and painting pane.
 * This class predicts attempts of creating w floor, inside walls in specific room,
 * adding exits, adding people.
 * Moreover handles attempts of prohibited actions, like opening a simulation, when
 * Building don't have exits or people.
 * Existence of this class was forced by javafx.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.8 28.01.2019
 */
public class Controller2B{
    @FXML private Button compileButton;
    @FXML private Button simulationButton;
    @FXML private Button backButton;

    @FXML private Button newFileButton;
    @FXML private Button loadFileButton;
    @FXML private Button saveFileButton;
    @FXML private Button copyButton;
    @FXML private Button cutButton;
    @FXML private Button pasteButton;
    @FXML private Button newWallButton;
    @FXML private Button newDoorButton;
    @FXML private Button newStairsButton;
    @FXML private Button newSimButton;
    @FXML private Button deleteButton;
    @FXML private Button loadPngButton;

    @FXML private GridPane gridPane;
    @FXML private MenuBar menuBar;
    @FXML private Canvas paintCanvas;
    @FXML private AnchorPane stairs;
    @FXML private Label testLabel;
    @FXML private Pane pane;

    private final double PILE_SIZE = 10.0;
    private final double DELTA = 3;
    private final double RADIUS = 5;

    private FloorCreator floorCreator = FloorCreator.getInstance();
    private ExitCreator exitCreator = ExitCreator.getInstance();
    private WallInsideCreator wallInsideCreator = WallInsideCreator.getInstance();

    private MouseStateContext mouseStateContext;

    /**
     * Initializes settings of Editor window.
     */
    @FXML
    void initialize(){
        mouseStateContext = new MouseStateContext();
        floorCreator.initialize(PILE_SIZE, null);
        exitCreator.initialize(PILE_SIZE, 100,null);
        mouseStateContext.setState(new MouseWallOutState());
        pane.setStyle("-fx-background-color: #FFFFFF");


        paintCanvas.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            try {
                Object object = mouseStateContext.clicked(e);

                if (object != null)
                    if (object.getClass().equals(Floor.class)) {

                        Floor floor = (Floor) object;
                        Creator.getInstance().setFloor(floor);
                        Creator.getInstance().setPILE_SIZE(PILE_SIZE);

                        Polygon polygon = floor.getPolygon();

                        if (polygon != null && polygon.getPoints().size() >= 6 && !pane.getChildren().contains(polygon)) {
                            polygon.setStroke(Color.BLACK);
                            polygon.setFill(null);
                            polygon.setStrokeWidth(DELTA);
                            pane.getChildren().add(polygon);
                        }

                    } else if (object.getClass().equals(Polyline.class)) {                                                      //  inside wall

                        Polyline polyline = (Polyline) object;

                        if (polyline.getPoints().size() >= 4 && !pane.getChildren().contains(polyline)) {
                            polyline.setStroke(Color.BLACK);
                            pane.getChildren().add(polyline);

                        }

                    } else if (object.getClass().equals(Exit.class)) {

                        Exit exit = (Exit) object;
                        double[] ends = exit.getEndsOfExit();

                        Line line = new Line(ends[0], ends[1], ends[2], ends[3]);

                        line.setStroke(Color.WHITE);
                        line.setFill(Color.WHITE);
                        line.setStrokeWidth(2*DELTA);
                        pane.getChildren().add(line);

                    } else if (object.getClass().equals(Sim.class)) {

                        Circle circ = new Circle(e.getX(), e.getY(), RADIUS);

                        circ.setStroke(Color.BLACK);
                        circ.setFill(Color.RED);
                        pane.getChildren().add(circ);
                    }
            } catch (ExitAlreadyPlacedInThatPositionException ex) {
                showErrorDialog("Exit already exists in this position!");

            } catch (NullPointerException ex2) {
                showErrorDialog("Some Error!");
            }
        });


        backButton.addEventHandler(ActionEvent.ACTION, e -> {
            goToSpecificWindow("window_1.fxml", e);
        });


        compileButton.addEventHandler(ActionEvent.ACTION, e -> {

            boolean existsExits = false;
            boolean existsSims = false;

            ObservableList<Node> children = pane.getChildren();
            for (Node node : children) {
                if (node.getClass().equals(Circle.class))
                    existsSims = true;

                if (node.getClass().equals(Line.class))
                    existsExits = true;
            }

            if (!existsExits) {
                showErrorDialog("Your Building should some people, ;)");
            }
            else if (!existsSims) {
                showErrorDialog("Your Building should some people, ;)");
            }
        });

        simulationButton.addEventHandler(ActionEvent.ACTION, e -> {
            goToSpecificWindow("window_3.fxml", e);
        });



        newStairsButton.addEventHandler(ActionEvent.ACTION, e -> {
            System.out.println("Stairs");
            mouseStateContext.setState(new MouseNewExitState());
        });

        newDoorButton.addEventHandler(ActionEvent.ACTION, e -> {
            System.out.println("Exit");
            exitCreator.initialize(PILE_SIZE, 100,null);
            mouseStateContext.setState(new MouseFinalExitState());
        });

        newWallButton.addEventHandler(ActionEvent.ACTION, e -> {
            System.out.println("InsideWall");
            wallInsideCreator.initialize(PILE_SIZE);
            mouseStateContext.setState(new MouseWallInsideState());
        });

        newSimButton.addEventHandler(ActionEvent.ACTION, e -> {
            System.out.println("Sim");
            mouseStateContext.setState(new MouseNewSimState());
        });

        newFileButton.addEventHandler(ActionEvent.ACTION, e -> {
            System.out.println("Reset");
            pane.getChildren().removeAll();
            Building.getInstance().clear();
            mouseStateContext.setState(new MouseWallOutState());
        });



    }

    /**
     * A assistant method shows Dialog with Error, which is used to inform the User
     * @param message a information printed in a Dialog
     */
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * A assistant method to move User from this window to another,
     * described name.
     * @param name name of window, what User wish to get.
     * @param e Event, on which this method reacts.
     */
    private void goToSpecificWindow(String name, ActionEvent e) {
        try {
            Parent info_window_parent = FXMLLoader.load(getClass().getResource(name));
            Scene info_window_scene = new Scene(info_window_parent);
            Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(info_window_scene);
            app_stage.show();
        } catch (IOException e1) {
            showErrorDialog("Cannot open this window!");
        }
    }
}
