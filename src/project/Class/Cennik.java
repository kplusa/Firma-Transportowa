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
public class Cennik extends Application {
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

    public static Stage stage =null;
    @Override
    public void start(Stage stage) throws Exception {
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