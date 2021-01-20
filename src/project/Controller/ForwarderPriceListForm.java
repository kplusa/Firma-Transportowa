package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import project.Class.Cennik;
import project.Utils.DataUtil;
import project.Class.Doplata;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ForwarderPriceListForm extends DataUtil implements Initializable {
    @FXML
    private AnchorPane APMain;
    @FXML
    public Label name;


    @FXML
    public Label clientType;
    private Socket s;
    private InetAddress ip;
    private DataInputStream dis;
    private DataOutputStream dos;
    private int counter, Limit;
    private Float Kwota;
    private String tmpstring, Gabaryt, Opis;
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
    private javafx.scene.control.TableColumn<Cennik, String> Type;
    @FXML
    private javafx.scene.control.TableColumn<Cennik, Integer> AditionalAmount;
    @FXML
    private AnchorPane AditionalAP;

    @FXML
    private AnchorPane PriceListAP;
    @FXML
    private JFXTextField dimensionTF;

    @FXML
    private JFXTextField amountTF;

    @FXML
    private JFXTextArea descriptionTA;

    @FXML
    private JFXTextField limitTF;
    @FXML
    private JFXTextField AmountAditionalPrice;
    @FXML
    private JFXTextField TypeOfAditionalPrice;
    @FXML
    private Label status;


    @FXML
    void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderMenu.fxml"));
        Parent root = loader.load();
        ForwarderMenuForm forwarderMenuForm = loader.getController();
        forwarderMenuForm.setName(getName(), forwarderMenuForm.name);
        forwarderMenuForm.setClientType(getClientType(), forwarderMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void goMenu(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ForwarderMenu.fxml"));
        Parent root = loader.load();
        ForwarderMenuForm forwarderMenuForm = loader.getController();
        forwarderMenuForm.setName(getName(), forwarderMenuForm.name);
        forwarderMenuForm.setClientType(getClientType(), forwarderMenuForm.clientType);
        Scene scene = new Scene(root);
        ((Node) event.getSource()).getScene().getWindow().hide();
        Stage window = new Stage();
        window.setScene(scene);
        window.show();
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
            fill_table();
            fill_table_second();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PriceList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                PriceListAP.setVisible(true);
                AditionalAP.setVisible(false);
                fillPriceListData();
            }
        });
        AditionalPriceList.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                PriceListAP.setVisible(false);
                AditionalAP.setVisible(true);
                fillAdditionalData();
            }

        });


    }

    @FXML
    public ObservableList<Cennik> fill_table() throws IOException {
        ObservableList<Cennik> Cennik_list = FXCollections.observableArrayList();
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(3);
            counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                Gabaryt = dis.readUTF();
                tmpstring = dis.readUTF();
                Kwota = Float.valueOf(tmpstring);
                Opis = dis.readUTF();
                tmpstring = dis.readUTF();
                Limit = Integer.valueOf(tmpstring);
                Cennik_list.add(new Cennik(Gabaryt, Kwota, Opis, Limit));
            }
            PriceList.setItems(Cennik_list);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Cennik_list;
    }

    public ObservableList<Doplata> fill_table_second() throws IOException {
        ObservableList<Doplata> DoplataList = FXCollections.observableArrayList();
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(4);
            counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                Opis = dis.readUTF();
                tmpstring = dis.readUTF();
                Kwota = Float.valueOf(tmpstring);
                DoplataList.add(new Doplata(Opis, Kwota));
            }
            AditionalPriceList.setItems(DoplataList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DoplataList;
    }

    private void fillPriceListData() {
        if (PriceList.getSelectionModel().getSelectedItem() != null) {
            Cennik cennik = PriceList.getSelectionModel().getSelectedItem();
            dimensionTF.setText(cennik.getGabaryt());
            amountTF.setText(String.valueOf(cennik.getKwota()));
            descriptionTA.setText(String.valueOf(cennik.getOpis()));
            limitTF.setText(String.valueOf(cennik.getLimit()));
        }
    }

    private void fillAdditionalData() {
        if (AditionalPriceList.getSelectionModel().getSelectedItem() != null) {
            Doplata doplata = AditionalPriceList.getSelectionModel().getSelectedItem();
            TypeOfAditionalPrice.setText(doplata.getTypDoplaty());
            AmountAditionalPrice.setText(String.valueOf(doplata.getKwotaD()));
        }
    }

    @FXML
    private void insert(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (PriceListAP.isVisible()) {
                dos.writeInt(21);
                dos.writeUTF(dimensionTF.getText());
                dos.writeFloat(Float.valueOf(amountTF.getText()));
                dos.writeUTF(descriptionTA.getText());
                dos.writeUTF(limitTF.getText());

            } else if (AditionalAP.isVisible()) {
                dos.writeInt(22);
                dos.writeUTF(TypeOfAditionalPrice.getText());
                dos.writeFloat(Float.valueOf(AmountAditionalPrice.getText()));
            }
            tmpstring = dis.readUTF();
            status.setText(tmpstring);
            if (tmpstring.equals("Added") && PriceListAP.isVisible()) {
                dimensionTF.setText("");
                amountTF.setText("");
                descriptionTA.setText("");
                limitTF.setText("");
                Thread.sleep(300);
                fill_table();


            } else if (tmpstring.equals("Added") && AditionalAP.isVisible()) {
                TypeOfAditionalPrice.setText("");
                AmountAditionalPrice.setText("");
                Thread.sleep(300);
                fill_table_second();
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(even ->
                    status.setText("")
            );
            pause.play();

            dis.close();
            dos.close();
            s.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void update(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (PriceListAP.isVisible()) {
                if (PriceList.getSelectionModel().getSelectedItem() != null) {
                    Cennik cennik = PriceList.getSelectionModel().getSelectedItem();
                    if (dimensionTF.getText().equals(cennik.getGabaryt()) && descriptionTA.getText().equals(cennik.getOpis())
                            &&amountTF.getText().equals(String.valueOf(cennik.getKwota())) && limitTF.getText().equals(String.valueOf(cennik.getLimit()))){
                        tmpstring = "The same data";}
                    else {
                        dos.writeInt(23);
                        dos.writeUTF(dimensionTF.getText());
                        dos.writeFloat(Float.valueOf(amountTF.getText()));
                        dos.writeUTF(descriptionTA.getText());
                        dos.writeUTF(limitTF.getText());
                        tmpstring = dis.readUTF();
                    }
                }
            }
            else if (AditionalAP.isVisible()) {
                if (AditionalPriceList.getSelectionModel().getSelectedItem() != null) {
                    Doplata doplata = AditionalPriceList.getSelectionModel().getSelectedItem();
                    if (TypeOfAditionalPrice.getText().equals(doplata.getTypDoplaty()) && AmountAditionalPrice.getText().equals(String.valueOf(doplata.getKwotaD())))
                        tmpstring = "The same data";
                    else{
                        dos.writeInt(24);
                        dos.writeUTF(TypeOfAditionalPrice.getText());
                        dos.writeFloat(Float.valueOf(AmountAditionalPrice.getText()));
                        tmpstring = dis.readUTF();
                    }

                }
            }

            status.setText(tmpstring);
            if (tmpstring.equals("Edited") && PriceListAP.isVisible()) {
                dimensionTF.setText("");
                amountTF.setText("");
                descriptionTA.setText("");
                limitTF.setText("");
                Thread.sleep(300);
                fill_table();


            } else if (tmpstring.equals("Edited") && AditionalAP.isVisible()) {
                TypeOfAditionalPrice.setText("");
                AmountAditionalPrice.setText("");
                Thread.sleep(300);
                fill_table_second();
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(even ->
                    status.setText("")
            );
            pause.play();

            dis.close();
            dos.close();
            s.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } }

    @FXML
    private void delete(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (PriceListAP.isVisible()) {
                dos.writeInt(25);
                dos.writeUTF(dimensionTF.getText());
            } else if (AditionalAP.isVisible()) {
                dos.writeInt(26);
                dos.writeUTF(TypeOfAditionalPrice.getText());
                dos.writeFloat(Float.valueOf(AmountAditionalPrice.getText()));
            }
            tmpstring = dis.readUTF();
            status.setText(tmpstring);
            if (tmpstring.equals("Deleted") && PriceListAP.isVisible()) {
                dimensionTF.setText("");
                amountTF.setText("");
                descriptionTA.setText("");
                limitTF.setText("");
                Thread.sleep(300);
                fill_table();


            } else if (tmpstring.equals("Deleted") && AditionalAP.isVisible()) {
                TypeOfAditionalPrice.setText("");
                AmountAditionalPrice.setText("");
                Thread.sleep(300);
                fill_table_second();
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(even ->
                    status.setText("")
            );
            pause.play();

            dis.close();
            dos.close();
            s.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
