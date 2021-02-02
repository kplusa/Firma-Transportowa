package project.Controller;

import project.Utils.DataUtil;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Class.Zlecenie;
import project.Factory.Menu;
import project.Factory.MenuFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourierMenuForm extends MenuFactory implements Initializable, Menu {
    @FXML
    private AnchorPane APMain;
    @FXML
    public Label name;


    @FXML
    public Label clientType;

    @FXML
    void goLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
        LoginForm loginForm = loader.getController();
        LoginForm.allowDrag(root, window);
    }

    @FXML
    void gocurrentorders(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CourierForm.fxml"));
        Parent root = loader.load();
        CourierForm courierForm = loader.getController();
        courierForm.setName(DataUtil.getName(), courierForm.name);
        courierForm.setClientType(DataUtil.getClientType(), courierForm.clientType);
        Zlecenie.filltableCourier(courierForm.CourierTabelForm, courierForm.name);
        courierForm.initializeOrder();
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public Parent loadFXML(FXMLLoader loader, ForwarderMenuForm forwarderMenuForm, JFXTextField mail, String clientType) throws IOException {
        return null;
    }

    @Override
    public Parent loadFXML(FXMLLoader loader, CourierMenuForm courierMenuForm, JFXTextField mail, String clientType) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/CourierMenuForm.fxml"));
        Parent root = loader.load();
        courierMenuForm = loader.getController();
        courierMenuForm.setName(mail.getText(), courierMenuForm.name);
        courierMenuForm.setClientType(clientType, courierMenuForm.clientType);
        return root;
    }

    @Override
    public Parent loadFXML(FXMLLoader loader, ClientMenuForm clientMenuForm, JFXTextField mail, String clientType) throws IOException {
        return null;
    }
}