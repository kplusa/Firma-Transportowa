package project.Class;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import project.Utils.DataUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Doplata extends DataUtil {
    public int ID;
    public String TypDoplaty;
    public Float KwotaD;

    public Float getKwotaD() {
        return KwotaD;
    }

    public void setKwotaD(Float kwota) {
        KwotaD = kwota;
    }

    public String getTypDoplaty() {
        return TypDoplaty;
    }

    public void setTypDoplaty(String typDoplaty) {
        TypDoplaty = typDoplaty;
    }

    public Doplata(String typDoplaty, Float kwotaD) {
        TypDoplaty = typDoplaty;
        KwotaD = kwotaD;
    }

    public static ObservableList<Doplata> fillTableAdditional(TableView AditionalPriceList) throws IOException {
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
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var Opis = dis.readUTF();
                var tmpstring = dis.readUTF();
                var Kwota = Float.valueOf(tmpstring);
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
}
