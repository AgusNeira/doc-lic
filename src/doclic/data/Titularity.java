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
public class Titularity extends SERUDData {
    
    @Override public final SERUDData copy(){
        return new Titularity(id.get(), docente, materia, date0.get(),
                date1.get(), date2.get(), reason.get(), observaciones.get());
    }
    
    public Titularity(ResultSet rs) throws SQLException {
        super(rs);
    }
    
    public Titularity(int id, Docente doc, Materia mat, LocalDate date0,
            LocalDate date1, LocalDate date2, String reason, String observaciones){
        super(id, doc, mat, date0, date1, date2, reason, observaciones);
    }
    
    public Titularity(int id, Docente doc, Materia mat, LocalDate date0,
            LocalDate date1, String reason, String observaciones){
        super(id, doc, mat, date0, date1, reason, observaciones);
    }
}
