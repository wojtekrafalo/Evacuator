package EditorController;

import Model.*;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Project: Evacuator
 * Name: ExitCreator.java
 * Purpose: This class is used to create an Exit in base of data given by the the user.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.8 06.01.2019
 */
public class ExitCreator{
    private double PILE_SIZE;
    private double doorWidth = 2*PILE_SIZE;
    private double startX;
    private double startY;
    private int targetX;
    private int targetY;
    private Floor floor;
    private Polygon polygon = null;

    private int multiple = 10;

    private static ExitCreator instance = new ExitCreator();

    /**
     * Public Constructor gets current floor from the Creator Semaphore
     * and assigns it to this class.
     */
    private ExitCreator() {
        this.floor = Creator.getInstance().getFloor();
    }

    /**
     * Default method to return an instance to this class.
     * @return instance to this class.
     */
    public static ExitCreator getInstance() {
        return instance;
    }

    /**
     * Method initialize Creator, if new Exit is going to be created.
     * @param PILE_SIZE size of the pile of graph's Vertex.
     * @param multiple describes, how far from the Building should be located the target Vartex.
     * @param polygon Polygon of the Floor.
     */
    public void initialize (double PILE_SIZE, int multiple, Polygon polygon) {
        this.PILE_SIZE = PILE_SIZE;
        this.multiple = multiple;
        this.doorWidth = 2*PILE_SIZE;
        this.polygon = polygon;
        if (polygon != null) {
            startX = polygon.getPoints().get(0);
            startY = polygon.getPoints().get(1);
        }
        floor = Creator.getInstance().getFloor();
    }

    /**
     * Method assigns current floor to this class
     * @param floor current floor, which exits are going to be added to.
     */
    void setFloor (Floor floor) {
        this.floor = floor;
        this.polygon = floor.getPolygon();
        startX = polygon.getPoints().get(0);
        startY = polygon.getPoints().get(1);
    }

    /**
     * Method finds the orthographic projection of a point to a specific line.
     * @param Ax horizontal coordinate of a Point.
     * @param Ay vertical coordinate of a Point.
     * @param nearest array describes two points of a Polygon, which appoint a line.
     * @param list list of Polygon's points.
     * @return array of searched Point's coordinates.
     */
    public static double[] findOrthographicProjection (double Ax, double Ay, int[] nearest, List<Double> list) {
        double Bx = list.get(nearest[0]);
        double By = list.get(nearest[0]+1);
        double Cx = list.get(nearest[1]);
        double Cy = list.get(nearest[1]+1);
        double[] result = {0.0, 0.0};

        if (Bx == Cx) {
            result[0] = Bx;
            result[1] = Ay;
        } else {
            double a = (By-Cy)/(Bx-Cx);
            double b = By - Bx*(By-Cy)/(Bx-Cx);

            double Dx=(Ax+Ay*a-a*b)/(1+a*a);

            result[0] = Dx;
            result[1] = a*Dx+b;

        }
        return result;
    }

    /**
     * Method returns an Exit in base of assigned fields and point chosen by the user.
     * @param x horizontal coordinate of a Point.
     * @param y vertical coordinate of a Point.
     * @return Container of Exit's fields
     * @throws PolygonDoesNotExistException Exception, predicted in case of creating Exit in prohibited position
     */
    ExitContainer addExit(double x, double y) throws PolygonDoesNotExistException {
        int[] nearest;

        List<Double> list = new ArrayList<>(polygon.getPoints());
        if (list.size()<6) throw new PolygonDoesNotExistException();

        nearest = nearestTwo(x, y, list);
        System.out.println("coor:"+x+", "+y+"\nnearest:"+nearest[0]+" "+nearest[1]);

        double[] orthographicPoint = findOrthographicProjection(x, y, nearest, list);

        double[] endsOfExitDoor = findEndsOfExitDoor(orthographicPoint, nearest, list);

        Vertex targetVertex = findTargetVertex(endsOfExitDoor);

        HashSet< Vertex > vertices = findVertices(endsOfExitDoor);

        return new ExitContainer(vertices, targetVertex.getX(), targetVertex.getY(), endsOfExitDoor);
    }

    /**
     * Method finds real position of Target Vertex in base of Exit's location
     * @param endsOfExitDoor array of Exit's coordinates
     * @param multiple describes, how far from the Building
     *                 should Target Vertex ne located
     * @return ral position of Target Vertex
     */
    public double[] findTargetVertexRealPosition(double[] endsOfExitDoor, double multiple) {
        double x1Exit = endsOfExitDoor[0];
        double y1Exit = endsOfExitDoor[1];
        double x2Exit = endsOfExitDoor[2];
        double y2Exit = endsOfExitDoor[3];
        System.out.println("exit1:("+x1Exit+","+y1Exit+"  exit2:("+x2Exit+","+y2Exit+")");

        double xFrom1ToMiddle = (x2Exit - x1Exit)/2;
        double yFrom1ToMiddle = (y2Exit - y1Exit)/2;
        System.out.println("1->M:["+xFrom1ToMiddle+";"+yFrom1ToMiddle+"]");

        double xMedium = (x1Exit + x2Exit)/2;
        double yMedium = (y1Exit + y2Exit)/2;
        System.out.println("M:("+xMedium+","+yMedium+")");

        double xFromMiddleToTarget = (-1)*yFrom1ToMiddle;
        double yFromMiddleToTarget =      xFrom1ToMiddle;
        System.out.println("M->T:["+xFromMiddleToTarget+";"+yFromMiddleToTarget+"]");

        double xTargetReal = xMedium + multiple*xFromMiddleToTarget;
        double yTargetReal = yMedium + multiple*yFromMiddleToTarget;

        System.out.println("T:("+xTargetReal+","+yTargetReal+")");

        if (polygon.contains(xTargetReal,yTargetReal)) {
            xTargetReal = xMedium - multiple*xFromMiddleToTarget;
            yTargetReal = yMedium - multiple*yFromMiddleToTarget;
        }

        System.out.println("T:("+xTargetReal+","+yTargetReal+")");

        return new double[]{xTargetReal, yTargetReal};
    }

    /**
     * Method finds Target Vertex's position in Graph of Floor in base of Exit's location
     * @param endsOfExitDoor array of Exit's coordinates
     * @return ral position of Target Vertex
     */
    public Vertex findTargetVertex(double[] endsOfExitDoor) {

        boolean exists = true;
        Vertex vTarget = null;

        double i = 1.0;
        double step = 0.1;
        while (exists) {
            double[] tR = findTargetVertexRealPosition(endsOfExitDoor, multiple*i);

            double xTargetReal = tR[0];
            double yTargetReal = tR[1];
            targetX = (int)((xTargetReal - startX)/PILE_SIZE);
            targetY = (int)((yTargetReal - startY)/PILE_SIZE);
            vTarget = new Vertex(targetX, targetY);

            exists = floor.containsVertex(vTarget);
            i = i + step;
        }

        return vTarget;
    }

    /**
     * Method finds vertices in Floor's graph, which are located close to the line of Exit
     * @param endsOfExitDoor array of Exit's coordinates
     * @return set of offsets
     */
    public HashSet<Vertex> findVertices(double[] endsOfExitDoor) {

        int xExit1 = (int)((endsOfExitDoor[0] - startX)/PILE_SIZE);
        int yExit1 = (int)((endsOfExitDoor[1] - startY)/PILE_SIZE);
        System.out.println("exit1:("+ xExit1 +","+ yExit1 +")");

        int xExit2 = (int)((endsOfExitDoor[2] - startX)/PILE_SIZE);
        int yExit2 = (int)((endsOfExitDoor[3] - startY)/PILE_SIZE);
        System.out.println("exit2:(" +xExit2+ "," +yExit2+ ")");

        HashSet<Vertex> vertices = new HashSet<>();

        if (endsOfExitDoor[2]-endsOfExitDoor[0] != 0) {
            double tangens = (endsOfExitDoor[3] -endsOfExitDoor[1]) / (endsOfExitDoor[2]-endsOfExitDoor[0]);
            double step = (endsOfExitDoor[0] <= endsOfExitDoor[2]) ? PILE_SIZE : -PILE_SIZE;

            System.out.println("tg:" +tangens+ ", step:" +step);
            for (double x = endsOfExitDoor[0]; x <= endsOfExitDoor[2]; x = x + step) {

                double y = endsOfExitDoor[1] + tangens*(x-endsOfExitDoor[0]);
                System.out.print("x:" +x+ ",  y:" +y);

                int xVer = (int)((x - startX)/PILE_SIZE);
                int yVer = (int)((y - startY)/PILE_SIZE);


                if (floor.getVertex(xVer, yVer) != null) {
                    System.out.println("  xVer:" +xVer+ "  yVer:" +yVer);
                    vertices.add(floor.getVertex(xVer, yVer));

                } else if (floor.getVertex(xVer, yVer+1) != null){
                    System.out.println("  xVer:" +xVer+ "  yVer:" +(yVer+1));
                    vertices.add(floor.getVertex(xVer, yVer+1));

                } else if (floor.getVertex(xVer, yVer-1) != null) {
                    System.out.println("  xVer:" +xVer+ "  yVer:" +(yVer-1));
                    vertices.add(floor.getVertex(xVer, yVer-1));
                }


                if (floor.getVertex(xVer+1, yVer) != null) {
                    System.out.println("  xVer:" +(xVer+1)+ "  yVer:" +yVer);
                    vertices.add(floor.getVertex(xVer+1, yVer));

                } else if (floor.getVertex(xVer+1, yVer+1) != null){
                    System.out.println("  xVer:" +(xVer+1)+ "  yVer:" +(yVer+1));
                    vertices.add(floor.getVertex(xVer+1, yVer+1));

                } else if (floor.getVertex(xVer+1, yVer-1) != null) {
                    System.out.println("  xVer:" +(xVer+1)+ "  yVer:" +(yVer-1));
                    vertices.add(floor.getVertex(xVer+1, yVer-1));
                }


                if (floor.getVertex(xVer-1, yVer) != null) {
                    System.out.println("  xVer:" +(xVer-1)+ "  yVer:" +yVer);
                    vertices.add(floor.getVertex(xVer-1, yVer));

                } else if (floor.getVertex(xVer-1, yVer+1) != null){
                    System.out.println("  xVer:" +(xVer-1)+ "  yVer:" +(yVer+1));
                    vertices.add(floor.getVertex(xVer-1, yVer+1));

                } else if (floor.getVertex(xVer-1, yVer-1) != null) {
                    System.out.println("  xVer:" +(xVer-1)+ "  yVer:" +(yVer-1));
                    vertices.add(floor.getVertex(xVer-1, yVer-1));
                }
            }
        }


        if (endsOfExitDoor[3]-endsOfExitDoor[1] != 0) {
            double tangens = (endsOfExitDoor[2] - endsOfExitDoor[0]) / (endsOfExitDoor[3] - endsOfExitDoor[1]);
            double step = (endsOfExitDoor[1] <= endsOfExitDoor[3]) ? PILE_SIZE : -PILE_SIZE;

            System.out.println("tg:" +tangens+ ", step:" +step);
            for (double y = endsOfExitDoor[1]; y <= endsOfExitDoor[3]; y = y + step) {

                double x = endsOfExitDoor[0] + (y - endsOfExitDoor[1]) * tangens;
                System.out.print("x:" + x + ",  y:" + y);

                int xVer = (int) ((x - startX) / PILE_SIZE);
                int yVer = (int) ((y - startY) / PILE_SIZE);

                if (floor.getVertex(xVer, yVer) != null) {
                    System.out.println("  xVer:" + xVer + "  yVer:" + yVer);
                    vertices.add(floor.getVertex(xVer, yVer));

                } else if (floor.getVertex(xVer + 1, yVer) != null) {
                    System.out.println("  xVer:" + (xVer + 1) + "  yVer:" + yVer);
                    vertices.add(floor.getVertex(xVer + 1, yVer));

                } else if (floor.getVertex(xVer - 1, yVer) != null) {
                    System.out.println("  xVer:" + (xVer - 1) + "  yVer:" + yVer);
                    vertices.add(floor.getVertex(xVer - 1, yVer));
                }

                if (floor.getVertex(xVer, yVer+1) != null) {
                    System.out.println("  xVer:" +(xVer)+ "  yVer:" +(yVer+1));
                    vertices.add(floor.getVertex(xVer, yVer+1));

                } else if (floor.getVertex(xVer+1, yVer+1) != null){
                    System.out.println("  xVer:" +(xVer+1)+ "  yVer:" +(yVer+1));
                    vertices.add(floor.getVertex(xVer+1, yVer+1));

                } else if (floor.getVertex(xVer-1, yVer+1) != null) {
                    System.out.println("  xVer:" +(xVer-1)+ "  yVer:" +(yVer+1));
                    vertices.add(floor.getVertex(xVer-1, yVer+1));
                }

                if (floor.getVertex(xVer, yVer-1) != null) {
                    System.out.println("  xVer:" +(xVer)+ "  yVer:" +(yVer-1));
                    vertices.add(floor.getVertex(xVer, yVer-1));

                } else if (floor.getVertex(xVer+1, yVer-1) != null){
                    System.out.println("  xVer:" +(xVer+1)+ "  yVer:" +(yVer-1));
                    vertices.add(floor.getVertex(xVer+1, yVer-1));

                } else if (floor.getVertex(xVer-1, yVer-1) != null) {
                    System.out.println("  xVer:" +(xVer-1)+ "  yVer:" +(yVer-1));
                    vertices.add(floor.getVertex(xVer-1, yVer-1));
                }
            }
        }
        return vertices;
    }


    /**
     * Method finds array of Exit's coordinates
     * @param exitPoint point chosen by the user
     * @param nearest position of nearest points in Polygon set
     * @param list set of Polygon's points.
     * @return array of Exit's coordinates
     */
    public double[] findEndsOfExitDoor(double[] exitPoint, int[] nearest, List<Double> list) {
        double x1 = list.get(nearest[0]);
        double y1 = list.get(nearest[0]+1);
        double x2 = list.get(nearest[1]);
        double y2 = list.get(nearest[1]+1);

        double xe = exitPoint[0];
        double ye = exitPoint[1];

        double L1 = Math.sqrt( (x1-xe)*(x1-xe) + (y1-ye)*(y1-ye) );
        double L2 = Math.sqrt( (x2-xe)*(x2-xe) + (y2-ye)*(y2-ye) );
        double L1e= doorWidth;
        double L2e= doorWidth;

        double x1e = (x1-xe)*L1e/L1;
        double y1e = (y1-ye)*L1e/L1;

        double x2e = (x2-xe)*L2e/L2;
        double y2e = (y2-ye)*L2e/L2;

        return new double[]{xe + x1e, ye + y1e, xe + x2e, ye + y2e};
    }

    /**
     * Method finds two points in Polygon set nearest to the point chosen by the user.
     * @param x horizontal coordinate of point chosen by the user.
     * @param y vertical coordinate of point chosen by the user.
     * @param list set of Polygon's points.
     * @return array with positions of the nearest points in Polygon's list
     */
    public static int[] nearestTwo (double x, double y, List<Double> list) {
        int[] nearest = {0,2};
        if (Floor.distance(list.get(0), list.get(1), x, y) > Floor.distance(list.get(2), list.get(3), x, y)) {
            nearest[0]=2;
            nearest[1]=0;
        }

        for (int i=4; i<list.size(); i+=2) {
            double x2 = list.get(i);
            double y2 = list.get(i+1);
            double dis = Floor.distance(x, y, x2, y2);
            if (dis < Floor.distance(x, y, list.get(nearest[0]), nearest[0]+1)) {
                nearest[1] = nearest[0];
                nearest[0] = i;
            } else if (dis < Floor.distance(x, y, list.get(nearest[1]), nearest[1]+1)) {
                nearest[1] = i;
            }
        }
        return nearest;
    }
}