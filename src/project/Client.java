package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Controller.LoginForm;
import project.Observer.Observer;

public class Client extends Application {
    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/LoginForm.fxml"));
        Parent root = loader.load();
        LoginForm loginForm = loader.getController();
        LoginForm.observer = new Thread(new Observer());
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        stage = primaryStage;
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void startpop() throws Exception {
        FXMLLoader loader = new FXMLLoader(Client.class.getResource("View/LoginForm.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
