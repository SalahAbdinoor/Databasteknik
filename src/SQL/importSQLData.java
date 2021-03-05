package SQL;

import Tabeller.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 15:17
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class importSQLData {

    Connection con = null;
    ResultSet rs;
    Statement stmt;

    List<Kund> kunder = new ArrayList<>();
    List<Produkt> produkter = new ArrayList<>();
    List<Beställning> beställningar = new ArrayList<>();
    List<Skickar> skickar = new ArrayList<>();
    List<Ort> orten = new ArrayList<>();
    List<Betyg> betygen = new ArrayList<>();
    List<Märke> märken = new ArrayList<>();
    List<Kategori> kategorier = new ArrayList<>();
    List<Tillhör> tillhörs = new ArrayList<>();
    List<slutILager> slutILagers = new ArrayList<>();
    List<Recension> recensions = new ArrayList<>();
    List<Beskriver> beskrivers = new ArrayList<>();

    public List<Beskriver> getBeskrivers() {
        return beskrivers;
    }

    public List<Betyg> getBetygen() {
        return betygen;
    }

    public List<Recension> getRecensions() {
        return recensions;
    }

    public List<Skickar> getSkickar() {
        return skickar;
    }

    public List<Beställning> getBeställningar() {
        return beställningar;
    }

    public List<Kund> getKunder() {
        return kunder;
    }

    public List<Produkt> getProdukter() {
        return produkter;
    }

    public void updateData() throws SQLException {

        connectToAndQueryDatabase("salah", "salah");

        updateKunder();
        updateProdukter();
        updateBeställning();
        updateSkickar();
        updateOrt();
        updateBetyg();
        updateMärke();
        updateKategori();
        updateTillhör();
        updateSlutILager();
        updateRecension();
        updateBeskriver();

    }

    public void updateKunder() throws SQLException {

        kunder.clear();

        rs = stmt.executeQuery("select id, förnamn,efternamn,adress, mail,telefonnummer,ortid,lösenord from kund");


        while (rs.next()) {
            
            Kund temp = new Kund();
            temp.setId(rs.getInt("id"));
            temp.setFörnamn(rs.getString("förnamn"));
            temp.setEfternamn(rs.getString("efternamn"));
            temp.setAddress(rs.getString("adress"));
            temp.setMail(rs.getString("mail"));
            temp.setTelefonnummer(rs.getInt("telefonnummer"));
            temp.setOrtID(rs.getInt("ortid"));
            temp.setLösenord(rs.getString("lösenord"));

            kunder.add(temp);

        }

    }

    public void updateBeställning() throws SQLException {

        beställningar.clear();

        rs = stmt.executeQuery("select id, köpnr,summa,datum, kundid from beställning");


        while (rs.next()) {
            Beställning temp = new Beställning();
            temp.setId(rs.getInt("id"));
            temp.setKöpnr(rs.getInt("köpnr"));
            temp.setSumma(rs.getInt("summa"));
            temp.setDatum(rs.getString("datum"));
            temp.setKundid(rs.getInt("kundid"));

            beställningar.add(temp);
        }

    }

    public void updateProdukter() throws SQLException {

        produkter.clear();

        rs = stmt.executeQuery("select id, namn,pris,storlek, färg,märkeid,lager from produkt");


        while (rs.next()) {
            Produkt temp = new Produkt();
            temp.setId(rs.getInt("id"));
            temp.setNamn(rs.getString("namn"));
            temp.setPris(rs.getInt("pris"));
            temp.setStorlek(rs.getInt("storlek"));
            temp.setFärg(rs.getString("färg"));
            temp.setMärkeid(rs.getInt("märkeid"));
            temp.setLager(rs.getInt("lager"));

            produkter.add(temp);
        }

    }

    public void updateSkickar() throws SQLException {

        skickar.clear();

        rs = stmt.executeQuery("select id, köpnr,produktid from skickar");


        while (rs.next()) {
            Skickar temp = new Skickar();
            temp.setId(rs.getInt("id"));
            temp.setKöpnr(rs.getInt("köpnr"));
            temp.setProduktid(rs.getInt("produktid"));


            skickar.add(temp);
        }

    }

    public void updateOrt() throws SQLException {

        orten.clear();

        rs = stmt.executeQuery("select id, namn from ort");


        while (rs.next()) {
            Ort temp = new Ort();
            temp.setId(rs.getInt("id"));
            temp.setNamn(rs.getString("namn"));


            orten.add(temp);
        }

    }

    public void updateBetyg() throws SQLException {
        betygen.clear();

        rs = stmt.executeQuery("select id, betygnr, omdöme from betyg");

        while (rs.next()) {
            Betyg temp = new Betyg();
            temp.setId(rs.getInt("id"));
            temp.setBetygnr(rs.getInt("betygnr"));
            temp.setOmdöme(rs.getString("omdöme"));

            betygen.add(temp);
        }

    }

    public void updateMärke() throws SQLException {
        märken.clear();

        rs = stmt.executeQuery("select id, namn from märke");

        while (rs.next()) {
            Märke temp = new Märke();
            temp.setId(rs.getInt("id"));
            temp.setNamn(rs.getString("namn"));

            märken.add(temp);
        }

    }

    public void updateKategori() throws SQLException {
        kategorier.clear();

        rs = stmt.executeQuery("select id, namn from kategori");

        while (rs.next()) {
            Kategori temp = new Kategori();
            temp.setId(rs.getInt("id"));
            temp.setNamn(rs.getString("namn"));

            kategorier.add(temp);
        }
    }

    public void updateTillhör() throws SQLException {
        tillhörs.clear();

        rs = stmt.executeQuery("select id, kategoriid,produktid from tillhör");

        while (rs.next()) {
            Tillhör temp = new Tillhör();
            temp.setId(rs.getInt("id"));
            temp.setKategoriID(rs.getInt("kategoriid"));
            temp.setProduktID(rs.getInt("produktid"));

            tillhörs.add(temp);
        }


    }

    public void updateSlutILager() throws SQLException {
        slutILagers.clear();

        rs = stmt.executeQuery("select id, datum,produktid from slutilager");

        while (rs.next()) {
            slutILager temp = new slutILager();
            temp.setId(rs.getInt("id"));
            temp.setDatum(rs.getString("datum"));
            temp.setProduktid(rs.getInt("produktid"));

            slutILagers.add(temp);
        }


    }

    public void updateRecension() throws SQLException {
        recensions.clear();

        rs = stmt.executeQuery("select id, kommentar,datum,betygid,produktid,kundid from recension");

        while (rs.next()) {
            Recension temp = new Recension();
            temp.setId(rs.getInt("id"));
            temp.setKommentar(rs.getString("kommentar"));
            temp.setDatum(rs.getString("datum"));
            temp.setBetygid(rs.getInt("betygid"));
            temp.setProduktid(rs.getInt("produktid"));
            temp.setKundid(rs.getInt("kundid"));

            recensions.add(temp);
        }

    }

    public void updateBeskriver() throws SQLException {
        beskrivers.clear();

        rs = stmt.executeQuery("select id, recensionid, betygnr from beskriver");

        while (rs.next()) {
            Beskriver temp = new Beskriver();
            temp.setId(rs.getInt("id"));
            temp.setRecensionid(rs.getInt("recensionid"));
            temp.setBetygnr(rs.getInt("betygnr"));

            beskrivers.add(temp);
        }

    }

    public void closeBackend() throws SQLException {
        rs.close();
        stmt.close();
        con.close();
    }


    public void connectToAndQueryDatabase(String username, String
            password) throws SQLException {


        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/database_webshop", username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        stmt = con.createStatement();


    }
}
