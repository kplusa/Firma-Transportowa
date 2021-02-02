package project.Builder;

public class ZlecenieBuilder implements Builder {

    public ZlecenieProduct zlecenie = new ZlecenieProduct();

    @Override
    public void buildId(int Id) {
        zlecenie.ID = Id;
    }

    @Override
    public void buildProperties(String AdresPoczatkowy, String AdresKoncowy, String DataNadania, int Ilosc) {
        zlecenie.AdresPoczatkowy = AdresPoczatkowy;
        zlecenie.AdresKoncowy = AdresKoncowy;
        zlecenie.DataNadania = DataNadania;
        zlecenie.Ilosc = Ilosc;
    }

    @Override
    public void buildStatus(String status) {
        zlecenie.status = status;
    }

    @Override
    public void buildKurier(String Kurier) {
        zlecenie.Kurier = Kurier;
    }

    @Override
    public void buildOddzial(String Oddzialp, String Oddzialk) {
        zlecenie.oddzialPoczatkowy = Oddzialp;
        zlecenie.oddzialKoncowy = Oddzialk;
    }

    @Override
    public ZlecenieProduct getResult() {
        return zlecenie;
    }

}
