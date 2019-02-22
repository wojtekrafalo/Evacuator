package Simulation;

public interface SimulationControllerInterface
{
    /**
     * Prepares simulationEngine.
     */
    void start();

    /**
     * If simulationEngine is properly started updates all sims position.
     */
    void moveSims();

    /**
     * Checks if simulation is going.
     * @return true if simulation is not finished, false otherwise.
     */
    boolean isGoing();

}
