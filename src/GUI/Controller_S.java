package GUI;

import EditorController.Creator;
import Model.Building;
import Model.Exit;
import Model.Floor;
import Model.Sim;
import Simulation.SimulationController;
import Simulation.SimulationControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: Evacuator
 * Name: Controller_S.java
 * Purpose: Handles actions predicted in "window_s.fxml" window, such as clicking a buttons and
 * reacts on a simulation of fleeing people.
 * Existence of this class was forced by javafx.
 * @author Karolina Czapla "kyczor"
 * @version 0.8 12.01.2019
 */
public class Controller_S
{
    @FXML
    public AnchorPane anchor_pane_bottom;
    public Button endButton, currentFloorButton, startButton, animateButton;
    public ListView list_view_floors;
    public Canvas canvas;
    public GraphicsContext gc;
    public Label label;

    public SimulationControllerInterface simulation;
    private Floor currentFloor;
    private Polygon polygon;
    private Building building;
    private ArrayList<FloorButton> floorButtons;
    private Sim[] sims;
    private double floorStartX;
    private double floorStartY;
    private Color bgCol, wallCol, simCol;

    /**
     * Initializes a window with all options.
     */
    @FXML
    public void initialize() throws InterruptedException
    {
        bgCol = Color.web("rgb(98,248,183)");
        wallCol = Color.web("rgb(0, 63, 36)");
        simCol = Color.web("rgb(0,95,54)");


        gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0,870, 430);
        EventHandler<ActionEvent> EndClicked = e ->
        {
            try {
                Parent info_window_parent = FXMLLoader.load(getClass().getResource("window_1.fxml"));
                Scene info_window_scene = new Scene(info_window_parent);
                Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(info_window_scene);
                app_stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        };
        endButton.addEventHandler(ActionEvent.ACTION, EndClicked);

        EventHandler<ActionEvent> AnimateClicked = e ->
        {
            while(simulation.isGoing())
            {
                simulation.moveSims();
                showContent();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

        };

        animateButton.addEventHandler(ActionEvent.ACTION, AnimateClicked);

        endButton.setVisible(false);
        animateButton.setVisible(false);

        /*
            When user clicks on a button to change current floor - current floor is being displayed
         */
        list_view_floors.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                Object obj =  list_view_floors.getSelectionModel().getSelectedItem();
                if(obj instanceof Button)
                {
                    currentFloorButton = (Button)obj;
                    String currentButtonId = currentFloorButton.getId();
                    String numOfCurrFloor = currentButtonId.split("_")[1];
                    currentFloor = building.getFloor(Integer.valueOf(numOfCurrFloor));
                    polygon = currentFloor.getPolygon();
                    drawWalls();
                    placeSimsOnCanvas();
                }
            }
        });

        startButton.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                startButton.setVisible(false);
                simulation = new SimulationController();
                simulation.start();

                showContent();
                fillListOfFloors();
                endButton.setVisible(true);
                animateButton.setVisible(true);
            }
        });
    }

    /**
     * A method that draws content of current floor and pane with buttons.
     */
    private void showContent()
    {
        building = Building.getInstance();

        if (building != null) {
            currentFloor = building.getFloor(0);
            polygon = currentFloor.getPolygon();
            floorButtons = new ArrayList<>();
            drawWalls();
            placeSimsOnCanvas();
        }
    }

    /**
     * A method that draws fills pane with buttons represented by all floors.
     */
    private void fillListOfFloors()
    {
        int numOfFloors = building.numberOfFloors();
        for(int i=0; i< numOfFloors; i++)
        {
            Button but = new Button();
            but.setText(String.valueOf(i));
            but.setId("button_" + i);
            but.setPrefHeight(80);
            but.setPrefWidth(80);

            FloorButton fBut = new FloorButton(but, i, building.getFloor(i));
            floorButtons.add(fBut);
            list_view_floors.getItems().add(floorButtons.get(i).getButton());
        }
    }

    /**
     * A method that draws outside walls of building.
     */
    private void drawWalls()
    {
        List<Double> points = polygon.getPoints();
        double[] pointsA = new double[points.size()/2];
        double[] pointsB = new double[points.size()/2];

        floorStartX = points.get(0);
        floorStartY = points.get(1);

        //draw walls
        for(int j=0; j<points.size()/2; j++)
        {
            pointsA[j] = points.get(j*2);
            pointsB[j] = points.get((j*2) + 1);
        }
        gc.setFill(bgCol);
        gc.fillPolygon(pointsA, pointsB, points.size()/2);
        gc.setStroke(wallCol);
        gc.setLineWidth(3);
        gc.strokePolygon(pointsA, pointsB, points.size()/2);

        //draw exits
        List<Exit> exits = currentFloor.getExits();
        for(int i=0; i< exits.size(); i++)
        {
            Exit exit = exits.get(i);
            double[] exitEnds = exit.getEndsOfExit();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(6);
            gc.strokeLine(exitEnds[0], exitEnds[1], exitEnds[2], exitEnds[3]);
        }
    }

    /**
     * A method that fetches all Sim data from current floor and places sims on their positions in canvas
     */
    private void placeSimsOnCanvas()
    {
        List<Sim> simsOnFloor = currentFloor.getSims();
        double pSize = Creator.getInstance().getPILE_SIZE();
        sims = new Sim[simsOnFloor.size()];
        sims = simsOnFloor.toArray(sims);
        gc.clearRect(0,0,870,430);
        drawWalls();

        for(int i = 0; i < sims.length; i++)
        {
            gc.setFill(simCol);
            gc.fillOval(sims[i].getX()*pSize + floorStartX, sims[i].getY()*pSize + floorStartY, 8, 8);
        }
    }
}
