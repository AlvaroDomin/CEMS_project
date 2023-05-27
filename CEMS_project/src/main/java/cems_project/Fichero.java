/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

import constants.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author maria
 */
public class Fichero {

    public static List<Metabolito> leerFichero() {  //utilizar interfaces: devolver List, no LinkedList (para no centrarnos solo en esta implementación)

        // TODO code application logic here
        File excelFile = new File(Constants.CEFilePath);
        List<Metabolito> metabolitos = new LinkedList<Metabolito>();

        // we create an XSSF Workbook object for our XLSX Excel File
        //It is a class that is used to represent both high and low level Excel file formats.
        try (FileInputStream fis = new FileInputStream(excelFile); // we create an XSSF Workbook object for our XLSX Excel File
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            // we get first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);   //Get the HSSFSheet object at the given index
            int totalRows = sheet.getPhysicalNumberOfRows();
            System.out.println("READING " + (totalRows - 1) + " compounds\n");

            Iterator<Row> rowIt = sheet.iterator();
            // skip the header
            rowIt.next();   //lee la linea en la que pone "Patrones"
            //rowIt.next(); //se salta la linea en la que pone COMPOUND, FORMULA...

            //creamos todas las variables que vamos a ir necesitando
            String name;
            String INCHI;
            String RefHMDB;
            Integer RefPubChem;
            String formula;
            Double M;
            Double m_z;
            Double MTcompnd;    //datos no primitivos para poder asignar null
            Double MTmets;
            Double RMTmets;
            Double MTmes;
            Double RMTmes;
            Double eff_mobility;

            while (rowIt.hasNext()) {   //este bucle va avanzando linea a linea

                Row row = rowIt.next();

                //iterate on cells for the current row
                Iterator<Cell> cellIterator = row.cellIterator();   //con este iterador nos vamos moviendo por las columnas dentro de una misma fila

                //vamos leyendo cada columna y guardando los valores en una variable para luego crear los metabolitos
                name = cellIterator.next().getStringCellValue();

                //para algunas de las siguientes propiedades, hay algunas que están sin rellenar, en tal caso, se asigna un valor imposible
                try {
                    INCHI = cellIterator.next().getStringCellValue();
                } catch (IllegalStateException ise) {
                    INCHI = null;
                }
                try {
                    RefHMDB = cellIterator.next().getStringCellValue();
                    if (RefHMDB == "") {
                        RefHMDB = null;
                    }
                } catch (IllegalStateException ise) {
                    RefHMDB = null;
                }
                try {
                    RefPubChem = (int) cellIterator.next().getNumericCellValue();
                    if (RefPubChem == 0) {
                        RefPubChem = null;
                    }
                } catch (IllegalStateException ise) {
                    RefPubChem = null;
                }
                try {
                    formula = cellIterator.next().getStringCellValue();
                } catch (IllegalStateException ise) {
                    formula = null;
                }

                M = cellIterator.next().getNumericCellValue();
                m_z = cellIterator.next().getNumericCellValue();

                try {
                    MTcompnd = cellIterator.next().getNumericCellValue();   //este método devuelve un double, por lo tanto no hay que hacer ningín cast
                } catch (IllegalStateException ise) {
                    MTcompnd = null;
                }

                try {
                    MTmets = cellIterator.next().getNumericCellValue();
                } catch (IllegalStateException ise) {
                    MTmets = null;
                }

                try {
                    RMTmets = cellIterator.next().getNumericCellValue();
                } catch (IllegalStateException ise) {
                    RMTmets = null;
                }

                try {
                    MTmes = cellIterator.next().getNumericCellValue();
                } catch (IllegalStateException ise) {
                    MTmes = null;
                }

                try {
                    RMTmes = cellIterator.next().getNumericCellValue();
                } catch (IllegalStateException ise) {
                    RMTmes = null;
                }

                try {
                    eff_mobility = cellIterator.next().getNumericCellValue();   //este método devuelve un double, por lo tanto no hay que hacer ningín cast
                } catch (IllegalStateException ise) {
                    eff_mobility = null;
                }
                //ahora leemos los fragmentos como String y luego con Split lo separamos en cada Double correspondiente
                String celdaLeida;
                String[] fragmentsLeidos;   //el método split devuelve un array de Strings
                List<Fragment> fragments = new LinkedList<Fragment>();
                celdaLeida = cellIterator.next().getStringCellValue();

                if (celdaLeida.equalsIgnoreCase("") || celdaLeida.equalsIgnoreCase("XXX")) {    //si lee esto es porque no hay fragments
                    fragments = null;
                    //también podemos llamar al constructor que no recibe fragmentos
                    //Metabolito m = new Metabolito(name, formula, M, m_z, MTcompnd, MTmets, RMTmets, MTmes, RMTmes);

                } else {    //en caso de que si que lea algo, se añaden a la lista

                    fragmentsLeidos = celdaLeida.split("[,;]");    //los fragmentos estan separados por comas
                    for (String s : fragmentsLeidos) {  //recorremos cada uno de los fragmentos (todavia en Strings)

                        try {       //si no se puede meter, es porque no solo hay numeros
                            fragments.add(new Fragment(Double.parseDouble(s)));   //transformamos el String en Double

                        } catch (NumberFormatException e) {    //si llegamos al catch es porque hay texto a parte de numeros
                            String[] sinProblemas = s.split("[(]");     //tenemos que quedarnos con la parte que no contiene texto y que viene entre ()
                            fragments.add(new Fragment(Double.parseDouble(sinProblemas[0])));
                        }
                    }

                }

                //vamos creando cada metabolito y lo añadimos a la lista
                Metabolito m = new Metabolito(name, INCHI, RefHMDB, RefPubChem, formula, M, m_z, MTcompnd, MTmets, RMTmets, MTmes, RMTmes, fragments, eff_mobility);
                metabolitos.add(m);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return metabolitos;
    }

    //--------------------------------------------------------------------------------------------------------------------------------------
    public static List<Compound> leerCEMBIOLIST() {
        File excelFile = new File(Constants.CEMBIOLIST);
        List<Compound> comps = new LinkedList<Compound>();

        try (FileInputStream fis = new FileInputStream(excelFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            // we get first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);   //Get the HSSFSheet object at the given index
            int totalRows = sheet.getPhysicalNumberOfRows();
            System.out.println("READING " + (totalRows - 1) + " compounds\n");
//            System.out.println(sheet.getLastRowNum());

            Iterator<Row> rowIt = sheet.iterator();
            // skip the header (la que tiene los nombres de cada columna)
            rowIt.next();

            //creamos todas las variables que vamos a ir necesitando
            Integer id;
            String name;
            String cembio_id;
            String cas_id;

            while (rowIt.hasNext()) {   //este bucle va avanzando linea a linea

                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //con este iterador nos vamos moviendo por las columnas dentro de una misma fila
//                try {
                //vamos leyendo cada columna y guardando los valores en una variable
                id = (int) cellIterator.next().getNumericCellValue();
                name = cellIterator.next().getStringCellValue();
                cellIterator.next();
                cellIterator.next();
                cembio_id = cellIterator.next().getStringCellValue();
                cas_id = cellIterator.next().getStringCellValue();

                Identifier i = new Identifier(null, null, null, null, null, cembio_id);
                Compound c = new Compound(id, name, cas_id, i);
                comps.add(c);
//                } catch (IllegalStateException e) {
//                    System.out.println("Error in cell: " + cellIterator);
//                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comps;
    }

    public static void escribirCEMBIOLIST(List<Compound> padres, List<Compound> comps) {
//        System.out.println(padres);
//        System.out.println(comps);
        // Crear un nuevo libro de trabajo de Excel
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja de cálculo en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("CEMBIO");

        // Escribir los datos en la hoja de cálculo
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Cell cellNombre = row.createCell(0);
        cellNombre.setCellValue("Name");
        Cell cellCembio_id = row.createCell(1);
        cellCembio_id.setCellValue("Cembio_id");
        Cell cellId = row.createCell(2);
        cellId.setCellValue("CasId");
        Cell cellInchiH = row.createCell(3);
        cellInchiH.setCellValue("Inchi");
        Cell cellPc_id = row.createCell(4);
        cellPc_id.setCellValue("Pc_id");
        Cell cellPc_id_P = row.createCell(5);
        cellPc_id_P.setCellValue("Pc_id_Padre");
        Cell cellInchi_P = row.createCell(6);
        cellInchi_P.setCellValue("Inchi_Padre");
        Cell cell_Id = row.createCell(7);
        cell_Id.setCellValue("Id");

        for (int i = 0; i < comps.size(); i++) {
            row = sheet.createRow(rowNum++);
            cellNombre = row.createCell(0);
            cellNombre.setCellValue(comps.get(i).getName());
            cellCembio_id = row.createCell(1);
            cellCembio_id.setCellValue(comps.get(i).getIdentifiers().getCembio_id());
            cellId = row.createCell(2);
            cellId.setCellValue(comps.get(i).getCasId());
            cellInchiH = row.createCell(3);
            cellInchiH.setCellValue(comps.get(i).getIdentifiers().getInchi());
            cellPc_id = row.createCell(4);
            try {
                cellPc_id.setCellValue(comps.get(i).getIdentifiers().getPc_id());
            } catch (NullPointerException e) {
                cellPc_id.setCellValue("---");
            }
            cellPc_id_P = row.createCell(5);
            try {
                cellPc_id_P.setCellValue(padres.get(i).getIdentifiers().getPc_id());
            } catch (NullPointerException e) {
                cellPc_id_P.setCellValue("---");
            }
            cellInchi_P = row.createCell(6);
            cellInchi_P.setCellValue(padres.get(i).getIdentifiers().getInchi());
            cell_Id = row.createCell(7);
            cell_Id.setCellValue(comps.get(i).getCompound_id());

        }

        // Guardar el libro de trabajo de Excel en un archivo
        try (FileOutputStream outputStream = new FileOutputStream(Constants.RUTA_GUARDAR + "InchisCEMBIO.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el Excel");
            e.printStackTrace();
        }
    }

    public static List<Compound> leerCOMERCIALLIST() {
        File excelFile = new File(Constants.COMERCIALLIST);
        List<Compound> comps = new LinkedList<Compound>();

        try (FileInputStream fis = new FileInputStream(excelFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            // we get first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);   //Get the HSSFSheet object at the given index
            int totalRows = sheet.getPhysicalNumberOfRows();
            System.out.println("READING " + (totalRows - 1) + " compounds\n");
//            System.out.println(sheet.getLastRowNum());

            Iterator<Row> rowIt = sheet.iterator();
            // skip the header (la que tiene los nombres de cada columna)
            rowIt.next();

            //creamos todas las variables que vamos a ir necesitando
            Integer id;
            String hmdb_pc;
            String name;
            String cas_id;
            String inchi;

            while (rowIt.hasNext()) {   //este bucle va avanzando linea a linea

                Row row = rowIt.next();

                //iterate on cells for the current row
                Iterator<Cell> cellIterator = row.cellIterator();   //con este iterador nos vamos moviendo por las columnas dentro de una misma fila

                //vamos leyendo cada columna y guardando los valores en una variable para luego crear los metabolitos
                id = (int) cellIterator.next().getNumericCellValue();
                hmdb_pc = cellIterator.next().getStringCellValue();
                name = cellIterator.next().getStringCellValue();
                cas_id = cellIterator.next().getStringCellValue();
                inchi = cellIterator.next().getStringCellValue();

                String hmdb = null;
                Integer pc = null;

                if (hmdb_pc.startsWith("HMDB")) {
                    hmdb = hmdb_pc;
                } else {
                    if (hmdb_pc.startsWith("PUBCHEMCID")) {
                        pc = parseInt(hmdb_pc.replace("PUBCHEMCID", ""));
                    } else {
                        pc = parseInt(hmdb_pc.replace("PubChemCID", ""));
                    }
                }

                //they do not have cembio_id
                Identifier i = new Identifier(inchi, pc, hmdb, null);
                Compound c = new Compound(id, name, cas_id, i);
                comps.add(c);

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comps;
    }

    public static void escribirCOMERCIALLIST(List<Compound> padres, List<Compound> comps) {
        // Crear un nuevo libro de trabajo de Excel
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja de cálculo en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("COMERCIAL");

        // Escribir los datos en la hoja de cálculo
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Cell cellNombre = row.createCell(0);
        cellNombre.setCellValue("Name");
        Cell cellHMDB_id = row.createCell(1);
        cellHMDB_id.setCellValue("HMDB Id");
        Cell cellId = row.createCell(2);
        cellId.setCellValue("CasId");
        Cell cellInchiH = row.createCell(3);
        cellInchiH.setCellValue("Inchi");
        Cell cellPc_id = row.createCell(4);
        cellPc_id.setCellValue("Pc_id");
        Cell cellPc_id_P = row.createCell(5);
        cellPc_id_P.setCellValue("Pc_id_Padre");
        Cell cellInchi_P = row.createCell(6);
        cellInchi_P.setCellValue("Inchi_Padre");

        for (int i = 0; i < comps.size(); i++) {
            row = sheet.createRow(rowNum++);
            cellNombre = row.createCell(0);
            cellNombre.setCellValue(comps.get(i).getName());
            cellHMDB_id = row.createCell(1);
            if (comps.get(i).getIdentifiers().getHmdb_id() == null) {
                cellHMDB_id.setCellValue("---");
            } else {
                cellHMDB_id.setCellValue(comps.get(i).getIdentifiers().getHmdb_id());
            }
            cellId = row.createCell(2);
            cellId.setCellValue(comps.get(i).getCasId());
            cellInchiH = row.createCell(3);
            cellInchiH.setCellValue(comps.get(i).getIdentifiers().getInchi());

            cellPc_id = row.createCell(4);
            try {
                cellPc_id.setCellValue(comps.get(i).getIdentifiers().getPc_id());
            } catch (NullPointerException e) {
                cellPc_id.setCellValue("---");
            }
            cellPc_id_P = row.createCell(5);
            try {
                cellPc_id_P.setCellValue(padres.get(i).getIdentifiers().getPc_id());
            } catch (NullPointerException e) {
                cellPc_id_P.setCellValue("---");
            }
            cellInchi_P = row.createCell(6);
            cellInchi_P.setCellValue(padres.get(i).getIdentifiers().getInchi());

        }

        // Guardar el libro de trabajo de Excel en un archivo
        try (FileOutputStream outputStream = new FileOutputStream(Constants.RUTA_GUARDAR + "InchisCOM.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el Excel");
            e.printStackTrace();
        }
    }

    public static List<Compound> leerInchisExcelCembio() {
        File excelFile = new File(Constants.INCHISCEMBIO);
        List<Compound> comps = new LinkedList<Compound>();

        try (FileInputStream fis = new FileInputStream(excelFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            // we get first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);   //Get the HSSFSheet object at the given index
            int totalRows = sheet.getPhysicalNumberOfRows();
            System.out.println("READING " + (totalRows - 1) + " compounds\n");
//            System.out.println(sheet.getLastRowNum());

            Iterator<Row> rowIt = sheet.iterator();
            // skip the header (la que tiene los nombres de cada columna)
            rowIt.next();

            //creamos todas las variables que vamos a ir necesitando
            Integer id;
            String inchiPadre;

            while (rowIt.hasNext()) {   //este bucle va avanzando linea a linea

                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //con este iterador nos vamos moviendo por las columnas dentro de una misma fila
                //vamos leyendo cada columna y guardando los valores en una variable
                id = (int) cellIterator.next().getNumericCellValue();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                inchiPadre = cellIterator.next().getStringCellValue();

                Identifier i = new Identifier(inchiPadre);
                Compound c = new Compound(id, i);
                comps.add(c);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comps;
    }

    public static void escribirInchisCembio(List<String> repetidos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja de cálculo en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("CEMBIO");

        // Escribir los datos en la hoja de cálculo
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Cell cellInchiRep = row.createCell(0);
        cellInchiRep.setCellValue("RepetidoEstaLista");

        for (int i = 0; i < repetidos.size(); i++) {
            row = sheet.createRow(rowNum++);
            cellInchiRep = row.createCell(0);
            cellInchiRep.setCellValue(repetidos.get(i));

        }

        // Guardar el libro de trabajo de Excel en un archivo
        try (FileOutputStream outputStream = new FileOutputStream(Constants.RUTA_GUARDAR + "ComparadorInchisCembio.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el Excel");
            e.printStackTrace();
        }
    }

    public static List<Compound> leerInchisExcelComercial() {
        File excelFile = new File(Constants.INCHISCOMERCIAL);
        List<Compound> comps = new LinkedList<Compound>();

        try (FileInputStream fis = new FileInputStream(excelFile);
                XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            // we get first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);   //Get the HSSFSheet object at the given index
            int totalRows = sheet.getPhysicalNumberOfRows();
            System.out.println("READING " + (totalRows - 1) + " compounds\n");
//            System.out.println(sheet.getLastRowNum());

            Iterator<Row> rowIt = sheet.iterator();
            // skip the header (la que tiene los nombres de cada columna)
            rowIt.next();

            //creamos todas las variables que vamos a ir necesitando
            Integer id;
            String inchiPadre;

            while (rowIt.hasNext()) {   //este bucle va avanzando linea a linea

                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();   //con este iterador nos vamos moviendo por las columnas dentro de una misma fila
                //vamos leyendo cada columna y guardando los valores en una variable
                id = (int) cellIterator.next().getNumericCellValue();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                cellIterator.next();
                inchiPadre = cellIterator.next().getStringCellValue();

                Identifier i = new Identifier(inchiPadre);
                Compound c = new Compound(id, i);
                comps.add(c);

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Fichero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return comps;
    }

    public static void escribirInchisComercial(List<String> repetidos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja de cálculo en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("COMERCIAL");

        // Escribir los datos en la hoja de cálculo
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Cell cellInchiRep = row.createCell(0);
        cellInchiRep.setCellValue("RepetidoEstaLista");

        for (int i = 0; i < repetidos.size(); i++) {
            row = sheet.createRow(rowNum++);
            cellInchiRep = row.createCell(0);
            cellInchiRep.setCellValue(repetidos.get(i));

        }

        // Guardar el libro de trabajo de Excel en un archivo
        try (FileOutputStream outputStream = new FileOutputStream(Constants.RUTA_GUARDAR + "ComparadorInchisComercial.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el Excel");
            e.printStackTrace();
        }
    }

    public static void escribirInchisCompararAmbos(List<String> repetidos) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Crear una hoja de cálculo en el libro de trabajo
        XSSFSheet sheet = workbook.createSheet("CEMBIO");

        // Escribir los datos en la hoja de cálculo
        int rowNum = 0;
        Row row = sheet.createRow(rowNum++);

        Cell cellInchiRep = row.createCell(0);
        cellInchiRep.setCellValue("RepetidoConListaComercial");

        for (int i = 0; i < repetidos.size(); i++) {
            row = sheet.createRow(rowNum++);
            cellInchiRep = row.createCell(0);
            cellInchiRep.setCellValue(repetidos.get(i));

        }

        // Guardar el libro de trabajo de Excel en un archivo
        try (FileOutputStream outputStream = new FileOutputStream(Constants.RUTA_GUARDAR + "ComparadorInchisCembio_Comercial.xlsx")) {
            workbook.write(outputStream);
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el Excel");
            e.printStackTrace();
        }
    }

}
