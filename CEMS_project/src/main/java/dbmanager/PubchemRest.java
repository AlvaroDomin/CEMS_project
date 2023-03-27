package dbmanager;

import static constants.Constants.*;
import java.io.IOException;

import cems_project.Identifier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;

public class PubchemRest {

    public static void main(String[] args) {
        Identifier id1 = null;
        try {
            id1 = getIdentifierFromName("carnitine");
            System.out.println(id1);
        } catch (IOException e) {
            System.out.println(e);
        }

    }


    /*
    public static Compound getCompoundFromName(String name) throws IOException {
        name = name.replace(" ", "%20");
        name = name.replace(" ", "%20");
        String uriString = PUBCHEM_ENDPOINT_COMPOUND_NAME + name + "/property/MonoisotopicMass,inchi,InChIKey,CanonicalSMILES,MolecularFormula,XLogP/JSON";

        Request request = Request.get(uriString);
        request.addHeader("Connection", "keep-alive");

        Response response = request.execute();
        Content jsonResponse = response.returnContent();
        String jsonResponseString = jsonResponse.asString();

        JsonObject jsonrepsonse = new JsonParser().parse(jsonResponseString).getAsJsonObject();
        JsonObject properties = jsonrepsonse.get(("PropertyTable")).getAsJsonObject().get("Properties").getAsJsonArray().get(0).getAsJsonObject();
        Integer cid = properties.get("CID").getAsInt();
        String IUPACName = null;
        if (properties.has("IUPACName")) {
            IUPACName = properties.get("IUPACName").getAsString();
        }
        String molecularFormula = properties.get("MolecularFormula").getAsString();
        String inchi_key = properties.get("InChIKey").getAsString();
        String inchi = properties.get("InChI").getAsString();
        String smiles = properties.get("CanonicalSMILES").getAsString();
        Double logP = null;
        if (properties.has("XLogP")) {
            logP = properties.get("XLogP").getAsDouble();
        }
        Double mass = properties.get("MonoisotopicMass").getAsDouble();
        String casId = null;
        Integer compound_id = 0;
        Integer compound_status = 0;
        Integer compound_type = 0;

        Identifier identifiers = new Identifier(inchi, inchi_key, smiles);
        Compound compound = new Compound(compound_id, casId, molecularFormula, mass, compound_status, compound_type, logP, identifiers);

        return compound;
    }
*/

    public static Identifier getIdentifierFromName(String name) throws IOException {
        name = name.replace(" ", "%20");
        name = name.replace(" ", "%20");
        String uriString = PUBCHEM_ENDPOINT_COMPOUND_NAME + name + "/property/inchi,InChIKey,CanonicalSMILES/JSON";

        Request request = Request.get(uriString);
        request.addHeader("Connection", "keep-alive");

        Response response = request.execute();
        Content jsonResponse = response.returnContent();
        String jsonResponseString = jsonResponse.asString();

        JsonObject jsonrepsonse = new JsonParser().parse(jsonResponseString).getAsJsonObject();
        JsonObject properties = jsonrepsonse.get(("PropertyTable")).getAsJsonObject().get("Properties").getAsJsonArray().get(0).getAsJsonObject();
        Integer cid = properties.get("CID").getAsInt();
        String inchi_key = properties.get("InChIKey").getAsString();
        String inchi = properties.get("InChI").getAsString();
        String smiles = properties.get("CanonicalSMILES").getAsString();

        Identifier identifier = new Identifier(inchi, inchi_key, smiles);

        return identifier;
    }

}
