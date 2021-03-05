package Tabeller;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 19:06
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Betyg {
    private int id;
    private int betygnr;
    private String omdöme;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBetygnr(int betygnr) {
        this.betygnr = betygnr;
    }

    public String getOmdöme() {
        return omdöme;
    }

    public void setOmdöme(String omdöme) {
        this.omdöme = omdöme;
    }
}
