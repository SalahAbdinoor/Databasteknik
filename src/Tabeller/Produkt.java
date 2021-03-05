package Tabeller;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 15:25
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Produkt {
    private int id;
    private String namn;
    private int pris;
    private int storlek;
    private String färg;
    private int märkeid;
    private int lager;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public int getStorlek() {
        return storlek;
    }

    public void setStorlek(int storlek) {
        this.storlek = storlek;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public void setMärkeid(int märkeid) {
        this.märkeid = märkeid;
    }

    public int getLager() {
        return lager;
    }

    public void setLager(int lager) {
        this.lager = lager;
    }
}
