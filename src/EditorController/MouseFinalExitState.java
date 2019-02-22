package EditorController;

import Model.Exit;
import Model.ExitAlreadyPlacedInThatPositionException;
import Model.TargetVertexAlreadyExistException;
import Model.Vertex;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.HashSet;

/**
 * Project: Evacuator
 * Name: MouseFinalExitState.java
 * Purpose: Handles clicking Mouse buttons on Painting Pane in GUI Editor.
 * This class is part of State design pattern and implements MouseEventState.
 * Class is used to create an Exit object after choosing specific button and
 * clicking specific position in Painting Pane
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class MouseFinalExitState implements MouseEventState {

    /**
     * Creates an Exit from Building to the outside after choosing
     * a specific button and clicking specific position in Painting Pane
     * @param context reference to object, which runs clicked method
     * @param e object of Mouse action
     * @return An Exit, Created by ExitCreator
     * @throws ExitAlreadyPlacedInThatPositionException Exception, predicted
     * in case of creating Exit in prohibited position
     */
    @Override
    public Object clicked(MouseStateContext context, MouseEvent e) throws ExitAlreadyPlacedInThatPositionException{
        if (e.getButton() == MouseButton.PRIMARY) {
            try {
                ExitCreator.getInstance().setFloor(Creator.getInstance().getFloor());
                ExitContainer exit = ExitCreator.getInstance().addExit(e.getX(), e.getY());

                HashSet<Vertex> set = exit.getSet();
                int x = exit.getX();
                int y = exit.getY();
                double[] ends = exit.getEnds();

                return Creator.getInstance().getFloor().addExit(set, x, y, ends);

            } catch (PolygonDoesNotExistException | TargetVertexAlreadyExistException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
}
