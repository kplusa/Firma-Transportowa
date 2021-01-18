package project.Class;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * 
 */
public class Zlecenie extends Application {

    /**
     * Default constructor
     */
    public Zlecenie() {
    }
    public static Stage stage =null;
    @Override
    public void start(Stage stage) throws Exception {

    }
    public int ID;
    public String DataNadania;
    public String AdresPoczatkowy;
    public String status;
    public String AdresKoncowy;
    public String Kurier;

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
    public Zlecenie(int id, String Status, String kurier) {
        ID=id;
        status=Status;
        Kurier=kurier;
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