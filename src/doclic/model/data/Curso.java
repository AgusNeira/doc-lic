/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model.data;

import java.sql.ResultSet;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author neira
 */
public class Curso extends Data {
    public static DataFactory CursoFactory = (ResultSet rs) -> {
        Curso m = new Curso();
        
        m.setId(rs.getInt("id"));
        m.setCurso(rs.getString("curso"));
        
        return m;
    };
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty curso;
    
    public SimpleIntegerProperty idProperty(){ return id; }
    public SimpleStringProperty cursoProperty(){ return curso; }
    
    public void setId(int i){ id.set(i); }
    public void setCurso(String c){ curso.set(c); }
    
    public Curso(){
        id = new SimpleIntegerProperty();
        curso = new SimpleStringProperty();
    }
}
