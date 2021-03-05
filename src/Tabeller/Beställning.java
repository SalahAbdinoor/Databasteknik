package Tabeller;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 16:51
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Beställning {

    private int id;
    private  int köpnr;
    private  int summa;
    private String datum;
    private int kundid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKöpnr() {
        return köpnr;
    }

    public void setKöpnr(int köpnr) {
        this.köpnr = köpnr;
    }

    public int getSumma() {
        return summa;
    }

    public void setSumma(int summa) {
        this.summa = summa;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getKundid() {
        return kundid;
    }

    public void setKundid(int kundid) {
        this.kundid = kundid;
    }
}
