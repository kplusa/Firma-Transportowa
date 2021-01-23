package project.Class;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 
 */
public class Cennik extends DataUtil {
    public int ID;
    public String Gabaryt;
    public float Kwota;
    public String Opis;
    public String DataZmiany;
    public int Limit;
    public Cennik() {
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getGabaryt() {
        return Gabaryt;
    }
    public void setGabaryt(String gabaryt) {
        Gabaryt = gabaryt;
    }
    public float getKwota() {
        return Kwota;
    }
    public void setKwota(float kwota) {
        Kwota = kwota;
    }
    public String getOpis() {
        return Opis;
    }
    public void setOpis(String opis) {
        Opis = opis;
    }
    public String getDataZmiany() {
        return DataZmiany;
    }
    public void setDataZmiany(String dataZmiany) {
        DataZmiany = dataZmiany;
    }
    public int getLimit() {
        return Limit;
    }
    public void setLimit(int limit) {
        Limit = limit;
    }
    public Cennik(String Gabaryt, float Kwota, String Opis, int Limit) {
        this.Gabaryt=Gabaryt;
        this.Kwota=Kwota;
        this.Opis=Opis;
        this.Limit=Limit;
    }


    @FXML
    public static ObservableList<Cennik> fillPriceList(TableView PriceList) throws IOException {
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
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var Gabaryt = dis.readUTF();
                var tmpstring = dis.readUTF();
                var Kwota = Float.valueOf(tmpstring);
                var Opis = dis.readUTF();
                tmpstring = dis.readUTF();
                var Limit = Integer.valueOf(tmpstring);
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


    public void ZmienCennik(int ID) {
        // TODO implement here
    }
    public void DodajCennik() {
        // TODO implement here
    }
    public void UsunCennik(int ID) {
        // TODO implement here
    }

}