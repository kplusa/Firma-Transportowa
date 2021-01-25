package project.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import project.Class.Oddzial;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddbranchForm extends DataUtil implements Initializable {
    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label name;
    @FXML
    public JFXTextField PlaceLabel;
    @FXML
    public Label clientType;
    @FXML
    public TableView PlaceTable;
    @FXML
    public TableColumn Place;
    @FXML
    public Label state, information;


    @FXML
    void goMenu(MouseEvent event) throws IOException {
        buttonMenu.onClick(event);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        buttonMenu.onClick(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Place.setCellValueFactory(new PropertyValueFactory<>("miejscowosc"));
        try {
            Oddzial.fill_table(PlaceTable);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void add(ActionEvent event) throws IOException {
        if (PlaceLabel.getText().isEmpty()) {
            state.setText("Podaj oddzial");
        } else {
            Oddzial.addAction(PlaceLabel, state);
        }
        Oddzial.fill_table(PlaceTable);
    }
}
