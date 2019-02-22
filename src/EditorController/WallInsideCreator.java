package EditorController;

import Model.*;
import javafx.scene.shape.Polyline;

import java.util.HashSet;
import java.util.List;

/**
 * Project: Evacuator
 * Name: WallInsideCreator.java
 * Purpose: This class is member of Factory pattern.
 * Creates Walls inside and modifies the Building
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class WallInsideCreator {
    private double PILE_SIZE;
    private double startX;
    private double startY;
    private Floor floor;
    private Polyline polyline = null;
    private static WallInsideCreator instance = new WallInsideCreator();

    /**
     * Default method to get Instance of Singleton
     * @return instance to this class
     */
    public static WallInsideCreator getInstance() {
        return instance;
    }

    /**
     * empty Constructor
     */
    WallInsideCreator() {}

    /**
     * Method sets all fields to Creator, if new Polyline is going to be created
     * @param PILE_SIZE
     */
    public void initialize (double PILE_SIZE) {
        this.PILE_SIZE = PILE_SIZE;
        this.floor = Creator.getInstance().getFloor();
        this.startX = floor.getPolygon().getPoints().get(0);
        this.startY = floor.getPolygon().getPoints().get(1);
        polyline = null;
    }


    /**
     * Method creates result, a Polyline of wall, and modifies the Floor.
     * @return Polyline of wall.
     */
    public Polyline createWallInside() {
        double[] firstEnd = new double[2];
        double[] lastEnd  = new double[2];

        List<Double> list = polyline.getPoints();                                                                       // CONNECTING ENDS OF EDGES WITH WALLS IN THIS SECTION
        firstEnd[0] = list.get(0);
        firstEnd[1] = list.get(0);
        lastEnd[0] = list.get(list.size() - 2);
        lastEnd[0] = list.get(list.size() - 1);

        int[] nearestFirst = ExitCreator.nearestTwo(firstEnd[0], firstEnd[1], list);
        int[] nearestLast =  ExitCreator.nearestTwo(lastEnd[0], lastEnd[1], list);

        double[] orthoFirst= ExitCreator.findOrthographicProjection(firstEnd[0], firstEnd[1], nearestFirst, list);
        double[] orthoLast = ExitCreator.findOrthographicProjection(lastEnd[0], lastEnd[1], nearestLast, list);

        if (Floor.distance(orthoFirst[0], orthoFirst[1], firstEnd[0], firstEnd[1]) < 2*PILE_SIZE) {
            list.set(0, orthoFirst[0]);
            list.set(1, orthoFirst[1]);
        }

        if (Floor.distance(orthoLast[0], orthoLast[1], lastEnd[0], lastEnd[1]) < 2*PILE_SIZE) {
            list.set(list.size() - 2, orthoLast[0]);
            list.set(list.size() - 1, orthoLast[1]);
        }


        System.out.println("COMMING HERE");

        HashSet<Vertex[]> offSetVertices = new HashSet<>();                                                             // FINDING EDGEs IN THIS SECTION
        for (int i = 0; i<list.size()-4; i+=2) {
            offSetVertices.addAll(findVertices( list.get(i), list.get(i+1), list.get(i+2), list.get(i+3) ));
        }

        for (Vertex[] vPair : offSetVertices) {
            if (floor.containsEdge(vPair[0], vPair[1])) {
                floor.removeEdge(vPair[0], vPair[1]);
                System.out.println("removeEdge: "+vPair[0].getX()+" "+vPair[0].getY()+";  "+vPair[1].getX()+" "+vPair[1].getY());
            }
        }

        return polyline;
    }

    /**
     * Method finds pairs of Vertices in Floor's graph, which are located in across the line
     * @param end1X horizontal coordinate of source of line
     * @param end1Y vertical coordinate of source of line
     * @param end2X horizontal coordinate of target of line
     * @param end2Y vertical coordinate of target of line
     * @return set of pairs of offsets
     */
    public HashSet<Vertex[]> findVertices( double end1X, double end1Y, double end2X, double end2Y ) {

        int x1Vertex = (int)((end1X - startX)/PILE_SIZE);
        int y1Vertex = (int)((end1Y - startY)/PILE_SIZE);
//        System.out.println("exit1:("+ x1Vertex +","+ y1Vertex +")");

        int x2Vertex = (int)((end2X - startX)/PILE_SIZE);
        int y2Vertex = (int)((end2Y - startY)/PILE_SIZE);
//        System.out.println("exit1:("+ x2Vertex +","+ y2Vertex +")");

        int[] ends = {x1Vertex, y1Vertex, x2Vertex, y2Vertex};

        HashSet<Vertex[]> vertices = new HashSet<>();

        if (end2X-end1X != 0) {
            double tangens = (end2Y - end1Y) / (end2X - end1X);
            double step = (end1X <= end2X) ? PILE_SIZE : -PILE_SIZE;

            for (double x = end1X; x <= end2X; x = x + step) {
                double y = end1Y + tangens*(x-end1X);

                int xVer = (int)((x - startX)/PILE_SIZE);
                int yVer = (int)((y - startY)/PILE_SIZE);

                if (floor.getVertex(xVer, yVer) != null) {
                    vertices.addAll(findCollisions(xVer, yVer, ends));

                } else if (floor.getVertex(xVer, yVer+1) != null){
                    vertices.addAll(findCollisions(xVer, yVer+1, ends));

                } else if (floor.getVertex(xVer, yVer-1) != null) {
                    vertices.addAll(findCollisions(xVer, yVer-1, ends));
                }

                if (floor.getVertex(xVer+1, yVer) != null) {
                    vertices.addAll(findCollisions(xVer+1, yVer, ends));

                } else if (floor.getVertex(xVer+1, yVer+1) != null) {
                    vertices.addAll(findCollisions(xVer+1, yVer+1, ends));

                } else if (floor.getVertex(xVer+1, yVer-1) != null) {
                    vertices.addAll(findCollisions(xVer+1, yVer-1, ends));
                }


                if (floor.getVertex(xVer-1, yVer) != null) {
                    vertices.addAll(findCollisions(xVer-1, yVer, ends));

                } else if (floor.getVertex(xVer-1, yVer+1) != null){
                    vertices.addAll(findCollisions(xVer-1, yVer+1, ends));

                } else if (floor.getVertex(xVer-1, yVer-1) != null) {
                    vertices.addAll(findCollisions(xVer-1, yVer-1, ends));
                }
            }
        }


        if (end2Y - end1Y != 0) {
            double tangens = (end2X - end1X) / (end2Y - end1Y);
            double step = (end1Y <= end2Y) ? PILE_SIZE : -PILE_SIZE;

            for (double y = end1Y; y <= end2Y; y = y + step) {
                double x = end1X + (y - end1Y) * tangens;

                int xVer = (int) ((x - startX) / PILE_SIZE);
                int yVer = (int) ((y - startY) / PILE_SIZE);

                if (floor.getVertex(xVer, yVer) != null) {
                    vertices.addAll(findCollisions(xVer, yVer, ends));

                } else if (floor.getVertex(xVer + 1, yVer) != null) {
                    vertices.addAll(findCollisions(xVer+1, yVer, ends));

                } else if (floor.getVertex(xVer - 1, yVer) != null) {
                    vertices.addAll(findCollisions(xVer-1, yVer, ends));
                }

                if (floor.getVertex(xVer, yVer+1) != null) {
                    vertices.addAll(findCollisions(xVer, yVer+1, ends));

                } else if (floor.getVertex(xVer+1, yVer+1) != null){
                    vertices.addAll(findCollisions(xVer+1, yVer+1, ends));

                } else if (floor.getVertex(xVer-1, yVer+1) != null) {
                    vertices.addAll(findCollisions(xVer-1, yVer+1, ends));
                }


                if (floor.getVertex(xVer, yVer-1) != null) {
                    vertices.addAll(findCollisions(xVer, yVer-1, ends));

                } else if (floor.getVertex(xVer+1, yVer-1) != null){
                    vertices.addAll(findCollisions(xVer+1, yVer-1, ends));

                } else if (floor.getVertex(xVer-1, yVer-1) != null) {
                    vertices.addAll(findCollisions(xVer-1, yVer-1, ends));
                }
            }
        }
        return vertices;
    }

    /**
     * Method finds pairs of Vertices in Floor's graph,
     * which are located in opposite sides of the line.
     * Method Checks all eight neighbouring vertices to the one set as parameter
     * @param xVer horizontal coordinate of the Vertex
     * @param yVer vertical coordinate of the Vertex
     * @param ends array with four elements, describing ends of the line
     * @return set of vertices pairs, which are located across the line
     */
    private HashSet<Vertex[]> findCollisions(int xVer, int yVer, int[] ends) {
        HashSet<Vertex[]> vertices = new HashSet<>();

        Vertex[] v1 = removeEdgeIfOpposite(xVer, yVer, xVer+1, yVer+1, ends);
        if (v1 != null) vertices.add(v1);
        Vertex[] v2 = removeEdgeIfOpposite(xVer, yVer,       xVer,   yVer+1, ends);
        if (v2 != null) vertices.add(v2);
        Vertex[] v3 = removeEdgeIfOpposite(xVer, yVer, xVer-1, yVer+1, ends);
        if (v3 != null) vertices.add(v3);
        Vertex[] v4 = removeEdgeIfOpposite(xVer, yVer, xVer-1,       yVer, ends);
        if (v4 != null) vertices.add(v4);

        Vertex[] v5 = removeEdgeIfOpposite(xVer, yVer, xVer+1, yVer-1, ends);
        if (v5 != null) vertices.add(v1);
        Vertex[] v6 = removeEdgeIfOpposite(xVer, yVer,       xVer,   yVer-1, ends);
        if (v6 != null) vertices.add(v2);
        Vertex[] v7 = removeEdgeIfOpposite(xVer, yVer, xVer-1, yVer-1, ends);
        if (v7 != null) vertices.add(v3);
        Vertex[] v8 = removeEdgeIfOpposite(xVer, yVer, xVer+1,       yVer, ends);
        if (v8 != null) vertices.add(v4);

        return vertices;
    }

    /**
     * Method returns pair of vertices if they are located on opposite sides of line.
     * @param xVer1 horizontal coordinate of the first Vertex
     * @param yVer1 vertical coordinate of the first Vertex
     * @param xVer2 horizontal coordinate of the second Vertex
     * @param yVer2 vertical coordinate of the second Vertex
     * @param ends ends of the line
     * @return pair of the vertices, if they are located on opposite sides of line.
     */
    private Vertex[] removeEdgeIfOpposite(int xVer1, int yVer1, int xVer2, int yVer2, int[] ends) {
        if (ends[0] == ends[2]) {
            if ((xVer1 < ends[0] && xVer2 >= ends[3]) || (xVer1 >= ends[0] && xVer2 < ends[3])) {
                return new Vertex[]{new Vertex(xVer1, yVer1), new Vertex(xVer2, yVer2)};
            }
        } else {
            double a = ( (double)(ends[1]-ends[3]) )/( (double)(ends[0]-ends[2]) );
            double b = ( (double)(ends[1]-ends[3]) )/( (double)(ends[0]-ends[2]) )*ends[0] + ends[1];

            if ( (a*xVer1 + b >= yVer1 && a*xVer2 + b < yVer2) || (a*xVer1 + b < yVer1 && a*xVer2 + b >= yVer2) )
                return new Vertex[]{new Vertex(xVer1, yVer1), new Vertex(xVer2, yVer2)};
        }
        return null;
    }

    /**
     * Method adds point to the Polyline.
     * @param x horizontal coordinate of the point.
     * @param y vertical coordinate of the point.
     */
    public void addPolylineVertex(double x, double y) {
        if (polyline == null) {
            System.out.println("tworze: "+x+" "+y);
            this.polyline = new Polyline(x, y);
        } else {
            System.out.println("dodaje: "+x+" "+y);
            this.polyline.getPoints().add(x);
            this.polyline.getPoints().add(y);
        }
    }

    /**
     * Method returns Polyline of wall.
     * @return Polyline of wall.
     */
    public Polyline getPolyline () {
        return polyline;
    }

    /**
     * Method returns current Floor.
     * @return current Floor.
     */
    public Floor getFloor () {
        return floor;
    }

    /**
     * Method returns size of the Polyline.
     * @return size of the Polyline.
     */
    public int getPolylineSize() {
        if (polyline == null) return 0;
        else if (polyline.getPoints() == null) return 0;
        else return polyline.getPoints().size();
    }
}
