package project.Class;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import project.Client;

public class DataUtil {
    public String Name;
    public String ClientType;
    @FXML
    private Label name;
    @FXML
    private Label clientType;
    public String getName() {
        return Name;
    }

    public void setName(String name, Label nameL) {
        Name = name;
        this.name = nameL;
        nameL.setText(name);
    }

    public String getClientType() {
        return ClientType;
    }

    public void setClientType(String clientType, Label clientTypeL) {
        ClientType = clientType;
        this.clientType= clientTypeL;
        clientTypeL.setText(clientType);
    }
    public void show()
    {
        System.out.println(Name);
        System.out.println(ClientType);
    }
}
