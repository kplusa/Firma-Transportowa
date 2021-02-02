package project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import project.Class.Cennik;
import project.Class.Doplata;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrizesForm extends DataUtil implements Initializable {
    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label name;
    @FXML
    public Label clientType;

    @FXML
    TableView<Cennik> PriceList;
    @FXML
    TableView<Doplata> AditionalPriceList;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, String> Dimension;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, Float> Amount;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, String> Description;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, Integer> Lm;
    @FXML
    private javafx.scene.control.TableColumn<Doplata, String> Type;
    @FXML
    private javafx.scene.control.TableColumn<Doplata, Integer> AditionalAmount;

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
        Dimension.setCellValueFactory(new PropertyValueFactory<>("Gabaryt"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Kwota"));
        Description.setCellValueFactory(new PropertyValueFactory<>("Opis"));
        Lm.setCellValueFactory(new PropertyValueFactory<>("Limit"));
        Type.setCellValueFactory(new PropertyValueFactory<>("TypDoplaty"));
        AditionalAmount.setCellValueFactory(new PropertyValueFactory<>("KwotaD"));
        try {
            Cennik.fillPriceList(PriceList);
            Doplata.fillTableAdditional(AditionalPriceList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}