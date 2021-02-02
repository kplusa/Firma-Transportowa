package project.Builder;

public interface Builder {

    public void buildId(int ID); //step 1

    public void buildProperties(String AdresPoczatkowy, String AdresKoncowy, String DataNadania, int Ilosc); //step 2

    public void buildStatus(String status); //step 3

    public void buildKurier(String Kurier); //step 4

    public void buildOddzial(String Oddzialp, String Oddzialk); //step 5

    public ZlecenieProduct getResult();
}
