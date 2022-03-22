/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dbmanager.DBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utilities.fileIO.readStringFromFile;

/**
 *
 * @author maria
 */
public class Ejecutable {

    public static void main(String[] args) {
        //imprimimos la lista de metabolitos que tenemos
        List<Metabolito> metabolitos = Fichero.leerFichero();
        for (Metabolito m : metabolitos) {
            System.out.println(m);
        }

        //conectamos con la database
        DBManager db = new DBManager();
        String filename = "resources/connectionData.pass";
        try {
            Gson gson = new Gson();
            String readJSONStr = readStringFromFile(filename);
            JsonElement element = gson.fromJson(readJSONStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            String dbName = jsonObj.get("db_name").getAsString();
            String dbUser = jsonObj.get("db_user").getAsString();
            String dbPassword = jsonObj.get("db_password").getAsString();

            db.connectToDB("jdbc:mysql://localhost/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", dbUser, dbPassword);

            //insertamos los metabolitos leidos
            for (Metabolito m : metabolitos) {
                db.insertMetabolito(m);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ioe);
        }
    }
    /*public static void main(String[] args) {
        //imprimimos la lista de metabolitos que tenemos
        List<Metabolito> metabolitos = Fichero.leerFichero();
        for (Metabolito m : metabolitos) {
            System.out.println(m);
        }
    }*/

 /*public static void main(String[] args) {

        // Testing regex
        String myString = "44.9989(0V), 79.9577, 130.0878";
        Pattern pattern = Pattern.compile("[1-9][0-9]*\\.[0-9]*");
        Matcher matcher = pattern.matcher(myString);

        while (matcher.find()) {
            String myMatch = matcher.group();
            System.out.println("Match found: " + myMatch);
        }

        //imprimimos la lista de metabolitos que tenemos
        List<Metabolito> metabolitos = Fichero.leerFichero();

        for (Metabolito m : metabolitos) {
            System.out.println(m);
        }
    }*/
}

/*String s = "44.9989 (0V), 79.9576, 130.0877";
        String[] p = s.split(",");
        List<Double> d = new LinkedList<Double>();

        for (String a : p) {
            System.out.println(a);

            try {
                d.add(Double.parseDouble(a));   //si no se puede meter, es porque no solo hay numeros
                System.out.println(d);
            } catch (NumberFormatException e) { //si llegamos al catch es porque hay texto a parte de numeros
                String[] sinProblemas = a.split("[(]");
                System.out.println("hola: " + sinProblemas[0]);
                Double t = Double.parseDouble(sinProblemas[0]);
                System.out.println("adios: " + t);
                d.add(Double.parseDouble(sinProblemas[0]));
                System.out.println(d);
            }

        }*/
