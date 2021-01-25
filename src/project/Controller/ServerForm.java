package project.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import project.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerForm implements Initializable {
    double x=0, y=0;
    static final int HBoxXMin=432;
    static final int HBoxXMax=492;
    static final int HBoxYMin=14;
    static final int HBoxYMax=44;
    ServerSocket serverSocket;
    Socket socket=null;
    public static String string;
    @FXML
    private AnchorPane APMain;
    @FXML
    JFXButton run;
    @FXML
    JFXTextArea text;
    @FXML
    void MakeDraggable(){
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MakeDraggable();
    }
    @FXML
    public void runserver(MouseEvent event){
        Thread socketServerThread=new Thread(new SocketServerThread());
        socketServerThread.setDaemon(true);
        socketServerThread.start();
        text.appendText("Serwer uruchomiono");
    }
    private class SocketServerThread extends Thread{
        @Override
        public void run(){
            try{
                serverSocket=new ServerSocket(5057);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            while (true){
                try {
                    socket=serverSocket.accept();
                    text.appendText("\nOtrzymano polaczenie");
                    Thread acceptedThread=new Thread(new ClientHandler(socket,text));
                    acceptedThread.setDaemon(true);
                    acceptedThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
