package project.Builder;

public class ZlecenieProduct {

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
        this.oddzialKoncowy = oddzialKoncowy;
    }

    public int getIlosc() {
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

}
