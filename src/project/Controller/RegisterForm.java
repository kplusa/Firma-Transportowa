package project.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterForm implements Initializable {
    double x = 0, y = 0;
    static final int HBoxXMin = 432;
    static final int HBoxXMax = 492;
    static final int HBoxYMin = 14;
    static final int HBoxYMax = 44;
    private Socket s;
    private InetAddress ip;
    private DataInputStream dis;
    private DataOutputStream dos;

    @FXML
    private AnchorPane APMain;
    @FXML
    private Label status;
    @FXML
    private JFXTextField mail;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXPasswordField pass2;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXComboBox type;
    @FXML
    private JFXTextField firstname;
    @FXML
    private JFXTextField lastname;
    @FXML
    private JFXTextField city;
    @FXML
    private JFXTextField street;
    @FXML
    private JFXTextField number;
    @FXML
    private JFXTextField code;
    private ObservableList list = FXCollections.observableArrayList();

    @FXML
    void closeAction(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void minAction(MouseEvent event) {
        Client.stage.setIconified(true);
    }

    @FXML
    void MakeDraggable() {
        APMain.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        APMain.setOnMouseDragged(event -> {
            if (!(x >= HBoxXMin && x <= HBoxXMax) || !(y >= HBoxYMin && y <= HBoxYMax)) {
                Client.stage.setX(event.getScreenX() - x);
                Client.stage.setY(event.getScreenY() - y);
            }
        });
    }

    @FXML
    void goLogin(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LoginForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void register(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                status.setText("Brak polaczenia z serwerem");
            }
            if (mail.getText().isEmpty() || pass.getText().isEmpty() || pass2.getText().isEmpty() || phone.getText().isEmpty() | type.getValue().toString().isEmpty() || firstname.getText().isEmpty() || lastname.getText().isEmpty() || city.getText().isEmpty() || street.getText().isEmpty() || number.getText().isEmpty() || code.getText().isEmpty()) {
                status.setText("Wypelnij wszystkie pola");
            } else {
                dos.writeInt(2);
                dos.writeUTF(mail.getText());
                dos.writeUTF(pass.getText());
                dos.writeUTF(pass2.getText());
                dos.writeInt(Integer.valueOf(phone.getText()));
                dos.writeUTF(type.getValue().toString());
                dos.writeUTF(firstname.getText());
                dos.writeUTF(lastname.getText());
                dos.writeUTF(city.getText());
                dos.writeUTF(street.getText());
                dos.writeUTF(number.getText());
                dos.writeUTF(code.getText());
                String st = dis.readUTF();
                status.setText(st);
                dis.close();
                dos.close();
                s.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList(List.of("Klient","Kurier","Spedytor"));
        type.setItems(list);
        MakeDraggable();
    }
}
