package project.Controller;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Class.Oddzial;
import project.Class.Zlecenie;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddbranchForm extends DataUtil implements Initializable {
    @FXML
    public Label name;
    @FXML
    public JFXTextField PlaceLabel;
    @FXML
    public Label clientType;
    @FXML
    public TableView PlaceTable;
    @FXML
    public TableColumn Place;
    @FXML
    public Label state;


    @FXML
    void goMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderMenu.fxml"));
        Parent root = loader.load();
        ForwarderMenuForm forwarderMenuForm = loader.getController();
        forwarderMenuForm.setName(getName(), forwarderMenuForm.name);
        forwarderMenuForm.setClientType(getClientType(), forwarderMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ForwarderMenuForm.class.getResource("../View/ForwarderMenu.fxml"));
        Parent root = loader.load();
        ForwarderMenuForm forwarderMenuForm = loader.getController();
        forwarderMenuForm.setName(getName(), forwarderMenuForm.name);
        forwarderMenuForm.setClientType(getClientType(), forwarderMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Place.setCellValueFactory(new PropertyValueFactory<>("miejscowosc"));
        try {
            Oddzial.fill_table(PlaceTable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void add(ActionEvent event) throws IOException {
        if (PlaceLabel.getText().isEmpty()) {
            state.setText("Podaj oddzial");
        } else {
            Oddzial.addAction(PlaceLabel, state);
        }
        Oddzial.fill_table(PlaceTable);
    }
}
