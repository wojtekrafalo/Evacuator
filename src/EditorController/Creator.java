package EditorController;

import Model.Floor;
import Model.Vertex;

import java.util.HashSet;

/**
 * Project: Evacuator
 * Name: MouseNewSimState.java
 * Purpose: This class is a Semaphore of Factory pattern.
 * Oversees the creation of objects like Exits, Sims, Floors
 * It also contains data about current Floor,
 * which objects are added to.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class Creator {
    private Floor floor;
    private double PILE_SIZE;

    private static Creator instance = new Creator();

    /**
     * public Constructor. Assigns current floor
     */
    private Creator() {
        this.floor = new Floor();
    }

    /**
     * Default method of Singleton Design Pattern.
     * @return instance of this Creator.
     */
    public static Creator getInstance() {
        return instance;
    }

    /**
     * Method returns current Floor.
     * @return reference to current Floor.
     */
    public Floor getFloor() {
        return floor;
    }

    /**
     * Method assigns current Floor.
     * @param floor reference to current Floor.
     */
    public void setFloor( Floor floor) {
        this.floor = floor;
    }

    /**
     * Method returns size of a Pile, painted in Editor and Simulation GUI.
     * @return number describing size of a pile
     */
    public double getPILE_SIZE() {
        return PILE_SIZE;
    }

    /**
     * Method assigns size of a Pile, painted in Editor and Simulation GUI.
     * @param PILE_SIZE number describing size of a pile
     */
    public void setPILE_SIZE (double PILE_SIZE) {
        this.PILE_SIZE = PILE_SIZE;
    }
}
