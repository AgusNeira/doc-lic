/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 *
 * @author Agustín
 */
public class Licencia extends SERUDData {
    
    @Override public final SERUDData copy(){
        return new Licencia(id, docente, materia, date0,
                date1, date2, reason, observaciones);
    }
    
    public Licencia(ResultSet rs) throws SQLException {
        super(rs);
    }
    
    public Licencia(int id, Docente doc, Materia mat, LocalDate date0,
            LocalDate date1, LocalDate date2, String reason, String obs){
        super(id, doc, mat, date0, date1, date2, reason, obs);
    }
    public Licencia(int id, Docente doc, Materia mat, LocalDate date0,
            LocalDate date1, String reason, String obs){
        super(id, doc, mat, date0, date1, reason, obs);
    }
}
