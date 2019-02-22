package EditorController;

import Model.ExitAlreadyPlacedInThatPositionException;
import javafx.scene.input.MouseEvent;

/**
 * Project: Evacuator
 * Name: MouseEventState.java
 * Purpose: Handles actions predicted in GUI Editor, such as clicking Mouse buttons.
 * This class is part of State design pattern.
 * @author Wojciech Rafałowski "wojtekrafalo"
 * @version 0.1 06.12.2018
 */
public interface MouseEventState {

    /**
     * Method, extended by all states. Is used to answer in specific Mouse Actions
     * @param context reference to object, which runs clicked method
     * @param e object of Mouse action
     * @return object, as a result of work of specific State
     * @throws ExitAlreadyPlacedInThatPositionException Exception, predicted
     * in case of creating Exit in prohibited position
     */
    Object clicked(MouseStateContext context, MouseEvent e) throws ExitAlreadyPlacedInThatPositionException;
}
