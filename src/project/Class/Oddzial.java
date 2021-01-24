package project.Class;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import project.Utils.DataUtil;

import java.io.IOException;

public class Oddzial extends DataUtil {
    public int id;
    public String miejscowosc;
    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Oddzial(String Miejscowosc) {
        miejscowosc = Miejscowosc;
    }

    @FXML
    public static ObservableList<Oddzial> fill_table(TableView PlaceTable) throws IOException {
        ObservableList<Oddzial> Oddziallist = FXCollections.observableArrayList();
        try {
            connectClient();
            dos.writeInt(30);
            var counter = dis.readInt();
            for (int i = 1; i <= counter; i++) {
                var tmpstring = dis.readUTF();
                Oddziallist.add(new Oddzial(tmpstring));
            }
            PlaceTable.setItems(Oddziallist);
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Oddziallist;
    }

    public static void addAction(JFXTextField PlaceLabel, Label state) throws IOException {
        try {
            connectClient();
            dos.writeInt(31);
            dos.writeUTF(PlaceLabel.getText());
            state.setText(dis.readUTF());
            dis.close();
            dos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
