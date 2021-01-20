package project.Controller;

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
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForwarderMenuForm extends DataUtil implements Initializable {
    @FXML
    private AnchorPane APMain;
    @FXML
    public Label name;
    @FXML
    public Label clientType;
    @FXML
    void prices(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderPriceListForm.fxml"));
        Parent root = loader.load();
        ForwarderPriceListForm forwarderPriceListForm= loader.getController();
        forwarderPriceListForm.setName(getName(), forwarderPriceListForm.name);
        forwarderPriceListForm.setClientType(getClientType(), forwarderPriceListForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void branch(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderAddBranch.fxml"));
        Parent root = loader.load();
        AddbranchForm addbranchForm= loader.getController();
        addbranchForm.setName(getName(), addbranchForm.name);
        addbranchForm.setClientType(getClientType(), addbranchForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void asign(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AssignOrderForm.fxml"));
        Parent root = loader.load();
        AssignOrder assignOrder= loader.getController();
        assignOrder.setName(getName(), assignOrder.name);
        assignOrder.setClientType(getClientType(), assignOrder.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void payment(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PaymentForm.fxml"));
        Parent root = loader.load();
        PaymentForm paymentForm= loader.getController();
        paymentForm.setName(getName(), paymentForm.name);
        paymentForm.setClientType(getClientType(), paymentForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goLogin(MouseEvent event) throws IOException {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}