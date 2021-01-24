package project.Class;

import javafx.stage.Stage;

public class Kurier extends Użytkownik {
    public int id;
    public String imie;
    public TypKuriera TypKuriera;
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
        this.imie=Imie;
        this.ilosc=Ilosc;
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