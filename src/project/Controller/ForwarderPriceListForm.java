package project.Controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import project.Class.Cennik;
import project.Class.Doplata;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForwarderPriceListForm extends DataUtil implements Initializable {
    @FXML
    public Label name;
    ButtonMenu buttonMenu = new ButtonMenu(getClientType());
    @FXML
    public Label clientType;
    private String tmpstring;
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
    public Label status, information;


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
            connectClient();
            if (PriceListAP.isVisible()) {//TODO DOS
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
                Cennik.fillPriceList(PriceList);


            } else if (tmpstring.equals("Added") && AditionalAP.isVisible()) {
                TypeOfAditionalPrice.setText("");
                AmountAditionalPrice.setText("");
                Thread.sleep(300);
                Doplata.fillTableAdditional(AditionalPriceList);
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
            connectClient();
            if (PriceListAP.isVisible()) {
                if (PriceList.getSelectionModel().getSelectedItem() != null) {
                    Cennik cennik = PriceList.getSelectionModel().getSelectedItem();
                    if (dimensionTF.getText().equals(cennik.getGabaryt()) && descriptionTA.getText().equals(cennik.getOpis())
                            && amountTF.getText().equals(String.valueOf(cennik.getKwota())) && limitTF.getText().equals(String.valueOf(cennik.getLimit()))) {
                        tmpstring = "The same data";
                    } else {
                        dos.writeInt(23);
                        dos.writeUTF(dimensionTF.getText());
                        dos.writeFloat(Float.valueOf(amountTF.getText()));
                        dos.writeUTF(descriptionTA.getText());
                        dos.writeUTF(limitTF.getText());
                        tmpstring = dis.readUTF();
                    }
                }
            } else if (AditionalAP.isVisible()) {
                if (AditionalPriceList.getSelectionModel().getSelectedItem() != null) {
                    Doplata doplata = AditionalPriceList.getSelectionModel().getSelectedItem();
                    if (TypeOfAditionalPrice.getText().equals(doplata.getTypDoplaty()) && AmountAditionalPrice.getText().equals(String.valueOf(doplata.getKwotaD())))
                        tmpstring = "The same data";
                    else {
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
                Cennik.fillPriceList(PriceList);


            } else if (tmpstring.equals("Edited") && AditionalAP.isVisible()) {
                TypeOfAditionalPrice.setText("");
                AmountAditionalPrice.setText("");
                Thread.sleep(300);
                Doplata.fillTableAdditional(AditionalPriceList);
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
    private void delete(ActionEvent event) throws IOException {
        try {
            connectClient();
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
                Cennik.fillPriceList(PriceList);


            } else if (tmpstring.equals("Deleted") && AditionalAP.isVisible()) {
                TypeOfAditionalPrice.setText("");
                AmountAditionalPrice.setText("");
                Thread.sleep(300);
                Doplata.fillTableAdditional(AditionalPriceList);
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
