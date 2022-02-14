/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import static org.junit.Assert.*;
import org.junit.Test;

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
                + "	\"db_name\" : \"CEMS\",\n"
                + "	\"db_user\" : \"root\",\n"
                + "	\"db_password\" : \"maria\"\n"
                + "}";
        String result = fileIO.readStringFromFile(filename);
        assertEquals(expResult, result);
    }

}
