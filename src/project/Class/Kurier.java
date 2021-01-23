package project.Class;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.TableView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Kurier extends Użytkownik {
    public int id;
    public TypKuriera TypKuriera;
    public String location;
    public Kurier(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public project.Class.TypKuriera getTypKuriera() {
        return TypKuriera;
    }

    public void setTypKuriera(project.Class.TypKuriera typKuriera) {
        TypKuriera = typKuriera;
    }


    public Kurier() {
    }
    public static Stage stage =null;




    public void ZmienTyp(int ID) {
        // TODO implement here
    }
    public void DodajKuriera() {
        // TODO implement here
    }

    public void UsunKuriera(int ID) {
        // TODO implement here
    }

}