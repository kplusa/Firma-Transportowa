package project.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import project.Class.*;
import project.Utils.DataUtil;
import project.Utils.OpenStreetMapUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AssignOrder extends DataUtil implements Initializable  {


    @FXML
    private AnchorPane APMain;
    @FXML
    public Label name;
    private Socket s;
    private InetAddress ip;
    private DataInputStream dis;
    private DataOutputStream dos;
    private int idOrder,idCourier, counter;
    private String fromBranch, toBranch, location,status;
    @FXML
    private JFXTextField IdOrderTF;
    @FXML
    private JFXTextField IdCourierTF;
    @FXML
    private JFXTextField Distance;
    @FXML
    TableView<Zlecenie> OrderTV;
    @FXML
    TableView<Kurier> CourierTV;
    @FXML
    Label Status;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, Integer> IdOrder;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> FromBranch;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> ToBranch;
    @FXML
    private javafx.scene.control.TableColumn<Kurier, Integer> IdCourier;
    @FXML
    private javafx.scene.control.TableColumn<Kurier, String> Location;
    @FXML
    public Label clientType,information;
    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderMenu.fxml"));
        Parent root = loader.load();
        ForwarderMenuForm forwarderMenuForm= loader.getController();
        forwarderMenuForm.setName(getName(), forwarderMenuForm.name);
        forwarderMenuForm.setClientType(getClientType(), forwarderMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderMenu.fxml"));
        Parent root = loader.load();
        ForwarderMenuForm forwarderMenuForm= loader.getController();
        forwarderMenuForm.setName(getName(), forwarderMenuForm.name);
        forwarderMenuForm.setClientType(getClientType(), forwarderMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    private void count(String A, String B)
    {
        double latA=0;
        double latB=0;
        double lonA=0;
        double lonB=0;
        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(A);
        latA+= coords.get("lat");
        lonA+= coords.get("lon");
        coords = OpenStreetMapUtils.getInstance().getCoordinates(B);

        latB+= coords.get("lat");
        lonB+= coords.get("lon");
        Distance.setText(String.format("%.2f",distance(latA, latB,
                lonA, lonB))+" KM");
    }
    private void fillFields() {
        fromBranch="";
        location="";

        OrderTV.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                if (OrderTV.getSelectionModel().getSelectedItem() != null) {
                    Zlecenie zlecenie = OrderTV.getSelectionModel().getSelectedItem();
                    IdOrderTF.setText(String.valueOf(zlecenie.getID()));
                    fromBranch = zlecenie.getOddzialPoczatkowy();
            }}
            if(!location.equals("")&&!fromBranch.equals("")){
                count(fromBranch,location);
            }
        });
        CourierTV.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                if (CourierTV.getSelectionModel().getSelectedItem() != null) {
                    Kurier kurier = CourierTV.getSelectionModel().getSelectedItem();
                    IdCourierTF.setText(String.valueOf(kurier.getId()));
                    location= kurier.getLocation();
            }}
            if(!location.equals("")&&!fromBranch.equals("")){
                count(fromBranch,location);
            }
        });
    }
    @FXML
    void assign(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(IdCourierTF.getText().isEmpty()||IdOrderTF.getText().isEmpty()||Distance.getText().isEmpty())
                status="Insert error";
               else{
                dos.writeInt(29);
                dos.writeUTF(IdCourierTF.getText());
                dos.writeUTF(IdOrderTF.getText());
                status = dis.readUTF();
            }
                Status.setText(status);
            if (status.equals("Added")) {
                IdOrderTF.setText("");
                IdCourierTF.setText("");
                Distance.setText("");
                Thread.sleep(300);
                Zlecenie.fillOrderTable(OrderTV);
                Zlecenie.fillCourierTable(CourierTV);
            }

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(even ->
                    Status.setText("")
            );
            pause.play();
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdOrder.setCellValueFactory(new PropertyValueFactory<>("ID"));
        FromBranch.setCellValueFactory(new PropertyValueFactory<>("oddzialPoczatkowy"));
        ToBranch.setCellValueFactory(new PropertyValueFactory<>("oddzialKoncowy"));
        IdCourier.setCellValueFactory(new PropertyValueFactory<>("id"));
        Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        try {
            Zlecenie.fillOrderTable(OrderTV);
            Zlecenie.fillCourierTable(CourierTV);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillFields();
    }
}
