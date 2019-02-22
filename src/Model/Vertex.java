package Model;



import java.util.*;

/**
 * Project: Evacuator
 * Name: Sim.java
 * Purpose: Stores data about single vertex, pile of floor,
 * with default size of 0.5m x 0.5m, such as its vertical
 * and horizontal location and reference to Sim standing on it.
 *
 * @author Bartosz Gorski "Alvirx", Wojciech Rafalowski "wojtekrafalo"
 * @version 0.4 15.12.2018
 */
public class Vertex
{
    private int x;
    private int y;
    private LinkedList<Sim> standingSims;
    private boolean enabled;
    private Exit exit;

    /**
     * Public constructor, what sets coordinates of vertex and rest of the fields as null.
     * @param x
     * @param y
     */
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        standingSims = new LinkedList<>();
        exit = null;
        enabled = true;
    }

    /**
     * Returns reference to exit whether such is assigned
     * @return exit
     */
    public Exit getExit() {
        return exit;
    }

    /**
     * Method assigns an exit to vertex
     * @param exit
     */
    public void setExit(Exit exit) {
        this.exit = exit;
    }

    /**
     * Adds sim to list of standing sims.
     * @param sim to be added.
     */
    public void addStandingSim(Sim sim) {
        standingSims.add(sim);
    }

    /**
     * Removes sim from list of standing sims.
     * @param sim to be removed.
     */
    public void removeStandingSim(Sim sim)
    {
        standingSims.remove(sim);
    }

    /**
     * Default getter of standingSims list. Returns unmodifiable version of List.
     * @return unmodifiable version of standingSims list
     */
    public List<Sim> getStandingSims() {
        return Collections.unmodifiableList(standingSims);
    }

    /**
     * Default getter of vertex' coordinate.
     * @return horizontal coordinate of vertex
     */
    public int getX() {
        return x;
    }

    /**
     * Default getter of vertex' coordinate.
     * @return vertical coordinate of vertex
     */
    public int getY() {
        return y;
    }

    /**
     * Default getter of vertex' availability.
     * @return true, if vertex is enabled.
     */
    public boolean isEnabled () {
        return enabled;
    }

    /**
     * Default setter of vertex' availability.
     * Field using in Editor to mark, if
     * vertex carry floor or wall.
     * @param enabled availability of vertex.
     */
    public void setEnabled (boolean enabled) {
        this.enabled=enabled;
    }


    /**
     * Default equals method,
     * using to compare two vertexes
     * @param o compared object
     * @return true if parameter object is equal to this object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return getX() == vertex.getX() &&
                getY() == vertex.getY();
    }

    /**
     * Default method extended from Object class,
     * returning hashCode of object.
     * @return hashCode of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
