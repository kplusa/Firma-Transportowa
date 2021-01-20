package project.Class;

public class Oddzial {
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

    public Oddzial( String Miejscowosc) {
            miejscowosc=Miejscowosc;
        }


}
