package project.Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import project.Class.Zlecenie;
import project.State.ButtonMenu;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PackForm extends DataUtil implements  Initializable {
    ButtonMenu buttonMenu = new ButtonMenu("Pack");
    @FXML
    public Label name;
    @FXML
    public Label clientType;
    @FXML
    public Label SummaryLabel;
    @FXML
    public JFXComboBox DimensionCombo;
    @FXML
    public JFXComboBox PriceCombo;
    @FXML
    public Label DimensionLabel;
    @FXML
    public Label PriceLabel;
    @FXML
    public Label listOfPrizes;
    @FXML
    public JFXTextField WeightLabel;
    @FXML
    public Label state;
    private int Zlecenieid,counter,counterOfAditional=0;
    final ObservableList dimensions = FXCollections.observableArrayList();
    final ObservableList prices = FXCollections.observableArrayList();
    private Socket s;
    private InetAddress ip;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String tmpstring;
    private Float tmpFloat;
    private List<String> StringList=new ArrayList<String>();


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
    }
    public void zlecid(int id){Zlecenieid=id;}
    @FXML
    public void fill() throws IOException {
        try {
                try {
                    ip = InetAddress.getByName("localhost");
                    s = new Socket(ip, 5057);
                    dis = new DataInputStream(s.getInputStream());
                    dos = new DataOutputStream(s.getOutputStream());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Brak polaczenia z serwerem");
                }
            dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(10);
                counter = dis.readInt();
                for(int i=0;i<counter;i++) {
                    tmpstring=dis.readUTF();
                    dimensions.add(tmpstring+" "+dis.readUTF());
                }
                DimensionCombo.setItems(dimensions);
            counter = dis.readInt();
            for(int i=0;i<counter;i++) {
                tmpstring=dis.readUTF();
                prices.add(tmpstring);
            }
            PriceCombo.setItems(prices);
        }
        catch (Exception e) {
            e.printStackTrace();
            dis.close();
            dos.close();
            s.close();
        }
        dis.close();
        dos.close();
        s.close();
    }
    @FXML
    void DimensionToLabel(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Brak polaczenia z serwerem");
            }
            dos.writeInt(11);
            tmpstring=DimensionCombo.getSelectionModel().getSelectedItem().toString();
            dos.writeChar(tmpstring.charAt(0));
            tmpFloat=dis.readFloat();
            DimensionLabel.setText(tmpFloat.toString());
            SetsummaryLabel();
        }
        catch (Exception e) {
            e.printStackTrace();
            dis.close();
            dos.close();
            s.close();
        }
        dis.close();
        dos.close();
        s.close();
    }
    @FXML
    void PriceToLabel(ActionEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Brak polaczenia z serwerem");
            }
            dos.writeInt(12);
            tmpstring=PriceCombo.getSelectionModel().getSelectedItem().toString();
            dos.writeUTF(tmpstring);
            tmpFloat=dis.readFloat();
             tmpstring=dis.readUTF();
            StringList.add(tmpstring);
            counterOfAditional++;
            listOfPrizes.setText(listOfPrizes.getText()+"\n"+tmpstring);
            if(!PriceLabel.getText().isEmpty()){

                tmpFloat+=Float.valueOf(PriceLabel.getText());
            }
            PriceLabel.setText(tmpFloat.toString());
            SetsummaryLabel();
        }
        catch (Exception e) {
            e.printStackTrace();
            dis.close();
            dos.close();
            s.close();
        }
        dis.close();
        dos.close();
        s.close();
    }
    @FXML
    void SetsummaryLabel()  {
        if(DimensionLabel.getText().isEmpty()&&!(PriceLabel.getText().isEmpty()))
        {
            tmpFloat=Float.valueOf(PriceLabel.getText());
        }
        else if(PriceLabel.getText().isEmpty()&&!(DimensionLabel.getText().isEmpty()))
        {
            tmpFloat=Float.valueOf(DimensionLabel.getText());
        }
        else if(!(DimensionLabel.getText().isEmpty()&&PriceLabel.getText().isEmpty()))
        {
            tmpFloat=Float.valueOf(DimensionLabel.getText())+Float.valueOf(PriceLabel.getText());
        }
        if(!(DimensionLabel.getText().isEmpty()&&PriceLabel.getText().isEmpty())){
        SummaryLabel.setText(tmpFloat.toString());}
    }
    @FXML
    void resetb()  {
        listOfPrizes.setText("");
    PriceLabel.setText("");
    StringList.clear();
    counterOfAditional=0;
        SetsummaryLabel();
    }
    @FXML
    void addpacktobase(MouseEvent event) throws IOException {
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Brak polaczenia z serwerem");
            }
            dos.writeInt(13);
            dos.writeFloat(Float.valueOf(SummaryLabel.getText()));
            dos.writeFloat(Float.valueOf(WeightLabel.getText()));
            dos.writeInt(Zlecenieid);
            tmpstring=DimensionCombo.getSelectionModel().getSelectedItem().toString();
            dos.writeChar(tmpstring.charAt(0));
            dos.writeInt(counterOfAditional);
            for (String send:StringList)
            {
                dos.writeUTF(send);
            }
            state.setText(dis.readUTF());
            resetb();
        }
        catch (Exception e) {
            e.printStackTrace();
            dis.close();
            dos.close();
            s.close();
        }
        dis.close();
        dos.close();
        s.close();
    }
}
