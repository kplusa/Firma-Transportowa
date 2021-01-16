package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CurentOrderForm implements Initializable {



    @FXML
    private AnchorPane APMain;




    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientMenuForm.fxml"));
        Parent root = loader.load();
        //MainForm mainForm = loader.getController();
        //Main.stage.initStyle(StageStyle.DECORATED);

        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
       // window.initStyle(StageStyle.UNDECORATED);
        window.setScene(scene);
        window.show();
    }
    @FXML
    void goMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ClientMenuForm.fxml"));
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
