package project.Class;

import javafx.stage.Stage;

public class Kurier  {
    public int id;
    public String imie;
    public int ilosc;

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }

    public String location;

    public Kurier(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public Kurier(int Id, String Imie, int Ilosc) {
        this.id = Id;
        this.imie = Imie;
        this.ilosc = Ilosc;
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

}