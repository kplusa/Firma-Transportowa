package project.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import project.Class.Zlecenie;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CourierForm extends DataUtil implements Initializable {
    @FXML
    public Label name;
    private String adresP, adresK, status, tmpstring, datas;
    private int ilosc;
    private Zlecenie selectedZlecenie;


    @FXML
    private JFXButton OKCourier;
    @FXML
    private JFXComboBox<String> StatusSelection;
    @FXML
    private JFXComboBox<String> OrderSelection;
    @FXML
    public Label clientType;
    @FXML
    TableView<Zlecenie> CourierTabelForm;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, Integer> Id;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> DataNadania;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> AdresP;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> AdresK;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> Status;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, Integer> Amount;
    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CourierMenuForm.fxml"));
        Parent root = loader.load();
        CourierMenuForm courierMenuForm= loader.getController();
        courierMenuForm.setName(getName(), courierMenuForm.name);
        courierMenuForm.setClientType(getClientType(), courierMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CourierMenuForm.fxml"));
        Parent root = loader.load();
        CourierMenuForm courierMenuForm= loader.getController();
        courierMenuForm.setName(getName(), courierMenuForm.name);
        courierMenuForm.setClientType(getClientType(), courierMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void aktualizuj(MouseEvent event) throws IOException, InterruptedException {
        if (CourierTabelForm.getSelectionModel().getSelectedItem() != null
                && StatusSelection.getSelectionModel().getSelectedItem() != null)
        {
            String testouput = StatusSelection.getSelectionModel().getSelectedItem();
            System.out.println(testouput);
            selectedZlecenie = CourierTabelForm.getSelectionModel().getSelectedItem();
            System.out.println(selectedZlecenie.ID);
            connectClient();
            dos.writeInt(42);
            dos.writeUTF(testouput);
            dos.writeInt(selectedZlecenie.ID);
            dis.close();
            dos.close();
            s.close();
            Thread.sleep(300);
            Zlecenie.filltableCourier(CourierTabelForm,name);
        }
        if (OrderSelection.getSelectionModel().getSelectedItem() != null)
        {
            connectClient();
            tmpstring = OrderSelection.getSelectionModel().getSelectedItem();

            dos.writeInt(44);
            dos.writeUTF(tmpstring);
            dos.writeUTF(name.getText());
            dis.close();
            dos.close();
            s.close();
            Thread.sleep(300);
        }
    }
    public void initializeOrder()
    {
        try {
            connectClient();
            dos.writeInt(43);
            dos.writeUTF(name.getText());
            var counter = dis.readInt();
            for (int i = 1;i <= counter; i++)
            {
                tmpstring = dis.readUTF();
                OrderSelection.getSelectionModel().select(tmpstring);
                System.out.println(tmpstring);
            }
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        AdresP.setCellValueFactory(new PropertyValueFactory<>("AdresPoczatkowy"));
        AdresK.setCellValueFactory(new PropertyValueFactory<>("AdresKoncowy"));
        DataNadania.setCellValueFactory(new PropertyValueFactory<>("DataNadania"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        StatusSelection.getItems().add("Odebrane");
        StatusSelection.getItems().add("Dostarczone");
        OrderSelection.getItems().add("Początkowy");
        OrderSelection.getItems().add("Końcowy");
        OrderSelection.getItems().add("Pomiędzy odziałami");
    }


}