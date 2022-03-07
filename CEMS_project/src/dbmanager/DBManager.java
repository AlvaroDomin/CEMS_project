/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import cems_project.Fragment;
import cems_project.Metabolito;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static utilities.fileIO.readStringFromFile;

/**
 *
 * @author alberto.gildelafuent
 */
public class DBManager {

    protected Connection connection;
    protected Statement statement;

    /**
     * Method to connect to the database
     *
     * @param bd JDBC String to coonect -> Example
     * "jdbc:mysql://localhost/<DATABASE_NAME>/?useSSL=false&serverTimezone=UuseSSLTC
     * @param usuario username of the database -> Example -> Root
     * @param clave "password of the database" -> example -> password
     */
    public void connectToDB(String bd, String usuario, String clave) {
        try {
            // MySQL driver registered
            //DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());

            // get DatabaseConnection
            connection = DriverManager.getConnection(bd, usuario, clave);

            statement = this.connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * It executes the query and returns the first int returned by the query.
     *
     * @param query
     * @return the ID of the query or 0 if the result is null
     */
    public int getInt(String query) {
        int id = -1;
        // Be aware that the connection should be initialized (calling the method connectToDB)
        try {
            ResultSet rs = statement.executeQuery(query);   //es un iterador

            if (rs.next()) {
                id = rs.getInt(1);  //este uno es que accede a la primera columna de la query que yo le paso
            }
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    /**
     * TO DO
     *
     * @param mz
     * @param tolerance
     * @return
     */
    public int getIdsFromMZ(Double mz, Double tolerance) {

        return 0;
    }

    //habrá que hacer también el resto de gets en función de lo que necesitemos
    /**
     * It executes the query and returns the first String returned by the query
     *
     * @param query
     * @return the String of the query or ??? if the result is null
     */
    public String getString(String query) {
        String word = null;
        try {
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                word = rs.getString(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return word;
    }

    /**
     * Metodo para obtener un metabolito por JDBC. It should throw a exception
     * if the query is not from metabolites
     *
     * @param ResultSet rs containing the row of the current metabolite
     * @return
     */
    private Metabolito getMetabolito(ResultSet rs) {    //tan solo lee el metabolito de al fila que se le ha pasado como parametro
        try {
            int id = rs.getInt("id");
            //int id = rs.getInt(1);
            //System.out.println("ID: " + id);

            String Compd_name = rs.getString("Compound_name");        //MEJOR PORNERLO CON EL NOMBRE DE LAS COLUMNAS
            //String Compd_name = rs.getString(2);
            //System.out.println("Compound name: " + Compd_name);

            String formula = rs.getString("Formula");
            //System.out.println("Formula: " + formula);
            double m_mass = rs.getDouble("Monoisotopic_mass");
            //System.out.println("Monoisotopic Mass: " + m_mass);
            double m_z = rs.getDouble("M_z");
            //System.out.println("M_z: " + m_z);
            double mt_compnd = rs.getDouble("Mt_compnd");
            // System.out.println("MT_compnd: " + mt_compnd);
            double mt_mets = rs.getDouble("Mt_mets");
            //System.out.println("MT_mets: " + mt_mets);
            double rmt_mets = rs.getDouble("Rmt_mets");
            //System.out.println("RMT_mets: " + rmt_mets);
            double mt_mes = rs.getDouble("Mt_mes");
            //System.out.println("MT_mes: " + mt_mes);
            double rmt_mes = rs.getDouble("Rmt_mes");
            //System.out.println("RMT_mes: " + rmt_mes);

            // Select de los fragmentos. Creando un metodo getFragmentos, solo reciba el parámetro int ID
            List<Fragment> fragments = getFragments(id);
            Metabolito m1 = new Metabolito(Compd_name, formula, m_mass, m_z, mt_compnd, mt_mets, rmt_mets, mt_mes, rmt_mes, fragments);
            return m1;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * It executes the query and returns the list of Fragments returned by the
     * query.
     *
     * @param query
     * @return the list of fragments or null
     */
    private List<Fragment> getFragments(int id) {

        String query = ConstantQueries.SELECTFRAGMENTSFROMID;
        List<Fragment> fragments = new LinkedList<>();
        try {
            PreparedStatement statement2 = this.connection.prepareStatement(query);
            statement2.setInt(1, id);   //para evitar la sql injection
            try {
                ResultSet rs = statement2.executeQuery();
                while (rs.next()) {
                    double m_z = rs.getDouble("m_z");
                    double intensity = rs.getDouble("intensity");
                    fragments.add(new Fragment(m_z, intensity));
                    //System.out.println("frag: " + fragments);
                }
                rs.close();
                return fragments;
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * TO DO It executes the query and returns the list of Fragments returned by
     * the mz and its tolerance
     *
     * @param mz mz of the fragments to find
     * @param tolerance tolerance in ppm
     * @return the list of fragments or null
     */
    private List<Fragment> getFragmentsFromMZRange(float mz, float tolerance) {

        // hay que crear la query para introducir la mz y la tolerancia por parametros
        // delta es el calculo de las ppm del mz
        String query = "Select * from fragments where mz < mz+delta and mz > mz-delta";
        List<Fragment> fragments = new LinkedList<>();

        try {
            statement.execute(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                //fragments.add(new Fragment(rs.getDouble(3)));   //la columna que contiene el m_z es la tres
                //mejor separarlo
                double m_z = rs.getDouble("M_z");
                fragments.add(new Fragment(m_z));
                //System.out.println("frag: " + fragments);
            }
            rs.close();
            return fragments;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     *
     * @param query the SQL query to execute. It should contain the attributes:
     * @return list of metabolites
     */
    public List<Metabolito> getMetabolitos(String query) {
        List<Metabolito> metabs = new LinkedList<Metabolito>();
        try {
            statement.execute(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                //System.out.println("ROW: " + rs.getRow());
                Metabolito m1 = getMetabolito(rs);  //no podemos estar trabajando con dos result sets distintos
                metabs.add(m1);
                //System.out.println(metabs);

            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return metabs;
    }

    /**
     *
     * @param metabolito
     * @throws Exception
     */
    public void insertMetabolito(Metabolito metabolito) /*throws Exception*/ {

        // mediante una llamada al metodo insertMetabolito (devuelve el ID generado.
        // mediante otra llamada la insercion de los fragmentos.
        // PARA INSERTS SE UTILIZA executeUPDATE. Una vez ejecuto el insert con
        // parametros PREPAREDSTATEMENT para crearlo,
        // ps.setTIPO(POSICION,VALOR);
        // ps.executeQuery(); IMPORTANTE SIN PARAMETROS QUE ES UN PREPAREDSTATEMENT
        // ps.getGeneratedKeys();
        // SI SOLO HE HECHO UN INSERT, devolverá un único entero correspondiente
        // a la fila insertada
        String query = ConstantQueries.INSERTINTOMETABOLITES;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, metabolito.getCompound());
            ps.setString(2, metabolito.getFormula());
            ps.setDouble(3, metabolito.getM());
            ps.setDouble(4, metabolito.getM_Z());
            ps.setDouble(5, metabolito.getMT_compound());
            ps.setDouble(6, metabolito.getMT_Mets());
            ps.setDouble(7, metabolito.getRMT_Mets());
            ps.setDouble(8, metabolito.getMT_Mes());
            ps.setDouble(9, metabolito.getRMT_Mes());
            List<Fragment> fragments = metabolito.getFragments();
            //------------------
            try {
                ps.executeUpdate();                 //este es el sentence que insertea la info
                //System.out.println("insertado");

                //hallamos el id del metabolito que acabamos de introducir para mandarlo a insertFragmentos
                int id = 0;
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                        System.out.println("Last id: " + id);       //este id es el que se manda a el insert de los fragments
                    }
                    rs.close();
                    System.out.println("ahora se insertan los fragmentos del metabolito " + id);
                    insertFragments(id, fragments);

                } catch (SQLException ex) {
                    Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param id which is the one from the last metabolite inserted
     * @throws Exception
     */
    public void insertFragments(int id, List<Fragment> fragments)/*throws Exception*/ {

        String query = ConstantQueries.INSERTFRAGMENTSFROMID;
        try {
            PreparedStatement ps = this.connection.prepareStatement(query);
            ps.setInt(1, id);

            for (int i = 0; i < fragments.size(); i++) {
                ps.setDouble(2, fragments.get(i).getM_Z());
                ps.setDouble(3, fragments.get(i).getIntensity());
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * SHOULD NOT BE USED..!!! It does not use preparedStaments so it is
     * vulnerable to SQL Injection
     *
     * @param query
     * @return the ID of the query or 0 if the result is null
     */
    private int exampleQueryToGetTheLastGeneratedIdFromAnInsert(String query) {
        int id = 0;
        // Be aware that the connection should be initialized (calling the method connectToDB

        try {

            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet provRS = statement.getGeneratedKeys()) {
                if (provRS.next()) {
                    id = provRS.getInt(1);
                    System.out.println("Last id: " + id);
                }
                provRS.close();

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println("\n GENERATED KEY of " + actualizacion + " : " + id);
        return id;
    }

    public static void main(String[] args) {

        DBManager db = new DBManager();
        String filename = "resources/connectionData.pass";  //este file contiene los datos de acceso a la database (notación JSON: clave-valor)
        try {
            Gson gson = new Gson();
            String readJSONStr = readStringFromFile(filename);
            JsonElement element = gson.fromJson(readJSONStr, JsonElement.class
            );
            JsonObject jsonObj = element.getAsJsonObject();
            String dbName = jsonObj.get("db_name").getAsString();
            String dbUser = jsonObj.get("db_user").getAsString();
            String dbPassword = jsonObj.get("db_password").getAsString();

            // Here you can check the values obtained
            //System.out.println("DB_NAME: " + dbName + " DBUser: " + dbUser + " DBPassword: " + dbPassword);
            db.connectToDB("jdbc:mysql://localhost/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", dbUser, dbPassword);

            //GET INT
            //int id = db.getInt("Select 10");
            //System.out.println(id);
            //GET STRING
            //String word = db.getString("Select \"asd\"");
            //System.out.println(word);
            //GETMETABOLITO
            //Metabolito m1 = db.getMetabolito("Select * from metabolites where ID = 2");
            //Metabolito m1 = db.getMetabolito("Select * from metabolites");
            //System.out.println("Metabolito 1:\n" + m1);
            //GETFRAGMENTOS
            //List<Fragment> fragments = db.getFragments(2);
            //System.out.println("Fragments: \n" + fragments);
            //GETMETABOLITOS
            //List<Metabolito> metabs = db.getMetabolitos("select * from metabolites where id = 1");
            //List<Metabolito> metabs = db.getMetabolitos("select * from metabolites");
            //System.out.println("Estos son los metabolitos almacenados: \n" + metabs);
            //List<Fragment> fragments = db.getFragments(2);
            //System.out.println("Estos son los fragmentos almacenados: \n" + fragments);
            // SI NO INSERTA NADA, AUTO_GENERATED KEYS DEVUELVE 0
            //int id_updated = db.exampleQueryToGetTheLastGeneratedIdFromAnInsert("update prueba set f1 = 2 where id=2");
            //System.out.println(id_updated);
            //int id_inserted = db.exampleQueryToGetTheLastGeneratedIdFromAnInsert("insert into prueba (f1) values (1)");
            //System.out.println(id_inserted);
            //INSERT METABOLITES
            List<Fragment> fragments = new LinkedList();
            fragments.add(new Fragment(78.9594));
            fragments.add(new Fragment(96.9671));
            fragments.add(new Fragment(138.9802));
            //Metabolito metabolito = new Metabolito("Fructose 1,6 Biphosphate", "C6H14O12P2", 339.9960, 338.9887, 9.739, 27.135, 0.36, 20.187, 0.48, fragments);
            //db.insertMetabolito(metabolito);
            db.insertFragments(1, fragments);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ioe) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ioe);
        }
    }
}
