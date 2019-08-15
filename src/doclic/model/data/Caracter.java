
package doclic.model.data;

import java.sql.ResultSet;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author neira
 */
public class Caracter extends Data {
    public static DataFactory CaracterFactory = (ResultSet rs) -> {
        Caracter c = new Caracter();
        
        c.setId(rs.getInt("id"));
        c.setCaracter(rs.getString("caracter"));
        
        return c;
        
    };
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty caracter;
    
    public SimpleIntegerProperty idProperty(){ return id; }
    public SimpleStringProperty caracterProperty(){ return caracter; }
    
    public void setId(int i){ id.set(i); }
    public void setCaracter(String c){ caracter.set(c); }
    
    public Caracter(){
        id = new SimpleIntegerProperty();
        caracter = new SimpleStringProperty();
    }
}
