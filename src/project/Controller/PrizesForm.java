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
import project.Utils.DataUtil;
import project.Class.Doplata;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class PrizesForm extends DataUtil implements Initializable {
    @FXML
    public Label name;


    @FXML
    public Label clientType;

    @FXML
    TableView<Cennik> PriceList;
    @FXML
    TableView<Doplata> AditionalPriceList;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, String> Dimension;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, Float> Amount;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, String> Description;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, Integer> Lm;
    @FXML
    private javafx.scene.control.TableColumn<Doplata, String> Type;
    @FXML
    private javafx.scene.control.TableColumn<Doplata, Integer> AditionalAmount;
    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientMenuForm.fxml"));
        Parent root = loader.load();
        ClientMenuForm clientMenuForm= loader.getController();
        clientMenuForm.setName(getName(), clientMenuForm.name);
        clientMenuForm.setClientType(getClientType(), clientMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientMenuForm.fxml"));
        Parent root = loader.load();
        ClientMenuForm clientMenuForm= loader.getController();
        clientMenuForm.setName(getName(), clientMenuForm.name);
        clientMenuForm.setClientType(getClientType(), clientMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Dimension.setCellValueFactory(new PropertyValueFactory<>("Gabaryt"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Kwota"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Opis"));
        Lm.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        Type.setCellValueFactory(new PropertyValueFactory<>("TypDoplaty"));
        AditionalAmount.setCellValueFactory(new PropertyValueFactory<>("KwotaD"));
        try {
            Cennik.fillPriceList(PriceList);
            Doplata.fillTableAdditional(AditionalPriceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}