package Tabeller;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 19:51
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Beskriver {
    private int id;
    private int recensionid;
    private int betygnr;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRecensionid(int recensionid) {
        this.recensionid = recensionid;
    }

    public void setBetygnr(int betygnr) {
        this.betygnr = betygnr;
    }
}
