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

    static final String SELECTFRAGMENTSFROMID = "Select * from fragments where id_met = ?";

    static final String INSERTINTOMETABOLITES = "Insert into metabolites (COMPOUND_NAME, FORMULA, MONOISOTOPIC_MASS, M_Z, mt_COMPND, MT_METS, RMT_METS, MT_MES, RMT_MES) VALUES(?,?,?,?,?,?,?,?,?)";
    static final String INSERTFRAGMENTSFROMID = "Insert into fragments (ID_MET, M_Z, INTENSITY) VALUES (?, ?, ?)";

}
