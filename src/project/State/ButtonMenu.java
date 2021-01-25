package project.State;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ButtonMenu {
    public MenuState menuState;

    public ButtonMenu(String statemenu) {
        setActive(statemenu);
    }


    public void setActive(String statemenu) {
        if (statemenu == "Client")
            menuState = new ClientMenuState();
        else if (statemenu == "Forwarder")
            menuState = new ForwarderMenuState();
        else if (statemenu == "Courier")
            menuState = new CourierMenuState();
        else if (statemenu == "Pack")
            menuState = new AddPackState();
    }


    public void onClick(MouseEvent event) throws IOException {
        menuState.goMenuState(event);
    }

    public void onClick(ActionEvent event) throws IOException {
        menuState.goBackState(event);
    }
}
