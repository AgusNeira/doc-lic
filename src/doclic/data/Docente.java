
package doclic.data;


import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Agustín Neira
 */
public class Docente extends CRUDData {
    
    @Override public Docente copy(){
        return new Docente(this.id.get(), this.name.get(), this.cuit.get());
    }
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty cuit;
    
    public Docente(int id, String name, String cuit){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.cuit = new SimpleStringProperty(cuit);
    }
    
    public Docente(ResultSet rs) throws SQLException {
        this.id = new SimpleIntegerProperty(rs.getInt("id"));
        this.name = new SimpleStringProperty(rs.getString("fullname"));
        this.cuit = new SimpleStringProperty(rs.getString("cuit"));
    }
    
    public SimpleIntegerProperty idProperty(){
        return this.id;
    }
    public int getId(){
        return this.id.get();
    }
    
    public SimpleStringProperty nameProperty(){
        return this.name;
    }
    
    public SimpleStringProperty cuitProperty(){
        return this.cuit;
    }
    
    @Override
    public String toString(){
        return this.name.get();
    }
    
    
}
