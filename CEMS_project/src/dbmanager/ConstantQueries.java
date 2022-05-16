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

    //Nuevos:
    static final String INSERT_CE_EXP_PROP = "Insert into ce_experimental_properties(ce_exp_prop_id, buffer, temperature, ionization_mode, polarity) VALUES (?, 1, 20, 2, 2)";
    static final String INSERT_CE_EFF_MOB = "INSERT INTO ce_eff_mob(ce_compound_id, ce_exp_prop_id, eff_mobility) VALUES(?, ?, ?)";
    static final String INSERT_CE_EXP_PROP_META_METS = "INSERT INTO ce_experimental_properties_metadata(ce_eff_mob_id, experimental_mz, capillary_voltage, capillary_length, bge_compound_id, absolute_MT, relative_MT) VALUES (?, ?, 1000, 30, 180838, ?, ?)";
    static final String INSERT_CE_EXP_PROP_META_MES = "INSERT INTO ce_experimental_properties_metadata(ce_eff_mob_id, experimental_mz, capillary_voltage, capillary_length, bge_compound_id, absolute_MT, relative_MT) VALUES (?, ?, 1000, 30, 73414, ?, ?)";
    static final String INSERT_COMP_CE_PROD_ION = "INSERT INTO compound_ce_product_ion (ce_product_ion_mz, ce_product_ion_type, ce_eff_mob_id, compund_id_own) VALUES (?, 'fragment', ?, ?)";
    static final String INSERT_COMPOUNDS = "INSERT INTO compounds (compound_name, formula, mass) VALUES (?, ?, ?)";
    static final String INSERT_COMP_IDENT = "INSERT INTO compound_identifiers (inchi) VALUES (?)";
    static final String INSERT_COMP_HMDB = "INSERT INTO compounds_hmdb (hmdb_id, compound_id) VALUES (?, ?)";
    static final String INSERT_COMP_PC = "INSERT INTO compounds_pc (pc_id, compound_id) VALUES (?, ?)";

    public static final int CAPILLARY_VOLTAGE = 30;

}
