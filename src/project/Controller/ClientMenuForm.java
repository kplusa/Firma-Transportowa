package project.Controller;

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
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuForm extends DataUtil implements Initializable {
    @FXML
    private AnchorPane APMain;
    @FXML
    public Label name;


    @FXML
    public Label clientType;

    @FXML
    void goLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LoginForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
        LoginForm loginForm=loader.getController();
        loginForm.allowDrag(root,window);
    }
    @FXML
    void goprizes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PriceListForm.fxml"));
        Parent root = loader.load(); Scene scene = new Scene(root);
        PrizesForm prizesForm= loader.getController();
        prizesForm.setName(getName(), prizesForm.name);
        prizesForm.setClientType(getClientType(), prizesForm.clientType);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goaddorder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientOrder.fxml"));
        Parent root = loader.load(); Scene scene = new Scene(root);
        AddOrderform addOrderform= loader.getController();
        addOrderform.setName(getName(), addOrderform.name);
        addOrderform.setClientType(getClientType(), addOrderform.clientType);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void gocurrentorders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CurrentOrdersForm.fxml"));
        Parent root = loader.load();Scene scene = new Scene(root);
        CurentOrderForm curentOrderForm= loader.getController();
        curentOrderForm.setName(getName(), curentOrderForm.name);
        curentOrderForm.setClientType(getClientType(), curentOrderForm.clientType);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
