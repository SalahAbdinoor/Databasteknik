package Tabeller;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 14:19
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class Kund {

    private int id;
    private String förnamn;
    private  String efternamn;
    private String address;
    private String mail;
    private int telefonnummer;
    private int ortID;
    private String lösenord;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFörnamn() {
        return förnamn;
    }

    public void setFörnamn(String förnamn) {
        this.förnamn = förnamn;
    }

    public void setEfternamn(String efternamn) {
        this.efternamn = efternamn;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTelefonnummer(int telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public void setOrtID(int ortID) {
        this.ortID = ortID;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }
}
