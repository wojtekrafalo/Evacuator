package GUI;

import Model.Floor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Project: Evacuator
 * Name: FloorButton.java
 * Purpose: A Container of data related to Button of specific Floor Handles actions predicted in "window_s.fxml" window, such as clicking a buttons and
 * reacts on a simulation of fleeing people.
 * Existence of this class was forced by javafx.
 * @author Karolina Czapla "kyczor"
 * @version 0.8 12.01.2019
 */
public class FloorButton
{
    private Button button;
    private int floorNum;
    private Floor floor;

    /**
     * Constructor that creates a FloorButton with all variables.
     * @param button button specified by javafx.
     * @param floorNum number of floor, printed in button
     * @param floor object of floor, which button concerns.
     */
    public FloorButton(Button button, int floorNum, Floor floor)
    {
        this.button = button;
        this.floorNum = floorNum;
        this.floor = floor;

        javafx.event.EventHandler<ActionEvent> floorChosen = e ->
        {
            //currentFloor = i;
        };
        button.addEventHandler(ActionEvent.ACTION,floorChosen);
    }

    /**
     * Returns javafx object used in window_s to print them.
     * @return javafx button
     */
    public Button getButton()
    {
        return button;
    }

    /**
     * Returns number of floor specified by button
     * @return issue of a Floor
     */
    public int getFloorNum()
    {
        return floorNum;
    }

    /**
     * Returns instance of a Floor. Method used in "window_s"
     * @return object of floor, which instance this class concerns.
     */
    public Floor getFloor()
    {
        return floor;
    }
}
