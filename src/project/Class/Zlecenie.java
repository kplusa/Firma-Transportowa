package project.Class;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Zlecenie extends DataUtil {

    public static Stage stage =null;

    public int ID;
    public String DataNadania;
    public String AdresPoczatkowy;
    public String status;
    public String AdresKoncowy;
    public String Kurier;
    public int Ilosc;
    public String oddzialPoczatkowy;
    public String oddzialKoncowy;
    public String getOddzialPoczatkowy() {
        return oddzialPoczatkowy;
    }
    public void setOddzialPoczatkowy(String oddzialPoczatkowy) {
        this.oddzialPoczatkowy = oddzialPoczatkowy;
    }
    public String getOddzialKoncowy() {
        return oddzialKoncowy;
    }
    public void setOddzialKoncowy(String oddzialKoncowy) {
        this.oddzialKoncowy= oddzialKoncowy;
    }
    public int  getIlosc() {
        return Ilosc;
    }
    public void setIlosc(int ilosc) {
        Ilosc = ilosc;
    }
    public String getKurier() {
        return Kurier;
    }
    public void setKurier(String kurier) {
        Kurier = kurier;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getAdresKoncowy() {
        return AdresKoncowy;
    }
    public void setAdresKoncowy(String adresKoncowy) {
        AdresKoncowy = adresKoncowy;
    }
    public String getAdresPoczatkowy() {
        return AdresPoczatkowy;
    }
    public void setAdresPoczatkowy(String adresPoczatkowy) {
        AdresPoczatkowy = adresPoczatkowy;
    }
    public String getDataNadania() {
        return DataNadania;
    }
    public void setDataNadania(String dataNadania) {
        DataNadania = dataNadania;
    }
    public Zlecenie(int id, String Status, String kurier) {
        ID=id;
        status=Status;
        Kurier=kurier;
    }
    public Zlecenie(String oddzialPoczatkowy, int id,  String oddzialKoncowy) {
        this.ID=id;
        this.oddzialPoczatkowy=oddzialPoczatkowy;
        this.oddzialKoncowy=oddzialKoncowy;
    }
    public Zlecenie(int id,String adresPoczatkowy,String adresKoncowy, String dataNadania,int ilosc) {
        ID=id;
        AdresPoczatkowy=adresPoczatkowy;
        AdresKoncowy=adresKoncowy;
        DataNadania=dataNadania;
        Ilosc=ilosc;
    }
    public Zlecenie(int id, String data, String adresP, String adresK, String status, int ilosc) {
        this.ID=id;
        this.DataNadania = data;
        this.AdresPoczatkowy = adresP;
        this.AdresKoncowy = adresK;
        this.status = status;
        this.Ilosc = ilosc;
    }

    public static ObservableList<Zlecenie> fill_table(TableView Orders) throws IOException {
        ObservableList<Zlecenie> ZlecenieList = FXCollections.observableArrayList();
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(6);
            dos.writeUTF(getName());
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var tmpstring = dis.readUTF();
                var id = Integer.valueOf(tmpstring);
                var adresPoczatkowy = dis.readUTF();
                var adresKoncowy = dis.readUTF();
                var dataNadania = dis.readUTF();
                tmpstring = dis.readUTF();
                var ilosc = Integer.valueOf(tmpstring);
                ZlecenieList.add(new Zlecenie(id,adresPoczatkowy,adresKoncowy, dataNadania,ilosc));
            }
            Orders.setItems(ZlecenieList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ZlecenieList;
    }
    @FXML
    public static ObservableList<Zlecenie> fillOrderTable(TableView OrderTV) throws IOException {
        ObservableList<Zlecenie> orderList = FXCollections.observableArrayList();
        try {
            try {
                connectClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(27);
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var idOrder = Integer.valueOf(dis.readUTF());
                var fromBranch = dis.readUTF();
                var toBranch = dis.readUTF();
                orderList.add(new Zlecenie(fromBranch, idOrder, toBranch));
            }
            OrderTV.setItems(orderList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public static ObservableList<Kurier> fillCourierTable(TableView CourierTV) throws IOException {
        ObservableList<Kurier> courierList = FXCollections.observableArrayList();
        try {
            try {
                connectClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(28);
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var idCourier = Integer.valueOf(dis.readUTF());
                var location = dis.readUTF();
                courierList.add(new Kurier(idCourier, location));
            }
            CourierTV.setItems(courierList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courierList;
    }
    @FXML
    public static ObservableList<Zlecenie> filltableCourier(TableView CourierTabelForm, Label name) throws IOException {
        ObservableList<Zlecenie> zlecenieList = FXCollections.observableArrayList();
        try {
            try {
                ip = InetAddress.getByName("localhost");
                s = new Socket(ip, 5057);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            dos.writeInt(41);
            dos.writeUTF(name.getText());
            var counter = dis.readInt();
            for (int i = 1;i <= counter; i++)
            {
                var tmpstring = dis.readUTF();
                var id = Integer.valueOf(tmpstring);
                var datas = dis.readUTF();
                var adresP = dis.readUTF();
                var adresK = dis.readUTF();
                var status = dis.readUTF();
                tmpstring = dis.readUTF();
                var ilosc = Integer.valueOf(tmpstring);
                zlecenieList.add(new Zlecenie(id, datas, adresP, adresK, status, ilosc));
            }
            CourierTabelForm.setItems(zlecenieList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return zlecenieList;
    }
    @FXML
    public static ObservableList<Zlecenie> filltableCurrentOrder(TableView CurrentOrder, Label name) throws IOException {
        ObservableList<Zlecenie> ZlecenieList = FXCollections.observableArrayList();
        try {
            connectClient();
            dos.writeInt(5);
            dos.writeUTF(name.getText());
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var tmpstring = dis.readUTF();
                var id = Integer.valueOf(tmpstring);
                var stat = dis.readUTF();
                var kurier = dis.readUTF();
                if(!stat.equals("Dostarczone")){
                    ZlecenieList.add(new Zlecenie(id, stat, kurier));}
            }
            CurrentOrder.setItems(ZlecenieList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ZlecenieList;
    }
    public void WyswietlZlecenie(int ID) {
        // TODO implement here
    }
    public void DodajZlecenie() {
        // TODO implement here
    }
    public void UsunZlecenie(int ID) {
        // TODO implement here
    }
    public void ZmienZlecenie(int ID) {
        // TODO implement here
    }

}