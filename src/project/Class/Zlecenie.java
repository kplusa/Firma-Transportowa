package project.Class;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Zlecenie extends Application {

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
    public Zlecenie(String oddzialPoczatkowy, int id,  String oddzialKoncowy) {//TODO builder
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