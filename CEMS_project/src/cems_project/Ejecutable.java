/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

import dbmanager.PubchemRest;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author maria
 */
public class Ejecutable {

    public static void CembioList() {
        //imprimimos la lista de metabolitos que tenemos
        List<Compound> compsLeidos = Fichero.leerCEMBIOLIST();
        List<Compound> compsPC;
        List<Compound> comps = new LinkedList<Compound>();

//        for (Compound c : compsLeidos) {
//            System.out.println(c);
//        }
        // actualizamos los compuestos para tener su inchi y su pc_id
        compsPC = PubchemRest.getCompsFromNames(compsLeidos);

        //estos compounds tienen el nombre de la IUPAC (de sus parámetros solo queremos el pc_id y la inchi)
        for (int i = 0; i < compsPC.size(); i++) { //es la misma size que la de compsLeidos
            Identifier identifier = new Identifier(compsPC.get(i).getIdentifiers().getInchi(), compsPC.get(i).getIdentifiers().getInchi_key(), compsPC.get(i).getIdentifiers().getSmiles(), compsPC.get(i).getIdentifiers().getPc_id(), null, compsLeidos.get(i).getIdentifiers().getCembio_id());
            Compound c = new Compound(compsLeidos.get(i).getCompound_id(), compsLeidos.get(i).getName(), compsLeidos.get(i).getCasId(), identifier);
            comps.add(c);
//            System.out.println(c);
        }
        // obtenemos las inchis de los padres
        List<Compound> padres = PubchemRest.getParentsFromChildComps(compsPC);
//        for (Compound c : padres) {
//            System.out.println(c);
//        }

        //escribimos todo en el excel
        Fichero.escribirCEMBIOLIST(padres, comps);
    }

    public static void ComercialList() {
        List<Compound> compsLeidos = Fichero.leerCOMERCIALLIST();
//        for (Compound c : compsLeidos) {
//            System.out.println(c);
//        }

        List<Compound> compsPC;

        // actualizamos los compuestos para tener su inchi y su pc_id
        System.out.println("Buscar su info");
        compsPC = PubchemRest.getCompoundsFromInchis(compsLeidos);
        //estos compounds tienen el nombre de la IUPAC (de sus parámetros solo queremos el pc_id y la inchi)
//        for (Compound c : compsPC) {
//            System.out.println(c);
//        }

        // obtenemos las inchis de los padres
        System.out.println("Buscar padres");
        List<Compound> padres = PubchemRest.getParentsFromChildComps(compsPC);
//        for (Compound c : padres) {
//            System.out.println(c);
//        }
//
//        //escribimos todo en el excel
        System.out.println("Escribir excel");
        Fichero.escribirCOMERCIALLIST(padres, compsPC);
    }
//

    // MAIN PARA RELLENAR EXCELS
    public static void main(String[] args) {
//        CembioList();
//        ComercialList();
    }

    //MAIN PARA COMAPARAR LA COMERCIAL Y LA CEMBIO INDEPENDIENTES (No he hecho un método)
//    public static void main(String[] args) {
//
//        List<String> inchis = new LinkedList();
//        List<String> repetidos = new ArrayList<>();
//        System.out.println(repetidos.size());
//        List<String> inchisMain;
//        System.out.println("Leer fichero");
////        List<Compound> comps = Fichero.leerInchisExcelCembio();
//        List<Compound> comps = Fichero.leerInchisExcelComercial();
//        System.out.println(comps);
//        for (Compound c : comps) {
//            inchis.add(c.getIdentifiers().getInchi());
//        }
////        System.out.println(inchis);
//
//        inchisMain = Regex.getMainPart(inchis);
//        System.out.println(inchisMain);
//
//        //------------------------------------------------------
//        //rellenamos la lista de repetidos con 0:
//        for (String cop : inchisMain) {
//            repetidos.add("0");
//        }
//
//        List<String> cambiantes = inchisMain;
//        int counter;
//        for (counter = 0; counter < comps.size(); counter++) {
//            String inchiComp = inchisMain.get(0);
////            System.out.println("A comparar");
////            System.out.println(inchiComp);
//            //elimino el primer elemento de la lista porque es el que queremos comparar
//            cambiantes.remove(0);
////            System.out.println("lista modificada:");
////            System.out.println(cambiantes);
//            if (repetidos.get(counter).equals("0")) {   //si todavia no hemos comparado esa string hacemos todo esto, si no, pasamos al siguiente
//                if (inchiComp.equals("No se puede buscar al padre") | inchiComp.equals("No hay padre")) {
////                    System.out.println("no padre");
//                    //nada que comparar; paso al siguiente
//                    repetidos.set(counter, "---");
////                    System.out.println("Lista repetidos");
////                    System.out.println(repetidos);
////                counter++;
//                    continue;
//                }
//                if (cambiantes.contains(inchiComp)) {
////                    System.out.println("Esta repe");
////                    System.out.println("Lo inserto en la posición " + counter);
//                    repetidos.set(counter, "Esta repetido");
////                    System.out.println(repetidos.size());
//                    int pos = 0;
//                    for (String i : cambiantes) {
//                        if (i.equals(inchiComp)) {
//
//                            String repe = "Igual que Id = " + comps.get(counter).getCompound_id();
//                            int a = pos + counter + 1;
////                            System.out.println("lo inserto en la pos " + a);
//                            repetidos.set(pos + counter + 1, repe);
////                            System.out.println("lista repetidos");
////                            System.out.println(repetidos);
//                        }
////                    repetidos.add(pos + 1, "-");
//                        pos++;
//                    }
//
//                } else {
//                    //no esta repetido
////                    System.out.println("no esta repe");
//                    repetidos.set(counter, "No esta repetido");
////                    System.out.println("Lista repetidos");
////                    System.out.println(repetidos);
//                }
//            }
////            System.out.println("Lista repetidos final");
//            int pos = 0;
//            for (String s : repetidos) {
////                System.out.println(s + "pos:" + pos);
//                pos++;
//            }
//        }
//        System.out.println(repetidos);
//
//        System.out.println("Escribir en fichero");
////        Fichero.escribirInchisCembio(repetidos);
//        Fichero.escribirInchisComercial(repetidos);
//    }
//    // MAIN PARA COMPARAR AMBAS LISTAS ENTRE ELLAS
//    public static void main(String[] args) {
//
//        List<String> inchisCembio = new LinkedList();
//        List<String> inchisComercial = new LinkedList();
//        List<String> repetidos = new ArrayList<>();
////        System.out.println(repetidos.size());
//        List<String> inchisMainCembio;
//        List<String> inchisMainComercial;
//
////        System.out.println("Leer fichero");
//        List<Compound> compsCembio = Fichero.leerInchisExcelCembio();
//        List<Compound> compsComercial = Fichero.leerInchisExcelComercial();
//
////        System.out.println(compsCembio);
////        System.out.println(compsComercial);
//        for (Compound c : compsCembio) {
//            inchisCembio.add(c.getIdentifiers().getInchi());
//        }
//        for (Compound c : compsComercial) {
//            inchisComercial.add(c.getIdentifiers().getInchi());
//        }
////        System.out.println(inchis);
//
//        inchisMainCembio = Regex.getMainPart(inchisCembio);
////        System.out.println(inchisMainCembio);
//        inchisMainComercial = Regex.getMainPart(inchisComercial);
////        System.out.println(inchisMainComercial);
//
//        //------------------------------------------------------
//        //rellenamos la lista de repetidos con 0:
//        for (String cop : inchisMainCembio) {
//            repetidos.add("0");
//        }
//
////        List<String> cambiantes = inchisMain;
//        int counter;
//        for (counter = 0; counter < compsCembio.size(); counter++) {
//            String inchiComp = inchisMainCembio.get(counter);
////            System.out.println("A comparar");
////            System.out.println(inchiComp);
//
//            if (inchiComp.equals("No se puede buscar al padre") | inchiComp.equals("No hay padre")) {
////                System.out.println("no padre");
//                repetidos.set(counter, "---");
////                System.out.println("Lista repetidos");
////                System.out.println(repetidos);
//                continue;
//            }
//            if (inchisMainComercial.contains(inchiComp)) {
////                System.out.println("Repetido");
//                int pos = inchisMainComercial.indexOf(inchiComp);
////                System.out.println(pos);
//                String repe = "Igual que Id = " + compsComercial.get(pos).getCompound_id();
//                repetidos.set(counter, repe);
////                System.out.println("Lista repetidos");
////                System.out.println(repetidos);
//
//            } else {
//
//                repetidos.set(counter, "No esta repetido");
////                System.out.println("Lista repetidos");
////                System.out.println(repetidos);
//            }
//        }
////        System.out.println("Repetidos final");
////        System.out.println(repetidos);
//
////        System.out.println("Escribir en fichero");
//        Fichero.escribirInchisCompararAmbos(repetidos);
//
//    }
//    public static void main(String[] args) {
//
//        String name = "18:1 Lyso PC 1-oleoyl-2-Hydroxy-sn-glycero-3-phosphocholine                                        ";
//        while (name.contains(" ")) {
//            name = name.replace(" ", "%20");
//        }
//        System.out.println(name);
//    }
//    public static void main(String[] args) {
//        //imprimimos la lista de metabolitos que tenemos
//        List<Metabolito> metabolitos = Fichero.leerFichero();
//        for (Metabolito m : metabolitos) {
//            System.out.println(m);
//        }
//
//        //conectamos con la database
//        DBManager db = new DBManager();
//        String filename = "resources/connectionData.pass";
//        try {
//            Gson gson = new Gson();
//            String readJSONStr = readStringFromFile(filename);
//            JsonElement element = gson.fromJson(readJSONStr, JsonElement.class);
//            JsonObject jsonObj = element.getAsJsonObject();
//            String dbName = jsonObj.get("db_name").getAsString();
//            String dbUser = jsonObj.get("db_user").getAsString();
//            String dbPassword = jsonObj.get("db_password").getAsString();
//
//            db.connectToDB("jdbc:mysql://localhost/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", dbUser, dbPassword);
//
//            //insertamos los metabolitos leidos
//            for (Metabolito m : metabolitos) {
//                db.insertMetabolite(m);
//            }
//
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ioe) {
//            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ioe);
//        }
//    }
}
