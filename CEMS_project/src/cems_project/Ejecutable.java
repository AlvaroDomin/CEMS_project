/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

import java.util.List;

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
    }

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