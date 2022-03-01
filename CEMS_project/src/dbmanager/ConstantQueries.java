/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

/**
 *
 * @author maria
 */
public class ConstantQueries {

    static final String SELECTMETABOLITES = "Select * from metabolites";
    static final String SELECTMETABOLITESFROMID = "Select * from metabolites where id = ?";

    static final String SELECTFRAGMENTSFROMID = "Select * from fragments where id = ?";

    static final String INSERTINTOMETABOLITES = "Insert into metabolites (CAMPOS...) VALUES(?,?,...,?)";
}
