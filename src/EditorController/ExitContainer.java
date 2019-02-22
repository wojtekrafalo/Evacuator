package EditorController;

import Model.Vertex;
import javafx.scene.shape.Line;

import java.util.HashSet;

/**
 * Project: Evacuator
 * Name: ExitContainer.java
 * Purpose: This class holds data of specific Exit.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.8 06.01.2019
 */
public class ExitContainer {
    private HashSet<Vertex> set;
    private int x;
    private int y;
    private double[] ends;

    /**
     * Public Constructor of Container.
     * @param set set of vertices related with the Exit.
     * @param x horizontal position of the Target Exit.
     * @param y vartical position of the Target Exit.
     * @param ends array of Exit's coordinates.
     */
    public ExitContainer (HashSet<Vertex> set, int x, int y, double[] ends) {
        this.set = set;
        this.x = x;
        this.y = y;
        this.ends = ends;
    }

    /**
     * Method returns set of vertices related with the Exit.
     * @return set of vertices related with the Exit.
     */
    HashSet<Vertex> getSet() {
        return set;
    }

    /**
     * Method returns horizontal position of the Target Exit.
     * @return horizontal position of the Target Exit.
     */
    int getX() {
        return x;
    }

    /**
     * Method returns vertical position of the Target Exit.
     * @return vertical position of the Target Exit.
     */
    int getY() {
        return y;
    }

    /**
     * Method returns ends array of Exit's coordinates.
     * @return ends array of Exit's coordinates.
     */
    double[] getEnds() {
        return ends;
    }
}
