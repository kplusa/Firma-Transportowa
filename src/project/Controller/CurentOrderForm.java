package project.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Class.Cennik;
import project.State.ButtonMenu;
import project.Utils.DataUtil;
import project.Class.Zlecenie;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CurentOrderForm extends DataUtil implements Initializable {

    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label name;
    @FXML
    public Label clientType;
    @FXML
    TableView<Zlecenie> CurrentOrder;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, Integer> OrderNumber;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> Status;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> Courier;

    @FXML
    void back(ActionEvent event) throws IOException {
        buttonMenu.onClick(event);
    }
    @FXML
    void goMenu(MouseEvent event) throws IOException {
        buttonMenu.onClick(event);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OrderNumber.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Courier.setCellValueFactory(new PropertyValueFactory<>("Kurier"));
    }

}
