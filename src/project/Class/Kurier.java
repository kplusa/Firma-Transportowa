package project.Class;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Kurier extends Użytkownik {

    public int id;
    public String location;
    public TypKuriera TypKuriera;

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
    @Override
    public void start(Stage stage) throws Exception {

    }






    /**
     * @param ID
     */
    public void ZmienTyp(int ID) {
        // TODO implement here
    }

    /**
     * 
     */
    public void DodajKuriera() {
        // TODO implement here
    }

    /**
     * @param ID
     */
    public void UsunKuriera(int ID) {
        // TODO implement here
    }

}