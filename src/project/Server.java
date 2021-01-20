package project;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Application {
    @FXML
    JFXTextArea text;

    public static Stage stage =null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("View/ServerForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("PIP PROJEKT GR 3");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED);
        this.stage=primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }

}
