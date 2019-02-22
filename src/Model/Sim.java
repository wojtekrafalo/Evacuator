package Model;

import com.sun.javafx.geom.*;

/**
 * Project: Evacuator
 * Name: Sim.java
 * Purpose: Stores data about single Sim, such as its vertical and horizontal location and coordinates of its pile
 *
 * @author Bartosz Gorski "Alvirx", Wojciech Rafalowski "wojtekrafalo"
 * @version 0.4 18.12.2018
 */
public class Sim
{
    //TODO complete javadoc, add other sim properties
    private double x;
    private double y;
    private Vertex vertex;
    private Vec2d v;
    private Exit headingTo;
    private double vMax;
    private double aMax;

    /**
     * Constructor used to create the Sim at Editor at specific position
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public Sim(double x, double y) {
        this.x = x;
        this.y = y;
        v = new Vec2d(0,0);
        headingTo = null;
        vMax = 20; // km/h
        aMax = 3; //
    }

    /**
     * Method used to set coordinate of Sim's position
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method used to get horizontal position of Sim
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Method used to get vertical position of Sim
     * @return y
     */
    public double getY() {
        return y;
    }


    /**
     * Method used to get pile of building occupied by the Sim
     * @return vertex
     */
    public Vertex getVertex() {
        return vertex;
    }

    /**
     * Method used to set specific pile of building occupied by the Sim
     * @param vertex specific pile to assign to Sim
     */
    public void setVertex(Vertex vertex) {
        this.vertex = vertex;
    }


    /**
     * Method used to get an Exit, which Sim is going to.
     * @return object of Exit.
     */
    public Exit getHeadingTo() {
        return headingTo;
    }

    /**
     * Method used to set specific Exit of building which Sim will go
     * @param headingTo object of Exit
     */
    public void setHeadingTo(Exit headingTo) {
        this.headingTo = headingTo;
    }

    /**
     * Method used to get a vector of a velocity of Sim
     * @return vector of a speed
     */
    public Vec2d getV()
    {
        return v;
    }

    /**
     * Method used to set vector of a velocity of Sim
     * @param v vector of a speed
     */
    public void setV(Vec2d v)
    {
        this.v = v;
    }

    /**
     * Method used to get a maximal velocity of Sim
     * @return value of a maximal speed
     */
    public double getVMax()
    {
        return vMax;
    }

    /**
     * Method used to get a vector of a maximal acceleration of Sim
     * @return value of a maximal acceleration
     */
    public double getAMax() {
        return aMax;
    }
}
