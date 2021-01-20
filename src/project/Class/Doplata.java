package project.Class;

public class Doplata {
    public int ID;
    public String TypDoplaty;
    public Float KwotaD;

    public Float getKwotaD() {
        return KwotaD;
    }

    public void setKwotaD(Float kwota) {
        KwotaD = kwota;
    }

    public String getTypDoplaty() {
        return TypDoplaty;
    }

    public void setTypDoplaty(String typDoplaty) {
        TypDoplaty = typDoplaty;
    }

    public Doplata(String typDoplaty, Float kwotaD) {
        TypDoplaty = typDoplaty;
        KwotaD=kwotaD;
    }
}
