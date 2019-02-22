package EditorController;

import Model.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;

/**
 * Project: Evacuator
 * Name: MouseNewExitState.java
 * Purpose: Handles clicking Mouse buttons on Painting Pane in GUI Editor.
 * This class is part of State design pattern and implements MouseEventState.
 * Class is used to create an Exit object after choosing specific button and
 * clicking specific position in Painting Pane and connects reference to it
 * with another Exit
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class MouseNewExitState implements MouseEventState {

    private Exit exit1;

    /**
     * Creates an Exit in Building to other Exit after choosing
     * a specific button and clicking specific position in Painting Pane
     * @param context reference to object, which runs clicked method
     * @param e object of Mouse action
     * @return An Exit, Created by ExitCreator, connected with another Exit
     */
    @Override
    public Object clicked(MouseStateContext context, MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            try {
                ExitCreator.getInstance().setFloor(Creator.getInstance().getFloor());
                ExitContainer exit = ExitCreator.getInstance().addExit(e.getX(), e.getY());

                HashSet<Vertex> set = (HashSet<Vertex>) exit.getSet();
                int x = exit.getX();
                int y = exit.getY();
                double[] ends = exit.getEnds();

                Exit exitNew = Creator.getInstance().getFloor().addExit(set, x, y, ends);

                if (exit1 == null) {
                    exit1 = exitNew;
                } else {
                    Building.getInstance().connectExits(exit1, exitNew);
                    exit1 = null;
                }

//                context.setState(new MouseWallInsideState());
                return exitNew;
            } catch (PolygonDoesNotExistException | ExitAlreadyPlacedInThatPositionException | TargetVertexAlreadyExistException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
