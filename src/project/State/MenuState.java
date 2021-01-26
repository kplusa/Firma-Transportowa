package project.State;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public interface MenuState {
    void goMenuState(MouseEvent event) throws IOException;

    void goBackState(ActionEvent event) throws IOException;
}
