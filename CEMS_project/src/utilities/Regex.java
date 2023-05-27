/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author maria
 */
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static List<String> getMainPart(List<String> inchisCompletas) {

        List<String> inchisMain = new LinkedList();
        Pattern pattern = Pattern.compile("/(.*?)(/[^ch]|$)");

        for (String s : inchisCompletas) {
            Matcher matcher = pattern.matcher(s);
//            System.out.println(s);
            if (s.equals("No se puede buscar al padre") | s.equals("No hay padre")) {
                inchisMain.add(s);
//                System.out.println(s);
                continue;
            }
            if (matcher.find()) {
                String extractedString = matcher.group(1);
                inchisMain.add(extractedString);
//                System.out.println(extractedString);
            }
        }
        return inchisMain;
    }

//    public static void main(String[] args) {
//        String inputString = "InChI=1S/C17H16O2/c18-17(12-11-15-7-3-1-4-8-15)19-14-13-16-9-5-2-6-10-16/h1-12H";
//
////        Pattern pattern = Pattern.compile("/(.*?)(?:(/b|/t|/m|/s))");
////"/(.*?)(?:/[^ch])"
//        Pattern pattern = Pattern.compile("/(.*?)(/[^ch]|$)");
//
//        Matcher matcher = pattern.matcher(inputString);
//        if (matcher.find()) {
//            String extractedString = matcher.group(1);
//            System.out.println(extractedString);
//        }
}
