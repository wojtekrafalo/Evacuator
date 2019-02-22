package EditorController;

import Model.Floor;
import Model.Vertex;
import javafx.scene.shape.Polygon;
import java.util.LinkedList;

/**
 * Project: Evacuator
 * Name: FloorCreator.java
 * Purpose: This class is used to create a Floor in base of Polygon added by the user.
 * Oversees Class modifies the Polygon of contour.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.8 06.12.2018
 */
public class FloorCreator
{
    private double PILE_SIZE;
    private double startX;
    private double startY;
    private Floor floor;
    private Polygon polygon = null;

    private static FloorCreator instance = new FloorCreator();

    /**
     * Public Constructor assigns current floor.
     */
    private FloorCreator() {
        this.floor = new Floor();
    }

    /**
     * Default Singleton method to return an instance of class.
     * @return instance of class.
     */
    public static FloorCreator getInstance()
    {
        return instance;
    }

    /**
     * Method initialize Creator, if new Floor is going to be created.
     * @param PILE_SIZE size of the pile of graph's Vertex.
     * @param polygon Polygon of the Floor.
     */
    public void initialize (double PILE_SIZE, Polygon polygon) {
        this.PILE_SIZE = PILE_SIZE;
        this.polygon = polygon;
        if (polygon != null) {
            startX = polygon.getPoints().get(0);
            startY = polygon.getPoints().get(1);
        }
        floor = new Floor();
        Creator.getInstance().setFloor(floor);
    }


    /**
     * Method creates a Floor on base of Polygon.
     * @return Floor class.
     */
    public Floor createGraph() {
        LinkedList<Vertex> verticesToCompute = new LinkedList<>();

        // start expanding graph
        floor.setPolygon(polygon);
        Vertex startV = floor.addVertex(0, 0);
        System.out.println("Dodaje: ("+0+";"+0+")");
        verticesToCompute.add(startV);

        while (!verticesToCompute.isEmpty())
        {
            Vertex v = verticesToCompute.poll();
            int x = v.getX();
            int y = v.getY();
            addVertex(x, y+1, verticesToCompute);
            addVertex(x, y-1, verticesToCompute);
            addVertex(x+1, y, verticesToCompute);
            addVertex(x-1, y, verticesToCompute);
            addVertex(x+1, y+1, verticesToCompute);
            addVertex(x-1, y-1, verticesToCompute);
            addVertex(x-1, y+1, verticesToCompute);
            addVertex(x+1, y-1, verticesToCompute);
        }
        return floor;
    }

    /**
     * Method adds a Vertex, neighbouring Vertices and connecting Edges to the Graph of the floor.
     * @param x horizontal coordinate of the Vertex.
     * @param y vertical coordinate of the Vertex.
     * @param verticesToCompute set of vertices, which methods adds vertices to.
     */
    private void addVertex(int x, int y, LinkedList<Vertex> verticesToCompute) {
        if(polygon.contains(startX+x*PILE_SIZE,startY+y*PILE_SIZE)) {
            Vertex v = floor.addVertex(x, y);
            if(v!=null) {
                verticesToCompute.add(v);
                System.out.println("Dodaje: ("+x+";"+y+")");

                addEdge(v.getX(), v.getY(), v.getX()-1, v.getY());
                addEdge(v.getX(), v.getY(), v.getX()+1, v.getY());
                addEdge(v.getX(), v.getY(), v.getX()-1, v.getY()-1);
                addEdge(v.getX(), v.getY(), v.getX()-1, v.getY()+1);
                addEdge(v.getX(), v.getY(), v.getX()+1, v.getY()-1);
                addEdge(v.getX(), v.getY(), v.getX()+1, v.getY()+1);
                addEdge(v.getX(), v.getY(), v.getX(), v.getY()-1);
                addEdge(v.getX(), v.getY(), v.getX(), v.getY()+1);
            }

        }
    }

    /**
     * Method adds an Edge to the floor.
     * @param sourceX horizontal coordinate of the source.
     * @param sourceY vertical coordinate of the source.
     * @param targetX horizontal coordinate of the target.
     * @param targetY vertical coordinate of the target.
     */
    private void addEdge(int sourceX, int sourceY, int targetX, int targetY) {
        floor.addEdge(sourceX, sourceY, targetX, targetY, Floor.distance((double)sourceX, (double)sourceY, (double)targetX, (double)targetY));
    }

    /**
     * Method adds point to the Polygon.
     * @param x horizontal coordinate of the point.
     * @param y vertical coordinate of the point.
     */
    public void addPolygonVertex(double x, double y) {
        if (polygon == null) {
            System.out.println("tworze: "+x+" "+y);
            polygon = new Polygon(x, y);
            startX = x;
            startY = y;
        } else {
            System.out.println("dodaje: "+x+" "+y);
            polygon.getPoints().add(x);
            polygon.getPoints().add(y);
        }
    }

    /**
     * Method returns Polygon of wall.
     * @return Polygon of wall.
     */
    public Polygon getPolygon () {
        return polygon;
    }

    /**
     * Method returns current Floor.
     * @return current Floor.
     */
    public Floor getFloor () {
        return floor;
    }

    /**
     * Method returns size of the Polygon.
     * @return size of the Polygon.
     */
    public int getPolygonSize() {
        if (polygon == null) return 0;
        else if (polygon.getPoints() == null) return 0;
        else return polygon.getPoints().size();
    }
}