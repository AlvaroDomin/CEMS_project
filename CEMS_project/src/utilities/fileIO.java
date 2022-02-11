/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alberto.gildelafuent
 */
public class fileIO {

    /**
     * Method to return the String from a plain text file
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readStringFromFile(String filePath) throws IOException {

        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded, Charset.defaultCharset()).replace("\r", "");

    }

    public static void main(String[] args) {
        try {
            String filename = "resources/connectionData.pass";

            String result = fileIO.readStringFromFile(filename);
            System.out.println(result);
        } catch (IOException ex) {
            Logger.getLogger(fileIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
