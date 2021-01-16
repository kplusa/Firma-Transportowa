package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuForm implements Initializable {
    @FXML
    private AnchorPane APMain;
    @FXML
    void goLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/LoginForm.fxml"));
        Parent root = loader.load();
        //MainForm mainForm = loader.getController();
        //Main.stage.initStyle(StageStyle.DECORATED);

        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        //window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goprizes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PriceListForm.fxml"));
        Parent root = loader.load();
        //MainForm mainForm = loader.getController();
        //Main.stage.initStyle(StageStyle.DECORATED);

        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        //window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goaddorder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientOrder.fxml"));
        Parent root = loader.load();
        //MainForm mainForm = loader.getController();
        //Main.stage.initStyle(StageStyle.DECORATED);

        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        //window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
    }
    @FXML
    void gocurrentorders(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ActualOrdersForm.fxml"));
        Parent root = loader.load();
        //MainForm mainForm = loader.getController();
        //Main.stage.initStyle(StageStyle.DECORATED);

        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        //window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
