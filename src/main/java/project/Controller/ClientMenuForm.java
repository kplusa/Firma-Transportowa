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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Class.Zlecenie;
import project.Factory.Menu;
import project.Factory.MenuFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuForm extends MenuFactory implements Initializable, Menu {
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
    void goprizes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PriceListForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        PrizesForm prizesForm = loader.getController();
        prizesForm.setName(DataUtil.getName(), prizesForm.name);
        prizesForm.setClientType(DataUtil.getClientType(), prizesForm.clientType);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void goaddorder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ClientOrder.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        AddOrderform addOrderform = loader.getController();
        addOrderform.setName(DataUtil.getName(), addOrderform.name);
        addOrderform.setClientType(DataUtil.getClientType(), addOrderform.clientType);
        Zlecenie.fill_table(addOrderform.Orders);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void gocurrentorders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/CurrentOrdersForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        CurentOrderForm curentOrderForm = loader.getController();
        curentOrderForm.setName(DataUtil.getName(), curentOrderForm.name);
        curentOrderForm.setClientType(DataUtil.getClientType(), curentOrderForm.clientType);
        Zlecenie.filltableCurrentOrder(curentOrderForm.CurrentOrder, curentOrderForm.name);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public Parent loadFXML(FXMLLoader loader, ClientMenuForm clientMenuForm, JFXTextField mail, String clientType) throws IOException {
        loader = new FXMLLoader(getClass().getResource("/View/ClientMenuForm.fxml"));
        Parent root = loader.load();
        clientMenuForm = loader.getController();
        clientMenuForm.setName(mail.getText(), clientMenuForm.name);
        clientMenuForm.setClientType(clientType, clientMenuForm.clientType);
        return root;
    }

    @Override
    public Parent loadFXML(FXMLLoader loader, CourierMenuForm courierMenuFormMenuForm, JFXTextField mail, String clientType) throws IOException {
        return null;
    }

    @Override
    public Parent loadFXML(FXMLLoader loader, ForwarderMenuForm forwarderMenuForm, JFXTextField mail, String clientType) throws IOException {
        return null;
    }
}
