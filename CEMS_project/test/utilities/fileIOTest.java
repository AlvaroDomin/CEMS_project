/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alberto.gildelafuent
 */
public class fileIOTest {

    public fileIOTest() {
    }

    /**
     * Test of readStringFromFile method, of class fileIO.
     */
    @Test
    public void testReadStringFromFile() throws Exception {

        String filename = "resources/connectionData.pass";

        System.out.println("readStringFromFile");
        String expResult = "{\n"
                + "	\"db_name\" : \"prueba\",\n"
                + "	\"db_user\" : \"a\",\n"
                + "	\"db_password\" : \"a\"\n"
                + "}";
        String result = fileIO.readStringFromFile(filename);
        assertEquals(expResult, result);
    }

}
