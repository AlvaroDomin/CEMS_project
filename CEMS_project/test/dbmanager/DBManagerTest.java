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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import static utilities.fileIO.readStringFromFile;

/**
 *
 * @author maria
 */
public class DBManagerTest {

    private static DBManager dbmanager;

    public DBManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        try {
            dbmanager = new DBManager();
            String filename = "resources/connectionData.pass";
            Gson gson = new Gson();
            String readJSONStr = readStringFromFile(filename);
            JsonElement element = gson.fromJson(readJSONStr, JsonElement.class);
            JsonObject jsonObj = element.getAsJsonObject();
            String dbName = jsonObj.get("db_name").getAsString();
            String dbUser = jsonObj.get("db_user").getAsString();
            String dbPassword = jsonObj.get("db_password").getAsString();
            // Here you can check the values obtained
            //System.out.println("DB_NAME: " + dbName + " DBUser: " + dbUser + " DBPassword: " + dbPassword);
            dbmanager.connectToDB("jdbc:mysql://localhost/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", dbUser, dbPassword);
        } catch (IOException ex) {
            Logger.getLogger(DBManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of connectToDB method, of class DBManager.
     */
    @Test
    public void testConnectToDB() {
        System.out.println("connectToDB");
        int exp_result = 1;
        int result = dbmanager.getInt("Select 1");
        assertEquals(exp_result, result);
    }

    /**
     * Test of getString method, of class DBManager.
     */
    @Test
    public void testGetString() {
        System.out.println("getString");
        String query = "select \"asd\"";
        String expResult = "asd";
        String result = dbmanager.getString(query);
        assertEquals(expResult, result);
    }

    /**
     * Test of insertMetabolito method, of class DBManager.
     */
    @Test
    public void testInsertMetabolito() throws Exception {
        System.out.println("insertMetabolito");
        Metabolito metabolito = null;
        DBManager instance = new DBManager();
        instance.insertMetabolito(metabolito);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exampleQueryToGetTheLastGeneratedIdFromAnInsert method, of class
     * DBManager.
     */
    @Test
    public void testExampleQueryToGetTheLastGeneratedIdFromAnInsert() {
        System.out.println("exampleQueryToGetTheLastGeneratedIdFromAnInsert");
        String query = "";
        DBManager instance = new DBManager();
        int expResult = 0;
        int result = instance.exampleQueryToGetTheLastGeneratedIdFromAnInsert(query);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class DBManager.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        DBManager.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
