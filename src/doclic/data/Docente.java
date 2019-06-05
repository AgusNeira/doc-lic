 
package doclic.data;


import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Agustín Neira
 */
public class Docente extends CRUDData {
    
    public static long parseCuit(String cuit){
        return Long.parseLong(cuit.replace("-", ""));
    }
    
    @Override public Docente copy(){
        return new Docente(this.name.get(), this.cuit);
    }
    
    private final SimpleStringProperty name;
    private final long cuit;
    
    public Docente(String name, long cuit){
        this.name = new SimpleStringProperty(name);
        this.cuit = cuit;
    }
    
    public Docente(ResultSet rs) throws SQLException {
        this.name = new SimpleStringProperty(rs.getString("fullname"));
        this.cuit = rs.getLong("cuit");
    }
    
    public SimpleStringProperty nameProperty(){
        return this.name;
    }
    
    public SimpleStringProperty cuitProperty(){
        String str = String.valueOf(this.cuit);
        if(str.length() == 11){
            return new SimpleStringProperty(str.substring(0, 2) + "-" + str.substring(2, 10) + "-" + str.substring(10));
        }
        return new SimpleStringProperty("NULL");
    }
    
    public long getCUIT(){
        return this.cuit;
    }
    
    @Override
    public String toString(){
        return this.name.get();
    }
    
    
}
