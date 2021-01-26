package project.Class;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import project.Builder.Director;
import project.Builder.ZlecenieProduct;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Zlecenie extends DataUtil {
    public static Stage stage = null;

    public static ObservableList<ZlecenieProduct> fill_table(TableView Orders) throws IOException {
        ObservableList<ZlecenieProduct> ZlecenieList = FXCollections.observableArrayList();
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
                Director director = new Director();
                director.getZlecenieAdresyIlosc(id, adresPoczatkowy, adresKoncowy, dataNadania, ilosc);
                ZlecenieList.add(director.builder.zlecenie);
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
    public static ObservableList<ZlecenieProduct> fillOrderTable(TableView OrderTV) throws IOException {
        ObservableList<ZlecenieProduct> orderList = FXCollections.observableArrayList();
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
                Director director = new Director();
                director.getZlecenieOddzialy(fromBranch, idOrder, toBranch);
                orderList.add(director.builder.zlecenie);
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
    public static ObservableList<ZlecenieProduct> filltableCourier(TableView CourierTabelForm, Label name) throws IOException {
        ObservableList<ZlecenieProduct> zlecenieList = FXCollections.observableArrayList();
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
            for (int i = 1; i <= counter; i++) {
                var tmpstring = dis.readUTF();
                var id = Integer.valueOf(tmpstring);
                var datas = dis.readUTF();
                var adresP = dis.readUTF();
                var adresK = dis.readUTF();
                var status = dis.readUTF();
                tmpstring = dis.readUTF();
                var ilosc = Integer.valueOf(tmpstring);
                Director director = new Director();
                director.getZlecenieAdresyStatus(id, datas, adresP, adresK, status, ilosc);
                zlecenieList.add(director.builder.zlecenie);
            }
            CourierTabelForm.setItems(zlecenieList);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zlecenieList;
    }

    @FXML
    public static ObservableList<ZlecenieProduct> filltableCurrentOrder(TableView CurrentOrder, Label name) throws IOException {
        ObservableList<ZlecenieProduct> ZlecenieList = FXCollections.observableArrayList();
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
                kurier += " ";
                kurier += dis.readUTF();
                if (!stat.equals("Dostarczone")) {
                    Director director = new Director();
                    director.getZlecenieStatusKurier(id, stat, kurier);
                    ZlecenieList.add(director.builder.zlecenie);
                }
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

}