package EditorController;

import Model.Building;
import Model.Floor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Project: Evacuator
 * Name: MouseWallOutState.java
 * Purpose: Handles clicking Mouse buttons on Painting Pane in GUI Editor.
 * This class is part of State design pattern and implements MouseEventState.
 * Class is used to create a contour of outside wall of the Building
 * after choosing specific series of button.
 * clicking specific position in Painting Pane
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class MouseWallOutState implements MouseEventState {

    /**
     * Creates or modifies a Polygon of outside wall after choosing
     * a specific button and clicking specific series of points in Painting Pane.
     * Then Creates a Floor inside this Polygon.
     * @param context reference to object, which runs clicked method.
     * @param e object of Mouse action.
     * @return A Floor created inside the Polygon.
     */
    @Override
    public Object clicked(MouseStateContext context, MouseEvent e) {
        FloorCreator floorCreator = FloorCreator.getInstance();

        if (e.getButton() == MouseButton.PRIMARY) {
            floorCreator.addPolygonVertex(e.getX(), e.getY());
            System.out.println("wchodzi: "+e.getX()+" "+e.getY());

        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (floorCreator.getPolygonSize()>=6) {
                Floor f = floorCreator.createGraph();
                Creator.getInstance().setFloor(f);
                Building.getInstance().addFloor(f);
                f.setPolygon(floorCreator.getPolygon());
                context.setState(new MouseWallInsideState());

                return f;
            }
        }
        return null;
    }
}