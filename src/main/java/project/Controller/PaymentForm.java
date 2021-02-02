package project.Controller;


import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import project.Class.Kurier;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentForm extends DataUtil implements Initializable {
    ButtonMenu buttonMenu = new ButtonMenu(getClientType());

    @FXML
    public Label name;
    @FXML
    public Label clientType;
    @FXML
    public TableView<Kurier> Table;
    @FXML
    public TableColumn<Kurier, Integer> ID;
    @FXML
    public TableColumn<Kurier, String> Courier;
    @FXML
    public TableColumn<Kurier, Integer> Quantity;
    @FXML
    public JFXTextField CourierLabel;
    @FXML
    public JFXTextField PercentageLabel;
    @FXML
    public Label state, information;
    private Socket s;
    private InetAddress ip;
    private DataInputStream dis;
    private DataOutputStream dos;

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

        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Courier.setCellValueFactory(new PropertyValueFactory<>("imie"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("ilosc"));
        try {
            fill_table();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                if (Table.getSelectionModel().getSelectedItem() != null) {
                    Kurier kurier = Table.getSelectionModel().getSelectedItem();
                    CourierLabel.setText(String.valueOf(kurier.getId()));
                }
            }
        });
    }

    @FXML
    public void add(ActionEvent event) {
        if (!PercentageLabel.getText().equals("")) {
            try {
                try {
                    ip = InetAddress.getByName("localhost");
                    s = new Socket(ip, 5057);
                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dos.writeInt(33);
                dos.writeInt(Integer.valueOf(PercentageLabel.getText()));
                dos.writeInt(Table.getSelectionModel().getSelectedItem().getId());
                state.setText(dis.readUTF());
                dis.close();
                dos.close();
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            state.setText("Wprowadz wartosc do Percentage");
        }
    }

    @FXML
    public ObservableList<Kurier> fill_table() throws IOException {
        ObservableList<Kurier> KurierList = FXCollections.observableArrayList();
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(32);
            int counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                String tmpstring = dis.readUTF();
                int id = Integer.valueOf(tmpstring);
                String imie = dis.readUTF();
                tmpstring = dis.readUTF();
                int ilosc = Integer.valueOf(tmpstring);
                KurierList.add(new Kurier(id, imie, ilosc));
            }
            Table.setItems(KurierList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return KurierList;
    }

}