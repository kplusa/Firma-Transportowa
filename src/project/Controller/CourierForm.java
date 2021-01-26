package project.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import project.Builder.ZlecenieProduct;
import project.Class.Zlecenie;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourierForm extends DataUtil implements Initializable {
    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label name;
    private String tmpstring;


    @FXML
    private JFXButton OKCourier;
    @FXML
    private JFXComboBox<String> StatusSelection;
    @FXML
    private JFXComboBox<String> OrderSelection;
    @FXML
    public Label clientType;
    @FXML
    TableView<ZlecenieProduct> CourierTabelForm;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, Integer> Id;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, String> DataNadania;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, String> AdresP;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, String> AdresK;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, String> Status;
    @FXML
    private javafx.scene.control.TableColumn<ZlecenieProduct, Integer> Amount;

    @FXML
    void back(ActionEvent event) throws IOException {
        buttonMenu.onClick(event);
    }

    @FXML
    void goMenu(MouseEvent event) throws IOException {
        buttonMenu.onClick(event);
    }

    @FXML
    void aktualizuj(MouseEvent event) throws IOException, InterruptedException {
        if (CourierTabelForm.getSelectionModel().getSelectedItem() != null
                && StatusSelection.getSelectionModel().getSelectedItem() != null) {
            String testouput = StatusSelection.getSelectionModel().getSelectedItem();
            ZlecenieProduct selectedZlecenie = CourierTabelForm.getSelectionModel().getSelectedItem();
            connectClient();
            dos.writeInt(42);
            dos.writeUTF(testouput);
            dos.writeInt(selectedZlecenie.ID);
            dis.close();
            dos.close();
            s.close();
            Thread.sleep(300);
            Zlecenie.filltableCourier(CourierTabelForm, name);
        }
        if (OrderSelection.getSelectionModel().getSelectedItem() != null) {
            connectClient();
            tmpstring = OrderSelection.getSelectionModel().getSelectedItem();

            dos.writeInt(44);
            dos.writeUTF(tmpstring);
            dos.writeUTF(name.getText());
            dis.close();
            dos.close();
            s.close();
            Thread.sleep(300);
        }
    }

    public void initializeOrder() {
        try {
            connectClient();
            dos.writeInt(43);
            dos.writeUTF(name.getText());
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                tmpstring = dis.readUTF();
                OrderSelection.getSelectionModel().select(tmpstring);
            }
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        AdresP.setCellValueFactory(new PropertyValueFactory<>("AdresPoczatkowy"));
        AdresK.setCellValueFactory(new PropertyValueFactory<>("AdresKoncowy"));
        DataNadania.setCellValueFactory(new PropertyValueFactory<>("DataNadania"));
        Amount.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        StatusSelection.getItems().add("Odebrane od nadawcy");
        StatusSelection.getItems().add("Dostarczone do oddziału");
        StatusSelection.getItems().add("Odebrane z oddziału");
        StatusSelection.getItems().add("Dostarczone");
        OrderSelection.getItems().add("Początkowy");
        OrderSelection.getItems().add("Końcowy");
        OrderSelection.getItems().add("Pomiędzy odziałami");
    }


}