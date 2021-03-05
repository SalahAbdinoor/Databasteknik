package Logic;

import SQL.importSQLData;
import Tabeller.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

/**
 * Created by Daniel Bojic
 * Date: 2021-02-21
 * Time: 00:40
 * Project: Inlämningsuppgift 2
 * Copyright: MIT
 */
public class GUI {
    importSQLData imp = new importSQLData();

    Connection con = null;
    Statement stmt;

    int purchase = 0;

    String username;
    String password;

    List<Kund> kunder = imp.getKunder();
    List<Produkt> produkter = imp.getProdukter();
    List<Beställning> beställningar = imp.getBeställningar();
    List<Skickar> skickar = imp.getSkickar();
    List<Recension> recensions = imp.getRecensions();
    List<Betyg> betygs = imp.getBetygen();
    List<Beskriver> beskrivers = imp.getBeskrivers();

    Kund loggedInKund = new Kund();

    List<Produkt> produkterILager = new ArrayList<>();

    List<Produkt> varukorg = new ArrayList<>();

    public void refresh() throws SQLException {

        imp.updateData();
        kunder = imp.getKunder();
        produkter = imp.getProdukter();
        skickar = imp.getSkickar();
        recensions = imp.getRecensions();
        betygs = imp.getBetygen();
        beskrivers = imp.getBeskrivers();

    }


    public void startServer() throws SQLException {
        connectToAndQueryDatabase("salah", "salah");
    }

    public void connectToAndQueryDatabase(String username, String
            password) throws SQLException {


        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/database_webshop", username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        stmt = con.createStatement();
        spaaaace();
        System.out.println("Välkommen till ShoeShop!");
        spaaaace();
        logInUI();

    }


    public void mainMeny() throws SQLException {
        Scanner in = new Scanner(System.in);
        purchase = 0;
        int alternativ = 0;
        spaaaace();
        System.out.println("Välkommen " + loggedInKund.getFörnamn() + "!");
        spaaaace();
        System.out.println("Välj ett alternativ!");
        System.out.println("0. Byt användare");
        System.out.println("1. Gör en ny beställning");
        System.out.println("2. Kolla tidigare beställningar");
        System.out.println("3. Kolla medelbetyg");
        System.out.println("4. Skriv en recension");
        System.out.println("5. Stäng av");
        spaaaace();

        String alt = in.nextLine();
        try {
            alternativ = Integer.parseInt(alt);
        }catch (Exception e){
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            mainMeny();
        }
        

        switch (alternativ) {
            case 0 -> {
                spaaaace();
                logInUI();
            }
            case 1 -> selectAllProducts();
            case 2 -> kollaBeställningar();
            case 3 -> findAverageBetyg();
            case 4 -> skrivRecension();
            case 5 -> closeProgram();
            default -> {
                spaaaace();
                System.out.println("Ett fel uppstod!");
                spaaaace();
                mainMeny();
            }
        }

    }

    public void kollaBeställningar() throws SQLException {

        refresh();

        List<Beställning> tempB = new ArrayList<>();

        for (Beställning be : beställningar) {
            if (be.getKundid() == loggedInKund.getId()) {
                tempB.add(be);
            }
        }

        List<Beställning> unikaID = new ArrayList<>();

        for (Beställning beID : tempB) {

            if (!unikaID.contains(beID.getKöpnr())) {
                unikaID.add(beID);
            }
        }


        List<Beställning> sistaListanJagLovar = new ArrayList<>();

        int tempköpnr = 0;
        int tempköpsumma = 0;
        for (Beställning beställning : unikaID) {
            if (beställning.getKöpnr() != tempköpnr) {
                tempköpnr = beställning.getKöpnr();
                sistaListanJagLovar.add(beställning);

                for (Beställning best : beställningar) {
                    if (best.getKöpnr() == beställning.getKöpnr()) {
                        tempköpsumma = tempköpsumma + beställning.getSumma();
                    }
                }

                sistaListanJagLovar.get(sistaListanJagLovar.size() - 1).setSumma(tempköpsumma);
                tempköpsumma = 0;

            }
        }

        spaaaace();
        System.out.println("Beställningar från: " + loggedInKund.getFörnamn());
        spaaaace();
        for (Beställning printOrder : sistaListanJagLovar) {
            System.out.println("OrderID: " + printOrder.getKöpnr());
            for (Skickar findItems : skickar) {
                if (findItems.getKöpnr() == printOrder.getKöpnr()) {
                    for (Produkt pro : produkter) {
                        if (pro.getId() == findItems.getProduktid()) {
                            System.out.println(pro.getNamn() + " " + pro.getPris() + "kr");
                        }
                    }
                }
            }
            System.out.println("Totalsumma: " + printOrder.getSumma() + "kr");
            System.out.println("Datum: " + printOrder.getDatum());
            spaaaace();
        }

        mainMeny();

    }

    public void logInUI() throws SQLException {

        Scanner in = new Scanner(System.in);
        boolean loggedin = false;

        refresh();


        System.out.print("Användarnamn: ");
        username = in.nextLine();
        System.out.print("Lösenord: ");
        password = in.nextLine();


        for (Kund kund : kunder) {
            if (kund.getFörnamn().equalsIgnoreCase(username) && kund.getLösenord().equalsIgnoreCase(password)) {
                loggedin = true;
                loggedInKund = kund;
            }
        }

        if (loggedin) {
            mainMeny();

        } else {
            spaaaace();
            System.out.println("Inloggning misslyckades! Försök igen!");
            spaaaace();
            logInUI();
        }
        selectAllProducts();
    }

    public void selectAllProducts() throws SQLException {
        Scanner in = new Scanner(System.in);
        int product = 0;

        produkterILager.clear();

        for (Produkt temp : produkter) {
            if (temp.getLager() > 0) {
                produkterILager.add(temp);
            }
        }

        int counter = 0;
        spaaaace();
        System.out.println("0. Tillbaka");
        for (Produkt tempish : produkterILager) {
            counter++;
            System.out.println(counter + ". " + tempish.getNamn());
        }
        System.out.println((produkterILager.size() + 1) + ". Varukorg");
        spaaaace();

        String answerToFind = in.nextLine();
        
        try{
            product = Integer.parseInt(answerToFind);
        }catch (Exception e){
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            selectAllProducts();
        }
        

        if (product == 0) {
            mainMeny();
        } else if (product == (produkterILager.size() + 1) && product > 0) {
            varukorg();
        } else if (product  < produkterILager.size() && product > 0){
            int ship = produkterILager.get(product - 1).getId();

            showProductInfo(ship);
        }else {
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            selectAllProducts();
        }

    }

    public void showProductInfo(int id) throws SQLException {
        Scanner in = new Scanner(System.in);
        Produkt currentProduct = new Produkt();
        int findProductToSell = 0;
        int foundIt = 0;
        for (Produkt find : produkterILager) {
            if (find.getId() == id) {
                foundIt = findProductToSell;
                currentProduct = find;
            }
            findProductToSell++;
        }
        spaaaace();

        System.out.println(currentProduct.getNamn());
        System.out.println("Pris: " + currentProduct.getPris() + "kr");
        System.out.println("Storlek: " + currentProduct.getStorlek());
        System.out.println("Färg: " + currentProduct.getFärg());
        System.out.println("Lagerstatus: " + currentProduct.getLager() + "st");
        System.out.println("0. Tillbaka");
        System.out.println("1. Lägg till i varukorgen");
        spaaaace();

        String choice = in.nextLine();
        int choicee = 0;
        try{
            choicee = Integer.parseInt(choice);
        }catch (Exception e){
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            showProductInfo(id);
        }

         

        if (choicee == 0) {
            selectAllProducts();
        } else if (choicee == 1) {
            varukorg.add(currentProduct);
            purchase++;
            spaaaace();
            System.out.println("Tillagd i varukorgen!");
            spaaaace();
            produkterILager.get(foundIt).setLager(produkterILager.get(foundIt).getLager() - 1);
            if (produkterILager.get(foundIt).getLager() == 0) {
                produkterILager.remove(foundIt);
                selectAllProducts();
            } else {
                showProductInfo(id);
            }


        }

    }

    public void varukorg() throws SQLException {
        Scanner in = new Scanner(System.in);
        int totalVarukorg = 0;
        if (purchase == 0) {
            spaaaace();
            System.out.println("Varukorgen är tom!");
            spaaaace();
            selectAllProducts();
        } else {
            spaaaace();
            System.out.println("Produkter i varukorgen: ");
            for (Produkt produktIKorg : varukorg) {
                System.out.println(produktIKorg.getNamn() + " " + produktIKorg.getPris() + "kr");
                totalVarukorg = totalVarukorg + produktIKorg.getPris();
            }
            System.out.println("Totalt: " + totalVarukorg + "kr");
            System.out.println("0. Tillbaka");
            System.out.println("1. Köp");
            spaaaace();

            String next = in.nextLine();
            int nextInt = 0; 
            try{
                nextInt = Integer.parseInt(next);
            }catch (Exception e){
                spaaaace();
                System.out.println("Ett fel uppstod!");
                spaaaace();
                varukorg();
            }
            

            if (nextInt == 0) {
                selectAllProducts();
            } else {
                purchaseProduct();
            }
        }


    }

    public void findAverageBetyg() throws SQLException {
        Scanner in = new Scanner(System.in);
        int a = 0;

        spaaaace();
        System.out.println("Välj en produkt!");
        System.out.println("0. Tillbaka");
        for (Produkt pro : produkter) {
            a++;
            System.out.println(a + ". " + pro.getNamn());
        }
        spaaaace();
        String answer = in.nextLine();
        
        int tempCount = 0;
        try {
           tempCount = Integer.parseInt(answer);
        }catch (Exception e){
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            findAverageBetyg();
        }
        
        if (tempCount == 0){
            mainMeny();
        }else if (tempCount -1 < produkter.size() && tempCount > 0){
            List<Recension> recensionUnique = new ArrayList<>();
            List<Recension> recensionWithNoBetyg = new ArrayList<>();



            for (Recension recenTemp : recensions){
                if (recenTemp.getProduktid() == tempCount){
                    recensionUnique.add(recenTemp);
                }
            }

            List<String> kommentarer = new ArrayList<>();

            int finalanswer = 0;
            for (Recension res : recensionUnique){
                if (res.getBetygid() == 0){
                    recensionWithNoBetyg.add(res);
                }else {
                    kommentarer.add(res.getKommentar());
                    finalanswer = finalanswer + res.getBetygid();
                }
            }



            if (finalanswer == 0){
                spaaaace();
                System.out.println("Produkten har inte blivit betygsatt!");
                spaaaace();
                findAverageBetyg();
            }else {
                finalanswer = finalanswer / (recensionUnique.size()-recensionWithNoBetyg.size());

                spaaaace();
                System.out.println(produkter.get(tempCount-1).getNamn());
                System.out.println("Medelbetyg: " + finalanswer);
                System.out.println();
                System.out.println("Kommentarer: ");
                for (String comments : kommentarer){
                    comments.trim();
                    if (!comments.equals("")){
                        System.out.println("\"" + comments + "\"");
                    }
                }
                spaaaace();
                findAverageBetyg();
            }


        }else {
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            findAverageBetyg();
        }



    }

    public void skrivRecension() throws SQLException {
        Scanner in = new Scanner(System.in);
        int countItems = 0;
        spaaaace();
        System.out.println("Välj en produkt att recensera");
        System.out.println("0. Tillbaka");

        for (Produkt alla : produkter){
            countItems++;
            System.out.println(countItems+ ". " +alla.getNamn());
        }
        spaaaace();
        String answer = in.nextLine();
        int answerDigit = 0;
        try{
            answerDigit = Integer.parseInt(answer);
        }catch (Exception e){
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            skrivRecension();
        }
         



        if (answerDigit == 0) {
            mainMeny();
        }else if (answerDigit -1 < produkter.size() && answerDigit > 0){
            spaaaace();
            System.out.println("Välj ett betyg för: " + produkter.get(answerDigit-1).getNamn());
            System.out.println("1. " + betygs.get(1).getOmdöme());
            System.out.println("2. " + betygs.get(2).getOmdöme());
            System.out.println("3. " + betygs.get(3).getOmdöme());
            System.out.println("4. " + betygs.get(4).getOmdöme());
            spaaaace();
            int betygRate = 0;
            String betygRec = in.nextLine();
            try {
                betygRate = Integer.parseInt(betygRec);
            }catch (Exception e){
                spaaaace();
                System.out.println("Ett fel uppstod!");
                spaaaace();
                skrivRecension();
            }



            spaaaace();
            System.out.println("Skriv en kommentar");
            spaaaace();

            String kommentarRate = in.nextLine();

            CallableStatement stm = con.prepareCall("Call rate(?,?,?,?)");
            stm.setInt(1, betygRate);
            stm.setString(2, kommentarRate);
            stm.setInt(3, loggedInKund.getId());
            stm.setInt(4, answerDigit);
            stm.execute();

            spaaaace();
            System.out.println("Det gick igenom!");

            refresh();

            spaaaace();



            skrivRecension();

        }else {
            spaaaace();
            System.out.println("Ett fel uppstod!");
            spaaaace();
            skrivRecension();
        }




    }

    public int findMaxBeställningNr() {
        int tempNr = 0;

        for (Beställning best : beställningar) {
            if (best.getKöpnr() > tempNr) {
                tempNr = best.getKöpnr();
            }
        }
        return tempNr + 1;
    }

    public void purchaseProduct() throws SQLException {

        try{
            CallableStatement stm;

            int maxNumber = findMaxBeställningNr();
            con.setAutoCommit(false);

            for (int i = 0; i < varukorg.size(); i++) {
                stm = con.prepareCall("call AddToCart((SELECT id from kund where förnamn = ?), ? ,?)");
                stm.setString(1, loggedInKund.getFörnamn());
                stm.setInt(2, maxNumber);
                stm.setInt(3, varukorg.get(i).getId());
                stm.execute();
            }
            con.commit();

            System.out.println("Tabeller.Beställning lagd!");
            purchase = 1;

            refresh();



        }catch (SQLException e){
            con.rollback();
            System.out.println("Ett fel uppstod med beställningen.");
            e.printStackTrace();
        }



    }

    public void closeProgram() throws SQLException {
        System.out.println("Välkommen åter!");
        imp.closeBackend();
        con.close();
        stmt.close();
        System.exit(0);
    }

    public void spaaaace() {
        System.out.println("\n------------------------------------------------\n");
    }

    public static void main(String[] args) throws SQLException {
        new GUI().startServer();


    }
}
