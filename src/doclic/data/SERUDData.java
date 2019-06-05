/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.data;

import doclic.model.DocenteModel;
import doclic.model.MateriaModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Agustín
 */
public class SERUDData extends Data {
    
    @Override
    public SERUDData copy() {
        if(ended) 
            return new SERUDData(
                    id, docente, materia, date0,
                    date1, date2, reason, observaciones);
        else
            return new SERUDData(
                    id, docente, materia, date0,
                    date1, date2, reason, observaciones);
    }
    
    protected final int id;
    protected final Docente docente;
    protected final Materia materia;
    protected final LocalDate date0;
    protected final LocalDate date1;
    protected final LocalDate date2;
    protected final String reason;
    protected final String observaciones;
    protected final boolean ended;
    
    protected final ReadOnlyObjectWrapper docenteName, materiaName;
    
    public SERUDData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        long sqlDoc = rs.getLong("docente");
        this.docente = DocenteModel.get()
                .find(doc -> doc.getCUIT() == sqlDoc)
                .orElse(DocenteModel.getDefaultValue())
                .copy();
        int sqlMat = rs.getInt("materia");
        this.materia = MateriaModel.get()
                .find(mat -> mat.getId() == sqlMat)
                .orElse(MateriaModel.getDefaultValue())
                .copy();
        this.date0 = rs.getDate("date0").toLocalDate();
        this.date1 = rs.getDate("date1").toLocalDate();
        Date sqlEnd = rs.getDate("date2");
        if(sqlEnd != null){
            this.date2 = sqlEnd.toLocalDate();
            this.ended = true;
        } else {
            this.date2 = null;
            this.ended = false;
        }
        this.reason = rs.getString("reason");
        this.observaciones = rs.getString("observaciones");
        
        this.docenteName = new ReadOnlyObjectWrapper();
        this.docenteName.bind(this.docente.nameProperty());
        
        this.materiaName = new ReadOnlyObjectWrapper();
        this.materiaName.bind(this.materia.nameProperty());
    }
    
    public SERUDData(int id, Docente docente, Materia materia,
            LocalDate date0, LocalDate date1, LocalDate date2, String reason,
            String observaciones){
        this.id = id;
        this.docente = docente.copy();
        this.materia = materia.copy();
        this.date0 = date0;
        this.date1 = date1;
        this.date2 = date2;
        this.reason = reason;
        this.observaciones = observaciones;
        
        this.ended = true;
        
        this.docenteName = new ReadOnlyObjectWrapper();
        this.docenteName.bind(this.docente.nameProperty());
        
        this.materiaName = new ReadOnlyObjectWrapper();
        this.materiaName.bind(this.materia.nameProperty());
    }
    
    public SERUDData(int id, Docente doc, Materia mat, LocalDate date0,
            LocalDate date1, String reason, String observaciones){
        this.id = id;
        this.docente = doc.copy();
        this.materia = mat.copy();
        this.date0 = date0;
        this.date1 = date1;
        this.date2 = null;
        this.reason = reason;
        this.observaciones = observaciones;
        
        this.ended = false;
        
        this.docenteName = new ReadOnlyObjectWrapper();
        this.docenteName.bind(this.docente.nameProperty());
        
        this.materiaName = new ReadOnlyObjectWrapper();
        this.materiaName.bind(this.materia.nameProperty());
    }
    
    public final int getId(){
        return this.id;
    }
    public final Docente getDocente(){
        return this.docente;
    }
    public final Materia getMateria(){
        return this.materia;
    }
    public final LocalDate getDate0(){
        return this.date0;
    }
    public final LocalDate getDate1(){
        return this.date1;
    }
    public final LocalDate getDate2(){
        return this.date2;
    }
    public final Period getDuration(){
        if(ended) return date0.until(date2);
        return date0.until(LocalDate.now());
    }
    public final boolean isEnded(){
        return this.ended;
    }
    public final String getReason(){
        return this.reason;
    }
    public final String getObservaciones(){
        if(observaciones == null) return "";
        return this.observaciones;
    }
    
    public final SimpleStringProperty docenteProperty(){
        return new SimpleStringProperty(this.docente.toString());
    }
    public final SimpleStringProperty materiaProperty(){
        return new SimpleStringProperty(this.materia.toString());
    }
    public final SimpleStringProperty startDateProperty(){
        return new SimpleStringProperty(date0.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }
    public final SimpleStringProperty temptativeEndDateProperty(){
        return new SimpleStringProperty(date1.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }
    public final SimpleStringProperty endDateProperty(){
        if(ended)
            return new SimpleStringProperty(date2.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        return new SimpleStringProperty("Vigente");
    }
    public final SimpleStringProperty durationProperty(){
        Period duration = this.getDuration();
        return new SimpleStringProperty(
            String.valueOf(duration.getYears()) + " años, " +
            String.valueOf(duration.getMonths()) + " meses y " +
            String.valueOf(duration.getDays()) + " días"
        );
    }
    public final SimpleStringProperty reasonProperty(){
        return new SimpleStringProperty(this.reason);
    }
    public final SimpleStringProperty observacionesProperty(){
        return new SimpleStringProperty(this.observaciones);
    }
}
