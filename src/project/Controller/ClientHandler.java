package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import project.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

class ClientHandler extends Thread implements Initializable {

        Socket socket=null;
        DataInputStream dataInputStream=null;
        DataOutputStream dataOutputStream=null;
        ClientHandler(Socket s){
            socket=s;
        }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void run(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ServerForm.fxml"));
            Parent root= loader.load();
            ServerForm serverForm = loader.getController();
            dataInputStream=new DataInputStream(socket.getInputStream());
            dataOutputStream=new DataOutputStream(socket.getOutputStream());
            String msg=dataInputStream.readUTF();
            System.out.println(msg);
            serverForm.store(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
