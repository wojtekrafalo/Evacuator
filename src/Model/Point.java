package Model;

import java.util.Objects;

/**
 * Project: Evacuator
 * Name: Point.java
 * Purpose: Stores simple point X and Y coordinates. Is used in HashMap as ID of
 * Vertices.
 * @author Bartosz Gorski "Alvirx"
 * @version 0.1 15.12.2018
 */
public class Point
{
    private int x;
    private int y;

    /**
     * Constructs a Point with integer variables
     * @param x horizontal coordinate
     * @param y vertical coordinate
     */
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Default equals method, checks if object specified as parameter is equal to
     * reference to this class.
     * @param o object, what is compared with this Point
     * @return true if objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    /**
     * Default method extended from Object class,
     * returning hashCode of object.
     * @return hashCode of this object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
