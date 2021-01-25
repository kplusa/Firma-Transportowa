package project.State;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface MenuState {
    public void goMenuState(MouseEvent event) throws IOException;

    public void goBackState(ActionEvent event) throws IOException;
}
