package Simulation;

public class SimulationController implements SimulationControllerInterface
{
    private BuildingPreprocessor preprocessor;
    private SimulationEngine simulationEngine;

    public SimulationController()
    {
        preprocessor = new BuildingPreprocessor();
    }


    @Override
    public void start() {
        preprocessor.preprocess();
        simulationEngine = new SimulationEngine(preprocessor.getShortestPaths());
    }

    @Override
    public void moveSims() {
        if(simulationEngine!=null)
            simulationEngine.moveSimsInBuilding();
    }

    @Override
    public boolean isGoing() {
        if(simulationEngine==null)
            return false;
        else
            return simulationEngine.isGoing();

    }
}
