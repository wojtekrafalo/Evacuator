package EditorController;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Project: Evacuator
 * Name: MouseWallInsideState.java
 * Purpose: Handles clicking Mouse buttons on Painting Pane in GUI Editor.
 * This class is part of State design pattern and implements MouseEventState.
 * Class is used to create a partition wall object inside the Building
 * after choosing specific button and
 * clicking specific position in Painting Pane
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class MouseWallInsideState implements MouseEventState {

    /**
     * Creates or modifies a Polyline of partition wall after choosing
     * a specific button and clicking specific position in Painting Pane
     * @param context reference to object, which runs clicked method
     * @param e object of Mouse action
     * @return A Polyline of partition wall
     */
    @Override
    public Object clicked(MouseStateContext context, MouseEvent e) {
        WallInsideCreator wallInsideCreator = WallInsideCreator.getInstance();

        if (e.getButton() == MouseButton.PRIMARY) {
            wallInsideCreator.addPolylineVertex(e.getX(), e.getY());
            System.out.println("wchodzi: "+e.getX()+" "+e.getY());
            if (wallInsideCreator.getPolylineSize()>=4)
                return wallInsideCreator.getPolyline();

        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (wallInsideCreator.getPolylineSize()>=4)
                return wallInsideCreator.createWallInside();
        }
        return null;
    }
}
