package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import project.Builder.ZlecenieProduct;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CurentOrderForm extends DataUtil implements Initializable {

    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label name;
    @FXML
    public Label clientType;
    @FXML
    TableView<ZlecenieProduct> CurrentOrder;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, Integer> OrderNumber;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, String> Status;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, String> Courier;

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
        OrderNumber.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        Courier.setCellValueFactory(new PropertyValueFactory<>("Kurier"));
    }

}
