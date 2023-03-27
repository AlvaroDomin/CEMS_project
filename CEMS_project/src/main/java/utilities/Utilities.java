package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utilities {

    public static void main(String[] args) {
        String inchi="inchi=InChI=1S/C7H15NO3/c1-8(2,3)5-6(9)4-7(10)11/h6,9H,4-5H2,1-3H3";
        String formulaFromInchi=getFormulaFromInChI(inchi);
        System.out.println(formulaFromInchi);
    }
    /**
     * return the formula generated from InChI structure
     *
     *
     * @param inChI
     * @return the formula contained in the InChI
     */
    public static String getFormulaFromInChI(String inChI) {

        // If the inChI is empty
        if (inChI.equals("") || inChI.equals("NULL") || inChI.equals("null")) {
            return "";
        }
        String formula;
        String formulaStart = "[/]";
        String formulaEnd = "[/]";
        formula = Utilities.searchFirstOcurrence(inChI, formulaStart + "(.*?)" + formulaEnd);
        // Quit the first / and the last /
        if (!formula.equals("")) {
            formula = formula.substring(1, formula.length() - 1);
        }

        return formula;
    }

    /**
     * Function searchFirstOcurrence
     *
     * @param content String of content
     * @param pattern pattern to search
     * @return a String with content which reaches the pattern
     */
    public static String searchFirstOcurrence(String content, String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);

        Matcher m = p.matcher(content);

        String respuesta = "";
        if (m.find()) {
            respuesta = m.group();
        }

        return respuesta;
    }


}
