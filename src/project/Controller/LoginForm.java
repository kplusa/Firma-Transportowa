package project.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginForm implements Initializable {
    double x=0, y=0;
    static final int HBoxXMin=432;
    static final int HBoxXMax=492;
    static final int HBoxYMin=14;
    static final int HBoxYMax=44;
    private String st;
    private Socket s;
    private InetAddress ip;
    private DataInputStream dis;
    private DataOutputStream dos;
    private static double[]  offset_XY;
    private static final Rectangle2D SCREEN_BOUNDS= Screen.getPrimary().getVisualBounds();
    @FXML
    private AnchorPane APMain;
    @FXML
    JFXTextField mail;
    @FXML
    JFXPasswordField pas;
    @FXML
    Label status;
    private String clientType;

    @FXML
    void closeAction(MouseEvent event) {
    System.exit(0);
    }

    @FXML
    void minAction(MouseEvent event) {
     Client.stage.setIconified(true);
    }

    @FXML
    public void MakeDraggable(){
        APMain.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        APMain.setOnMouseDragged(event -> {
            if(!(x>=HBoxXMin&&x<=HBoxXMax)||!(y>=HBoxYMin&&y<=HBoxYMax)) {
                Client.stage.setX(event.getScreenX() - x);
                Client.stage.setY(event.getScreenY() - y);
            }
        });
    }
    protected static void allowDrag(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent p) -> {
            offset_XY= new double[]{p.getSceneX(), p.getSceneY()};
        });

        root.setOnMouseDragged((MouseEvent d) -> {
            if (d.getScreenY()<(SCREEN_BOUNDS.getMaxY()-20))
                stage.setY(d.getScreenY() - offset_XY[1]);
            stage.setX(d.getScreenX() - offset_XY[0]);
        });

        root.setOnMouseReleased((MouseEvent r)-> {
            if (stage.getY()<0.0) stage.setY(0.0);
        });
    }
    @FXML
    void login(MouseEvent event) throws IOException {
        ip = InetAddress.getByName("localhost");
        s = new Socket(ip, 5057);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        dos.writeInt(1);
        dos.writeUTF(mail.getText());
        dos.writeUTF(pas.getText());
        st = dis.readUTF();
        System.out.println(st);
        status.setText(st);
        FXMLLoader loader=null;
        Parent root = null;
        if (st.equals("Poprawne dane-Klient"))
        {

            loader = new FXMLLoader(getClass().getResource("../View/ClientMenuForm.fxml"));
            root = loader.load();
            clientType="Client";
            ClientMenuForm clientMenuForm= loader.getController();
            clientMenuForm.setName(mail.getText(), clientMenuForm.name);
            clientMenuForm.setClientType(clientType, clientMenuForm.clientType);


        }
        else if(st.equals("Poprawne dane-Kurier"))
        {
            loader = new FXMLLoader(getClass().getResource("../View/CourierMenuForm.fxml"));
            root = loader.load();
            clientType="Courier";
            CourierMenuForm courierMenuForm= loader.getController();
            courierMenuForm.setName(mail.getText(), courierMenuForm.name);
            courierMenuForm.setClientType(clientType, courierMenuForm.clientType);

        }
        else if(st.equals("Poprawne dane-Spedytor"))
        {
            loader = new FXMLLoader(getClass().getResource("../View/ForwarderMenu.fxml"));
            root = loader.load();
            clientType="Forwarder";
            ForwarderMenuForm forwarderMenuForm= loader.getController();
            forwarderMenuForm.setName(mail.getText(), forwarderMenuForm.name);
            forwarderMenuForm.setClientType(clientType, forwarderMenuForm.clientType);
        }
        if(st.equals("Poprawne dane-Klient")||st.equals("Poprawne dane-Kurier")||st.equals("Poprawne dane-Spedytor")){



            Scene scene = new Scene(root);
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage window = new Stage();
            window.initStyle(StageStyle.DECORATED);
            window.setScene(scene);
            window.show();
        }
        dis.close();
        dos.close();
        s.close();
    }
    @FXML
    void goRegister(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/RegisterForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goRegister(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/RegisterForm.fxml"));
        Parent root = loader.load();
        //LoginForm loginForm = loader.getController();
        //loginForm.store_username(login);
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
MakeDraggable();
    }
}
