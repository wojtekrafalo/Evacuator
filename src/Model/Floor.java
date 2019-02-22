package Model;

import javafx.scene.shape.Polygon;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

/**
 * Project: Evacuator
 * Name: Floor.java
 * Purpose: Stores Floor data in the form of Graph G(V, E)
 * where each vertex is a 0.5m x 0.5m surface
 * and can have at most 8 edges to vertices around.
 * Also stores Data about exits from represented floor
 * and list of sims on that particular floor.
 * Sims are people in building. Their purpose is to evacuate from the building.
 *
 * @author Bartosz Gorski "Alvirx"
 * @version 0.7 17.12.2018
 */
public class Floor extends SimpleWeightedGraph<Vertex, DefaultWeightedEdge>
{
    //TODO make generation for exits target Vertices
    private ArrayList<Exit> exits;
    private LinkedList<Sim> sims;
    private HashMap<Point, Vertex> vertices;
    private Polygon polygon;

    /**
     * Constructor that initializes graph which represents the floor.
     * Walls are represented as a lack of vertex in the place where wall should be.
     */
    public Floor() {
        super(DefaultWeightedEdge.class);
        exits = new ArrayList<>();
        sims = new LinkedList<>();
        vertices = new HashMap<>(10000);
        polygon = null;
    }

    /**
     * Getter made for sims list.
     * @return unmodifiable version of sims list
     */
    public List<Sim> getSims()
    {
        return Collections.unmodifiableList(sims);
    }

    /**
     * Creates sim, adds it to list of sims. Then updates vertex where sim is standing.
     * @param x - X coordinate
     * @param y - Y coordinate
     * @return created Sim
     */
    public Sim addSim(double x, double y)
    {
        Sim sim = new Sim(x, y);
        sims.add(sim);
        Vertex v = getVertex((int)x, (int)y);
        sim.setVertex(v);
        v.addStandingSim(sim);
        return sim;
    }

    /**
     * Removes sim from list and clears its vertex.
     * @param sim - sim to remove
     */
    public void removeSim(Sim sim)
    {
        sims.remove(sim);
        sim.getVertex().removeStandingSim(sim);
        sim.setVertex(null);
    }


    /**
     * Returns vertex of specified coordinates. Vertices in this implementation uses only
     * X and Y coordinates for hashing so this method is indispensable for retrieving informations
     * from graph vertices. (Original Graph implementation does not provide the possibility
     * to get specified vertex since the whole vertex is a key and if we know the whole key
     * we already have it.)
     * @param x - X coordinate of Vertex
     * @param y - Y coordinate of Vertex
     * @return requested Vertex
     */
    public Vertex getVertex(int x, int y)
    {
        return vertices.get(new Point(x,y));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addVertex(Vertex vertex) {
        if(super.addVertex(vertex))
        {
            Point p = new Point(vertex.getX(),vertex.getY());
            vertices.put(p, vertex);
            return true;
        }
        return false;
    }

    /**
     * Adds new vertex in specified place.
     * @param x - X coordinate of new Vertex
     * @param y - Y coordinate of new Vertex
     * @return created Vertex if vertex of specified coordinates was not already in
     * graph, null otherwise.
     */
    public Vertex addVertex(int x, int y) {
        Vertex v = new Vertex(x, y);
        if(super.addVertex(v))
        {
            Point p = new Point(x,y);
            vertices.put(p, v);
            return v;
        }
        else
        {
            return null;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeVertex(Vertex vertex) {
        if(super.removeVertex(vertex))
        {
            Point p = new Point(vertex.getX(),vertex.getY());
            vertices.remove(p);
            return true;
        }
        return false;
    }

    /**
     * Removes vertex with specified coordinates.
     * @param x - X coordinate of Vertex to remove
     * @param y - Y coordinate of Vertex to remove
     * @return true if vertex of specified coordinates was in
     * graph and was successfully removed, false otherwise.
     */
    public boolean removeVertex(int x, int y) {
        Vertex v = new Vertex(x, y);
        if(super.removeVertex(v))
        {
            Point p = new Point(x,y);
            vertices.remove(p, v);
            return true;
        }
        else
        {
            return false;
        }

    }


    /**
     * Getter made for Exits. Exits are sets of vertices that have possibility to
     * move sims to another floor.
     * @return unmodifiable version of exits list
     */
    public List<Exit> getExits()
    {
        return Collections.unmodifiableList(exits);
    }

    /**
     * Creates exit on this floor, on vertices in vertices HashSet, with
     * targetVertex as new vertex in position (x;y). Also creates edge of weight 0.5
     * between each of vertices set and target Vertex
     * @param vertices - vertices that belongs to exit
     * @param x - X - coordinate of exit`s target Vertex
     * @param y - Y - coordinate of exit`s target Vertex
     * @throws ExitAlreadyPlacedInThatPositionException if Exit in that position already exists
     * @throws TargetVertexAlreadyExistException if targetVertex already Exists
     */
    public Exit addExit(HashSet<Vertex> vertices, int x, int y, double[] endsOfExit) throws ExitAlreadyPlacedInThatPositionException, TargetVertexAlreadyExistException {
        Vertex targetVertex = addVertex(x, y);
        Exit exit;
        if(targetVertex!=null)
        {
            for(Vertex v: vertices)
            {
                if(v.getExit()!=null)
                    throw new ExitAlreadyPlacedInThatPositionException();
            }

            exit = new Exit(vertices, this, targetVertex, endsOfExit);
            exits.add(exit);
            for(Vertex v: vertices)
            {
                v.setExit(exit);
                addEdge(v, targetVertex, 1);
            }
            return exit;
        }
        else
        {
            throw new TargetVertexAlreadyExistException();
        }
    }

    /**
     * Removes exit from list of exits and updates each vertex that belongs to that exit.
     * @param exit exit to be removed
     */
    public void removeExit(Exit exit)
    {
        if(exits.contains(exit))
        {
            exits.remove(exit);
            Exit targetExit = exit.getTargetExit();
            if((targetExit)!=null)
                targetExit.setTargetExit(null);
            Vertex targetVertex = exit.getTargetVertex();
            removeVertex(targetVertex);
            for (Vertex v: exit.getVertices()) {
                v.setExit(null);
            }
        }
    }

    /**
     * Adds edge with specified weight between vertices of specified coordinates.
     * @param sourceX horizontal position of source vertex
     * @param sourceY vertical position of source vertex
     * @param targetX horizontal position of target vertex
     * @param targetY vertical position of target vertex
     * @param w weight of new edge
     * @return true if edge was successfully added, false otherwise
     */
    public boolean addEdge(int sourceX, int sourceY, int targetX, int targetY, double w) {
        Vertex source = getVertex(sourceX, sourceY);
        Vertex target = getVertex(targetX, targetY);
        return addEdge(source, target, w);
    }

    /**
     * Counts the distance between two points
     * @param x1 horizontal position of first point
     * @param y1 vertical position of firs point
     * @param x2 horizontal position of second point
     * @param y2 vertical position of second point
     * @return calculated distance
     */
    public static double distance (double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x1-x2),2) + Math.pow(y1-y2,2));
    }

    /**
     * Moves sim on specified postion.
     * @param sim - sim to be moved
     * @param x - new X coordinate
     * @param y - new Y coordinate
     */
    public void moveSim(Sim sim, double x, double y)
    {
        Vertex v = getVertex((int)x, (int)y);
        if(v==null)
            v =sim.getVertex();
        sim.getVertex().removeStandingSim(sim);
        sim.setVertex(v);
        v.addStandingSim(sim);

        sim.setXY(x, y);
    }


    /**
     * Adds edge with specified weight between two specified vertices;
     * @param v1 source vertex
     * @param v2 target vertex
     * @param w weight of new edge
     * @return true if edge was successfully added, false otherwise
     */
    public boolean addEdge(Vertex v1, Vertex v2, double w)
    {
        DefaultWeightedEdge e = new DefaultWeightedEdge();
        setEdgeWeight(e, w);
        if (v1 == null || v2 == null) return false;
        return addEdge(v1, v2, e);
    }

    /**
     * Adds specific Polygon of walls in this floor.
     * @param polygon contour of walls
     */
    public void setPolygon (Polygon polygon) {
        this.polygon = polygon;
    }

    /**
     * Returns Polygon of walls in this floor.
     * @return contour of walls
     */
    public Polygon getPolygon() {
        return polygon;
    }
}