
package doclic.model.data;

import java.sql.ResultSet;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author neira
 */
public class TipoMovimiento extends Data {
    public static DataFactory TipoMovimientoFactory = (ResultSet rs) -> {
        TipoMovimiento m = new TipoMovimiento();
        
        m.setId(rs.getInt("id"));
        m.setTipo(rs.getString("tipo"));
        
        return m;
    };
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty tipo;
    
    public SimpleIntegerProperty idProperty(){ return id; }
    public SimpleStringProperty tipoProperty(){ return tipo; }
    
    public void setId(int i){ id.set(i); }
    public void setTipo(String c){ tipo.set(c); }
    
    public TipoMovimiento(){
        id = new SimpleIntegerProperty();
        tipo = new SimpleStringProperty();
    }
}
