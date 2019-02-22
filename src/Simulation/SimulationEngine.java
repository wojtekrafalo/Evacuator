package Simulation;

import Model.*;
import com.sun.javafx.geom.Vec2d;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

class SimulationEngine
{
    private Building building;
    private HashMap<Exit, ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge>> shortestPaths;
    private double resistanceMult;
    private double exitAttractionMult;
    private double crowdAttractionMult;
    private double colissionMult;
    private double kilometersPerHourToSimulation;
    private int colissionArea;
    private int crowdArea;



    public boolean isGoing() {

        return isGoing;
    }

    private boolean isGoing;
    public SimulationEngine(HashMap<Exit, ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge>> shortestPaths)
    {
        this.shortestPaths = shortestPaths;
        building = Building.getInstance();
        isGoing = true;
        exitAttractionMult = 0.4;
        resistanceMult = 0.1;
        crowdAttractionMult = 0.2;
        colissionMult = 0.3;
        colissionArea = 2;
        crowdArea = 4;
        kilometersPerHourToSimulation = 0.0092;
    }

    public void moveSimsInBuilding()
    {
        isGoing = false;
        for(int i=0;i<building.numberOfFloors(); i++)
        {
            Floor floor = building.getFloor(i);

            if(moveSimsOnFloor(floor, i))
                isGoing = true;
        }
    }

    private boolean moveSimsOnFloor(Floor floor, int i)
    {
        HashMap<Sim, Floor> simsToRemove = new HashMap<>();
        if(floor.getSims().isEmpty())
            return false;
        int j=0;

        while(j<floor.getSims().size()) {
            Sim sim = floor.getSims().get(j);
            if(moveSim(sim, floor, i))
                j++;
        }
        return true;
    }

    private boolean moveSim(Sim sim, Floor floor, int i)
    {
        LinkedList<Vec2d> vectors = new LinkedList<>();

        Vec2d resistance = getResistance(sim.getV());
        vectors.add(resistance);

        Vec2d directionToExit = getBestDirection(sim, floor);
        vectors.add(directionToExit);

        Vec2d separation =  getSeparation(sim, floor);
        vectors.add(separation);

        Vec2d attraction = getAttraction(sim, floor);
        vectors.add(attraction);

        Vec2d result = summarizeVectors(vectors);
        double ratio = VectorMath.norm(result)/(sim.getAMax()*kilometersPerHourToSimulation);
        if(ratio>1)
            result=VectorMath.multiply(result, 1/ratio);

        result = VectorMath.add(result, sim.getV());
        ratio = VectorMath.norm(result)/(sim.getVMax()*kilometersPerHourToSimulation);
        if(ratio>1)
            result=VectorMath.multiply(result, 1/ratio);

        sim.setV(result);
        System.out.println(i+"; " +sim.getX()+"; "+sim.getY());
        moveBy(result, sim, floor);

        Vertex resultVertex = sim.getVertex();
        Exit headingTo = sim.getHeadingTo();

        if(resultVertex.getExit()==headingTo)
        {
            floor.removeSim(sim);
            Exit correspondingExit = headingTo.getTargetExit();
            if(correspondingExit==null)
            {
                return false;
            }

            Set<Vertex> vertices = correspondingExit.getVertices();
            double x = 0;
            double y = 0;
            for(Vertex vertex: vertices)
            {
                x = vertex.getX();
                y = vertex.getY();
            }

            correspondingExit.getFloor().addSim(x, y);
        }
        return true;
    }

    private Vec2d getAttraction(Sim sim, Floor floor) {
        LinkedList<Sim> nearestSims = getSimsInArea(sim, crowdArea, floor);
        if (nearestSims.isEmpty())
            return new Vec2d(0,0);


        double xAvg=0;
        double yAvg=0;


        for(Sim otherSim:nearestSims)
        {
            xAvg+=otherSim.getX();
            yAvg+=otherSim.getY();
        }
        xAvg = xAvg/nearestSims.size();
        yAvg = yAvg/nearestSims.size();
        Vec2d result = new Vec2d(xAvg-sim.getX(), yAvg-sim.getY());
        double len = VectorMath.norm(result);
        if(len>1)
        {
            result = VectorMath.multiply(result, 1/len);
        }

        return VectorMath.multiply(result, crowdAttractionMult);
    }

    private LinkedList<Sim> getSimsInArea(Sim sim, int radius, Floor floor)
    {
        LinkedList<Sim> nearestSims = new LinkedList<>();
        Vertex simVert = sim.getVertex();
        int xMid = simVert.getX();
        int yMid = simVert.getY();
        for (int i=xMid-radius;i<xMid+radius;i++)
            for(int j=yMid-radius;j<yMid+radius;j++)
            {
                if(Math.sqrt(Math.pow(Math.abs(xMid-i),2) + Math.pow(Math.abs(yMid-j),2)) < colissionArea)
                {
                    Vertex vert = floor.getVertex(i, j);
                    if (vert!=null)
                    {
                        for (Sim otherSim:vert.getStandingSims())
                        {
                            if(otherSim!=sim)
                                nearestSims.add(otherSim);
                        }
                    }
                }
            }
        return nearestSims;
    }


    private Vec2d getSeparation(Sim sim, Floor floor)
    {
        LinkedList<Vec2d> vectors = new LinkedList<>();
        LinkedList<Sim> nearestSims = getSimsInArea(sim, colissionArea, floor);

        if(nearestSims.isEmpty())
            return new Vec2d(0,0);

        for(Sim otherSim:nearestSims)
        {
            Vec2d vectorTo = new Vec2d(1/(sim.getX()-otherSim.getX()), 1/(sim.getY()-otherSim.getY()));
            vectors.add(vectorTo);
        }
        Vec2d result = summarizeVectors(vectors);
        double len = VectorMath.norm(result);
        if(len>1)
        {
            result = VectorMath.multiply(result, 1/len);
        }
        return VectorMath.multiply(result, colissionMult);
    }

    private Vec2d getResistance(Vec2d v)
    {
        Vec2d out = new Vec2d(-v.x, -v.y);
        return VectorMath.multiply(out, resistanceMult);
    }

    private void moveBy(Vec2d vector, Sim sim, Floor floor)
    {
        double x = sim.getX() + vector.x;
        double y = sim.getY() + vector.y;
        floor.moveSim(sim, x, y);
    }

    private Vec2d summarizeVectors(LinkedList<Vec2d> vectors) {
        Vec2d result = new Vec2d(0,0);
        for(Vec2d v: vectors)
        {
            result = VectorMath.add(result, v);
        }
        return result;
    }

    private Vec2d getBestDirection(Sim sim, Floor floor) {
        ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge> path;
        double distance = Double.POSITIVE_INFINITY;
        Exit bestExit = null;
        Vertex source = sim.getVertex();
        if(source==null)
            throw new IllegalArgumentException("Sim not on board");
        for (Exit e: floor.getExits())
        {
            path = shortestPaths.get(e);
            if(path==null)
                continue;
            if(path.getWeight(source) + e.getDistanceToExit() < distance)
            {
                bestExit = e;
                distance = path.getWeight(source) + e.getDistanceToExit();
            }

        }
        path = shortestPaths.get(bestExit);


        if(path == null)
            throw new IllegalArgumentException("Path not exists");
        GraphPath<Vertex, DefaultWeightedEdge> p = path.getPath(source);

        Vertex target;
        if(p.getVertexList().size()-2 >=1)
            target = p.getVertexList().get(p.getVertexList().size()-2);
        else
            target = source;

        double x = target.getX() - source.getX();
        double y = target.getY() - source.getY();
        sim.setHeadingTo(bestExit);
        Vec2d result = new Vec2d(x,y);
        double len = VectorMath.norm(result);
        if(len>1)
        {
            result = VectorMath.multiply(result, 1/len);
        }

        return VectorMath.multiply(result, exitAttractionMult);
    }


}
