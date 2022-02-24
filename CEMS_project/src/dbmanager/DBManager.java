/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import cems_project.Metabolito;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utilities.fileIO.readStringFromFile;

/**
 *
 * @author alberto.gildelafuent
 */
public class DBManager {

    protected Connection connection;
    protected Statement statement;

    /**
     * Method to connect to the database
     *
     * @param bd JDBC String to coonect -> Example
     * "jdbc:mysql://localhost/<DATABASE_NAME>/?useSSL=false&serverTimezone=UuseSSLTC
     * @param usuario username of the database -> Example -> Root
     * @param clave "password of the database" -> example -> password
     */
    public void connectToDB(String bd, String usuario, String clave) {
        try {
            // MySQL driver registered
            //DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());

            // get DatabaseConnection
            connection = DriverManager.getConnection(bd, usuario, clave);

            statement = this.connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * It executes the query and returns the first int returned by the query.
     *
     * @param query
     * @return the ID of the query or 0 if the result is null
     */
    public int getInt(String query) {
        int id = -1;
        // Be aware that the connection should be initialized (calling the method connectToDB)
        try {
            ResultSet rs = statement.executeQuery(query);   //es un iterador

            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * TO DO METHOD TO GET A STRING FROM SQL It executes the query and returns
     * the first String returned by the query
     *
     * @param query
     * @return the String of the query or ??? if the result is null
     */
    public String getString(String query) {
        String word = null;
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                word = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return word;
    }

    /**
     * TO DO Metodo para obtener un metabolito por JDBC. It should throw a
     * exception if the query is not from metabolites
     *
     * @param query the SQL query to execute. It should contain the attributes:
     * @return
     */
    private Metabolito getMetabolito(String query) {
        try {
            statement.execute(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                //int id = rs.getInt("id");
                int id = rs.getInt(1);
                System.out.println("ID: " + id);

                //String Compd_name = rs.getString("Compound_name");
                String Compd_name = rs.getString(2);
                System.out.println("Compound name: " + Compd_name);

                String formula = rs.getString(3);
                System.out.println("Formula: " + formula);
                double m_mass = rs.getDouble(4);
                System.out.println("Monoisotopic Mass: " + m_mass);
                double m_z = rs.getDouble(5);
                System.out.println("M_z: " + m_z);
                double mt_compnd = rs.getDouble(6);
                System.out.println("MT_compnd: " + mt_compnd);
                double mt_mets = rs.getDouble(7);
                System.out.println("MT_mets: " + mt_mets);
                double rmt_mets = rs.getDouble(8);
                System.out.println("RMT_mets: " + rmt_mets);
                double mt_mes = rs.getDouble(9);
                System.out.println("MT_mes: " + mt_mes);
                double rmt_mes = rs.getDouble(10);
                System.out.println("RMT_mes: " + rmt_mes);

                // Select de los fragmentos. Creando un metodo getFragmentos, solo reciba el parámetro int ID
                Metabolito m1 = new Metabolito(Compd_name, formula, m_mass, m_z, mt_compnd, mt_mets, rmt_mets, mt_mes, rmt_mes, null);
                return m1;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param metabolito
     * @throws Exception
     */
    public void insertMetabolito(Metabolito metabolito) throws Exception {

        // mediante una llamada al metodo insertMetabolito (devuelve el ID generado.
        // mediante otra llamada la insercion de los fragmentos.
    }

    /**
     *
     * @param query
     * @return the ID of the query or 0 if the result is null
     */
    public int exampleQueryToGetTheLastGeneratedIdFromAnInsert(String query) {
        int id = 0;
        // Be aware that the connection should be initialized (calling the method connectToDB

        try {

            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet provRS = statement.getGeneratedKeys()) {
                if (provRS.next()) {
                    id = provRS.getInt(1);
                }
                provRS.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("\n GENERATED KEY of " + actualizacion + " : " + id);
        return id;
    }

    public static void main(String[] args) {

        DBManager db = new DBManager();
        String filename = "resources/connectionData.pass";  //este file contiene los datos de acceso a la database (notación JSON: clave-valor)
        try {
            Gson gson = new Gson();
            String readJSONStr = readStringFromFile(filename);
            JsonElement element = gson.fromJson(readJSONStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            String dbName = jsonObj.get("db_name").getAsString();
            String dbUser = jsonObj.get("db_user").getAsString();
            String dbPassword = jsonObj.get("db_password").getAsString();

            // Here you can check the values obtained
            //System.out.println("DB_NAME: " + dbName + " DBUser: " + dbUser + " DBPassword: " + dbPassword);
            db.connectToDB("jdbc:mysql://localhost/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", dbUser, dbPassword);

            //GET INT
            //int id = db.getInt("Select 10");
            //System.out.println(id);
            //GET STRING
            //String word = db.getString("Select \"asd\"");
            //System.out.println(word);
            //GETMETABOLITO
            Metabolito m1 = db.getMetabolito("Select * from metabolites");
            System.out.println("Metabolito 1: " + m1);

            // SI NO INSERTA NADA, AUTO_GENERATED KEYS DEVUELVE 0
            /*int id_updated = db.exampleQueryToGetTheLastGeneratedIdFromAnInsert("update prueba set f1 = 2 where id=2");
            System.out.println(id_updated);
            int id_inserted = db.exampleQueryToGetTheLastGeneratedIdFromAnInsert("insert into prueba (f1) values (25)");
            System.out.println(id_inserted);*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ioe) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ioe);
        }
    }
}
