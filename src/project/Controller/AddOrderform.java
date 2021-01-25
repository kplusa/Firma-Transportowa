package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.Class.Zlecenie;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddOrderform extends DataUtil implements Initializable {
    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label name;
    @FXML
    public Label clientType;
    @FXML
    public TableView<Zlecenie> Orders;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, Integer> IdColumn;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> FromColumn;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> ToColumn;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, String> PostingDateColumn;
    @FXML
    private javafx.scene.control.TableColumn<Zlecenie, Integer> AmountColumn;
    @FXML
    private JFXTextField IdLabel;
    @FXML
    private JFXTextField DateLabel;
    @FXML
    private JFXTextArea FromLabel;
    @FXML
    private JFXTextArea ToLabel;
    @FXML
    private Label state;

    public int id;
    public String tmpstring;
    @FXML
    void back(ActionEvent event) throws IOException {
        buttonMenu.onClick(event);
    }
    @FXML
    void addpack(ActionEvent event) throws IOException {

        if (Orders.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/PackForm.fxml"));
            Parent root = loader.load();
            PackForm packForm= loader.getController();
            packForm.setName(getName(), packForm.name);
            packForm.setClientType(getClientType(), packForm.clientType);
            Zlecenie zlecenie = Orders.getSelectionModel().getSelectedItem();
            packForm.zlecid(zlecenie.getID());
            packForm.fill();
            Scene scene = new Scene(root);
            ((Node) event.getSource()).getScene().getWindow().hide();
            Stage window = new Stage();
            window.setScene(scene);
            window.show();
        }
        else{
            state.setText("Wybierz zlecenie");
        }

    }
    @FXML
    void goMenu(MouseEvent event) throws IOException {
        buttonMenu.onClick(event);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        FromColumn.setCellValueFactory(new PropertyValueFactory<>("AdresPoczatkowy"));
        ToColumn.setCellValueFactory(new PropertyValueFactory<>("AdresKoncowy"));
        PostingDateColumn.setCellValueFactory(new PropertyValueFactory<>("DataNadania"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("Ilosc"));
        Orders.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                fillLabels();
            }
        });
    }



    private void fillLabels() {
        if (Orders.getSelectionModel().getSelectedItem() != null) {
            Zlecenie zlecenie = Orders.getSelectionModel().getSelectedItem();
            IdLabel.setText(String.valueOf(zlecenie.getID()));
            FromLabel.setText(zlecenie.getAdresPoczatkowy());
            ToLabel.setText(zlecenie.getAdresKoncowy());
            DateLabel.setText(zlecenie.getDataNadania());
        }
    }
    @FXML private void add()
    {
        try {
            connectClient();
                    dos.writeInt(7);
                    dos.writeUTF(name.getText());
                    dos.writeUTF(FromLabel.getText());
                    dos.writeUTF(ToLabel.getText());
                    tmpstring = dis.readUTF();
                    System.out.println(tmpstring);
            state.setText(tmpstring);
            Zlecenie.fill_table(Orders);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(even -> {
                state.setText("");
                IdLabel.setText(""); FromLabel.setText(""); ToLabel.setText(""); DateLabel.setText("");}
            );
            pause.play();
            dis.close();
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void edit()
    {
        try {
            connectClient();
                if (Orders.getSelectionModel().getSelectedItem() != null) {
                    Zlecenie zlecenie = Orders.getSelectionModel().getSelectedItem();
                    if (IdLabel.getText().equals(zlecenie.getID()) && FromLabel.getText().equals(zlecenie.getAdresPoczatkowy())
                            && ToLabel.getText().equals(String.valueOf(zlecenie.getAdresKoncowy())) && DateLabel.getText().equals(String.valueOf(zlecenie.getDataNadania()))) {
                        tmpstring = "The same data";
                    } else {
                        dos.writeInt(8);
                        dos.writeInt(zlecenie.getID());
                        dos.writeUTF(FromLabel.getText());
                        dos.writeUTF(ToLabel.getText());
                        tmpstring = dis.readUTF();
                        System.out.println(tmpstring);

                    }
                }
            state.setText(tmpstring);
            Zlecenie.fill_table(Orders);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(even -> {
                        state.setText("");
                    IdLabel.setText(""); FromLabel.setText(""); ToLabel.setText(""); DateLabel.setText("");}
            );
            pause.play();
            dis.close();
            dos.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML private void delete()
    {
        try {
            connectClient();
            dos.writeInt(9);
            dos.writeInt(Integer.valueOf(IdLabel.getText()));
            dos.writeUTF(FromLabel.getText());
            dos.writeUTF(ToLabel.getText());
            dos.writeUTF(DateLabel.getText());
            state.setText(dis.readUTF());
            if(state.getText().equals("Deleted"))
            {
                Zlecenie.fill_table(Orders);
                Thread.sleep(300);
                IdLabel.setText("");
                FromLabel.setText("");
                ToLabel.setText("");
                DateLabel.setText("");
            }
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
