package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Server extends Application {


    public static Stage stage =null;
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("View/ServerForm.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("PIP PROJEKT GR 3");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.DECORATED);
        stage=primaryStage;
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }

}
