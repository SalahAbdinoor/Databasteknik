package Tabeller;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 19:32
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Recension {
    private int id;
    private String kommentar;
    private String datum;
    private int betygid;
    private int produktid;
    private int kundid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getBetygid() {
        return betygid;
    }

    public void setBetygid(int betygid) {
        this.betygid = betygid;
    }

    public int getProduktid() {
        return produktid;
    }

    public void setProduktid(int produktid) {
        this.produktid = produktid;
    }

    public void setKundid(int kundid) {
        this.kundid = kundid;
    }
}
