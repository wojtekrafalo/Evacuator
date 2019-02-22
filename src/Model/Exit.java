package Model;


import java.util.*;


/**
 * Project: Evacuator
 * Name: Exit.java
 * Purpose: Storing Data of Exit including:
 * which vertices are marked as exit;
 * floor where particular exit belongs;
 * Target Exit - where particular exit leads;
 * endsOfExit - double array of four numbers, describing ends of line
 * distance
 *
 * @author Bartosz Gorski "Alvirx"
 * @version 0.8 28.01.2019
 */
public class Exit
{
    //TODO add javadoc and tests
    private HashSet<Vertex> vertices;
    private Floor floor;
    private Exit targetExit;
    private Vertex targetVertex;
    private double[] endsOfExit;
    private double distanceToExit;

    /**
     * Public Constructor with all fields
     * @param vertices set of vertices, which are marked as exit
     * @param floor reference to floor, which Exit belongs to
     * @param targetVertex vertex, where particular exit leads
     * @param endsOfExit double array of four numbers, describing ends of line
     */
    public Exit(HashSet<Vertex> vertices, Floor floor, Vertex targetVertex, double[] endsOfExit)
    {
        this.vertices = vertices;
        this.floor = floor;
        targetExit = null;
        distanceToExit = Double.POSITIVE_INFINITY;
        this.targetVertex = targetVertex;
        this.endsOfExit = endsOfExit;
    }

    /**
     * Method used to assign a distance to Exit
     * @param d number, describing distance to this object
     */
    public void setDistanceToExit(double d)
    {
        this.distanceToExit = d;
    }

    /**
     * Method used to return a distance to Exit
     * @return number, describing distance to this object
     */
    public double getDistanceToExit() {
        return distanceToExit;
    }

    /**
     * Method used to set an Exit connected to this Exit.
     * @return targetExit reference to target of this object
     */
    public Exit getTargetExit() {
        return targetExit;
    }

    /**
     * Method used to set an Exit connected to this Exit.
     * @param targetExit reference to target of this object
     */
    public void setTargetExit(Exit targetExit) {
        this.targetExit = targetExit;
    }

    /**
     * Method used to return a Floor object, which Exit is located at
     * @return reference to Floor
     */
    public Floor getFloor() {
        return floor;
    }

    /**
     * Method used to return vertices, which Exit is related to.
     * @return set of vertices connected with this Exit
     */
    public Set<Vertex> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    /**
     * Method used to return an instance to destination Vertex, which Sims will toward to
     * @return Vertex of destination
     */
    public Vertex getTargetVertex() {
        return targetVertex;
    }

    /**
     * Default method extended from Object class,
     * returning comparison of objects.
     * @param o object, which method compares this object to.
     * @return true, if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exit exit = (Exit) o;
        return getVertices().equals(exit.getVertices()) &&
                getFloor().equals(exit.getFloor()) &&
                getTargetVertex().equals(exit.getTargetVertex());
    }

    /**
     * Default method extended from Object class,
     * returning hashCode of object.
     * @return hashCode of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(getVertices(), getFloor(), getTargetVertex());
    }

    /**
     * Method used to get an array of four elements, describing ends points of exit
     * @return array of ends points
     */
    public double[] getEndsOfExit() {
        return endsOfExit;
    }
}
