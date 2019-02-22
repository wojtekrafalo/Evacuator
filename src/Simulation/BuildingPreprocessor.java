package Simulation;

import Model.Building;
import Model.Exit;
import Model.Floor;
import Model.Vertex;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;


import java.util.*;

class BuildingPreprocessor
{


    private HashMap<Exit, ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge>> shortestPaths;

    private HashSet<Exit> exitsProcessed;

    BuildingPreprocessor()
    {
        shortestPaths = new HashMap<>();
        exitsProcessed = new HashSet<>();
    }

    HashMap<Exit, ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge>> getShortestPaths() {
        return shortestPaths;
    }

    void preprocess()
    {
        preprocessExits();
    }

    private void preprocessExits()
    {
        LinkedList<Exit> exits = getExitsFromBuilding();
        for (Exit e : exits)
        {
            LinkedList<Exit> exitsToCompute = computeShortestPathsToExit(e);
            exitsProcessed = new HashSet<>();
            while (!exitsToCompute.isEmpty())
            {
                Exit exit = exitsToCompute.poll();
                exitsToCompute.addAll(computeShortestPathsToExit(exit));
            }
        }
    }

    private LinkedList<Exit> computeShortestPathsToExit(Exit exit)
    {
        ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge>  paths =
                findAllShortestPathsOnTheFloor(exit);
        shortestPaths.put(exit, paths);
        List<Exit> otherExits = exit.getFloor().getExits();
        LinkedList<Exit> exitsToCompute = new LinkedList<>();
        for(Exit e:otherExits)
        {
            if(e.getTargetExit()!=null)
            {
                Exit targetExit = e.getTargetExit();
                Vertex vertexOfExit = e.getTargetVertex();
                double distanceToExit = exit.getDistanceToExit() + paths.getWeight(vertexOfExit);
                if(targetExit.getDistanceToExit()>distanceToExit)
                {
                    targetExit.setDistanceToExit(distanceToExit);
                }

                if(!exitsProcessed.contains(e.getTargetExit()))
                {
                    exitsToCompute.add(e.getTargetExit());
                    exitsProcessed.add(e);
                }

            }
        }

        return exitsToCompute;
    }


    private ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultWeightedEdge> findAllShortestPathsOnTheFloor(Exit e)
    {
        Vertex target = e.getTargetVertex();
        DijkstraShortestPath<Vertex, DefaultWeightedEdge> dijkstraAlg =
                new DijkstraShortestPath<>(e.getFloor());

        return dijkstraAlg.getPaths(target);
    }

    private LinkedList<Exit> getExitsFromBuilding()
    {
        Building b = Building.getInstance();
        LinkedList<Exit> exits = new LinkedList<>();
        for(int i=0;i<b.numberOfFloors();i++)
        {
            Floor f = b.getFloor(i);
            for(Exit e:f.getExits())
            {

                if(e.getTargetExit() == null)
                {

                    e.setDistanceToExit(0);
                    exits.add(e);
                }
            }
        }
        return exits;
    }

}