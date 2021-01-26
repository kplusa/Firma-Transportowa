package project.Builder;

public class Director {
    public ZlecenieBuilder builder;

    public Director() {
        this.builder = new ZlecenieBuilder();
    }

    public void setBuilder(ZlecenieBuilder builder) {
        this.builder = builder;
    }

    public ZlecenieBuilder getZlecenieStatusKurier(int id, String Status, String kurier) {
        builder.buildId(id);
        builder.buildStatus(Status);
        builder.buildKurier(kurier);
        return builder;
    }

    public ZlecenieProduct getZlecenieAdresyIlosc(int id, String adresPoczatkowy, String adresKoncowy, String dataNadania, int ilosc) {
        builder.buildId(id);
        builder.buildProperties(adresPoczatkowy, adresKoncowy, dataNadania, ilosc);
        return builder.getResult();
    }

    public ZlecenieProduct getZlecenieOddzialy(String oddzialPoczatkowy, int id, String oddzialKoncowy) {
        builder.buildId(id);
        builder.buildOddzial(oddzialPoczatkowy, oddzialKoncowy);
        return builder.getResult();
    }

    public ZlecenieProduct getZlecenieAdresyStatus(int id, String data, String adresP, String adresK, String status, int ilosc) {
        builder.buildId(id);
        builder.buildProperties(adresP, adresK, data, ilosc);
        builder.buildStatus(status);
        return builder.getResult();
    }
}
