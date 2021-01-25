package project.State;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.Class.Zlecenie;
import project.Controller.AddOrderform;
import project.Controller.ClientMenuForm;
import project.Utils.DataUtil;

import java.io.IOException;

public class AddPackState extends DataUtil implements MenuState {
    @Override
    public void goMenuState(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientMenuForm.fxml"));
        Parent root = loader.load();
        ClientMenuForm clientMenuForm = loader.getController();
        clientMenuForm.setName(getName(), clientMenuForm.name);
        clientMenuForm.setClientType(getClientType(), clientMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void goBackState(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientOrder.fxml"));
        Parent root = loader.load();
        AddOrderform addOrderform = loader.getController();
        addOrderform.setName(getName(), addOrderform.name);
        addOrderform.setClientType(getClientType(), addOrderform.clientType);
        Zlecenie.fill_table(addOrderform.Orders);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
}
