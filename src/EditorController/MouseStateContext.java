package EditorController;

import Model.ExitAlreadyPlacedInThatPositionException;
import javafx.scene.input.MouseEvent;

/**
 * Project: Evacuator
 * Name: MouseStateContext.java
 * Purpose: Handles clicking Mouse buttons on Painting Pane in GUI Editor.
 * This class is part of State design pattern and implements MouseEventState.
 * Class is used to create an Exit object after choosing specific button and
 * clicking specific position in Painting Pane and connects reference to it
 * with another Exit
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public class MouseStateContext {
    private MouseEventState state;

    public MouseStateContext () {
        setState(new MouseWallOutState());
    }

    /**
     * Method used to set current State
     * @param newState state, which is set in context of State pattern
     */
    public void setState(final MouseEventState newState) {
        state = newState;
    }

    /**
     * Method causes method of the same name in current State.
     * Is is used to answer in specific Mouse Actions.
     * @param event object of Mouse action
     * @return object, as a result of work of specific State
     * @throws ExitAlreadyPlacedInThatPositionException Exception, predicted
     * in case of creating Exit in prohibited position
     */
    public Object clicked(MouseEvent event) throws ExitAlreadyPlacedInThatPositionException {
        return state.clicked(this, event);
    }
}