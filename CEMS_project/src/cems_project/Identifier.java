/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cems_project;

import java.util.Objects;

/**
 *
 * @author maria
 */
public class Identifier {

    private final String inchi;
    private final String inchi_key;
    private final String smiles;
    private final Integer pc_id;
    private final String hmdb_id;
    private final String cembio_id;

    public Identifier(String inchi, String inchi_key, String smiles, Integer pc_id, String hmdb_id, String cembio_id) {
        this.inchi = inchi;
        this.inchi_key = inchi_key;
        this.smiles = smiles;
        this.pc_id = pc_id;
        this.hmdb_id = hmdb_id;
        this.cembio_id = cembio_id;
    }

    public Identifier(String inchi, String inchi_key, String smiles) {
        this(inchi, inchi_key, smiles, null, null, null);
    }

    public Identifier(String inchi) {
        this(inchi, null, null, null, null, null);
    }

    public Identifier(String inchi, Integer pc_id) {
        this(inchi, null, null, pc_id, null, null);
    }

    public Identifier(String inchi, String inchi_key, String smiles, Integer pc_id) {
        this(inchi, inchi_key, smiles, pc_id, null, null);
    }

    public Identifier(String inchi, Integer pc_id, String hmdb_id, String cembio_id) {
        this(inchi, null, null, pc_id, hmdb_id, cembio_id);
    }

    public Integer getPc_id() {
        return pc_id;
    }

    public String getInchi() {
        return inchi;
    }

    public String getInchi_key() {
        return inchi_key;
    }

    public String getSmiles() {
        return smiles;
    }

    public String getHmdb_id() {
        return hmdb_id;
    }

    public String getCembio_id() {
        return cembio_id;
    }

    @Override
    public String toString() {
        return "\n\t\tinchi=" + inchi + "\n\t\tinchi_key=" + inchi_key + "\n\t\tsmiles=" + smiles + "\n\t\tpc_id=" + pc_id + "\n\t\thmdb_id=" + hmdb_id + "\n\t\tcembio_id=" + cembio_id + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.inchi_key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Identifier other = (Identifier) obj;
        if (!Objects.equals(this.inchi_key, other.inchi_key)) {
            return false;
        }
        return true;
    }

}
