package Model;

import Model.Vertex;

import java.util.ArrayList;


/**
 * Project: Evacuator
 * Name: Building.java
 * Purpose: Stores Building data in the form of ArrayList that has Floors inside
 * Uses Singleton Pattern for usage in whole application
 *
 * @author Bartosz Gorski "Alvirx"
 * @version 0.4 14.12.2018
 */
public class Building //TODO add functionality of negative (underground) floors
{
    //TODO javadoc and tests for creating Exits

    /**
     * initialization of Singleton`s instance
     */
    static Building instance = new Building();
    private ArrayList<Floor> floors;

    /**
     * Private Constructor as Singleton Pattern Stands
     */
    private Building() {
        this.floors = new ArrayList<>();
    }

    /**
     * Adds floor to list.
     * Floor is added on top of building.
     * @param floor to be added
     */
    public void addFloor(Floor floor) {

        floors.add(floor);

    }

    /**
     * Gets floor of particular id and returns it. Floors are in order from bottom
     * to top.
     * @param id of floor to be returned
     * @return particular floor
     */
    public Floor getFloor(int id)
    {
        return floors.get(id);
    }

    /**
     * Removes floor of particular id.
     * @param id id of floor to be removed
     */
    public void removeFloor(int id)
    {
        floors.remove(id);
    }

    /**
     * Singleton Pattern method.
     * @return instance of Building
     */
    public static Building getInstance()
    {
        return instance;
    }

    /**
     * Clears list of floors.
     */
    public void clear()
    {
        floors.clear();
    }

    /**
     * Returns number of floors in building.
     * @return size of list of floors
     */
    public int numberOfFloors() {
        return floors.size();
    }

    /**
     * Connects two exits
     * @param exit1 - first exit to be connected
     * @param exit2 - second exit to be connected
     * @return true if exits ware successfully connected, false otherwise
     */
    public boolean connectExits(Exit exit1, Exit exit2)
    {
        if(exit1==null || exit2==null)
            return false;
        exit1.setTargetExit(exit2);
        exit2.setTargetExit(exit1);
        return true;
    }



}
