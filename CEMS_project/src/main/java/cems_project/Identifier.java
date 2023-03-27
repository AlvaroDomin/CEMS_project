package cems_project;


import java.util.Objects;

/**
 * @author Alberto Gil-de-la-Fuente
 */
public record Identifier(String inchi, String inchi_key, String smiles) {

    public static void main(String[] args) {
        Identifier id1 = new Identifier("", "", "");
        System.out.println(id1);
    }

    @Override
    public String toString() {
        return "Identifier{" + "inchi=" + inchi + ", inchi_key=" + inchi_key + ", smiles=" + smiles + '}';
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
