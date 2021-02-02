package project.State;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import project.Controller.CourierMenuForm;
import project.Utils.DataUtil;

import java.io.IOException;

public class CourierMenuState extends DataUtil implements MenuState {

    @Override
    public void goMenuState(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CourierMenuForm.fxml"));
        Parent root = loader.load();
        CourierMenuForm courierMenuForm = loader.getController();
        courierMenuForm.setName(getName(), courierMenuForm.name);
        courierMenuForm.setClientType(getClientType(), courierMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void goBackState(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CourierMenuForm.fxml"));
        Parent root = loader.load();
        CourierMenuForm courierMenuForm = loader.getController();
        courierMenuForm.setName(getName(), courierMenuForm.name);
        courierMenuForm.setClientType(getClientType(), courierMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

}
