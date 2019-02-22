package EditorController;

import javafx.scene.input.MouseEvent;

/**
 * Project: Evacuator
 * Name: MouseNewSimState.java
 * Purpose: Handles clicking Mouse buttons on Painting Pane in GUI Editor.
 * This class is part of State design pattern and implements MouseEventState.
 * Class is used to create a Sim object after choosing specific button and
 * clicking specific position in Painting Pane.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class MouseNewSimState implements MouseEventState {

    /**
     * Method creates new Sim in position specified by coordinates in Mouse Event
     * @param context reference to object, which runs clicked method
     * @param e object of Mouse action
     * @return Sim created in specific position
     */
    @Override
    public Object clicked(MouseStateContext context, MouseEvent e) {
        double startX = Creator.getInstance().getFloor().getPolygon().getPoints().get(0);
        double startY = Creator.getInstance().getFloor().getPolygon().getPoints().get(1);
        double PILE_SIZE = Creator.getInstance().getPILE_SIZE();

        double vX = (e.getX() - startX)/PILE_SIZE;
        double vY = (e.getY() - startY)/PILE_SIZE;

        System.out.println("SIM: " +vX+ " " +vY);
        return Creator.getInstance().getFloor().addSim(vX, vY);
    }
}
