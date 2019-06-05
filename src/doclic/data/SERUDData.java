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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
                    id.get(), docente, materia, date0.get(),
                    date1.get(), date2.get(), reason.get(), observaciones.get());
        else
            return new SERUDData(
                    id.get(), docente, materia, date0.get(),
                    date1.get(), date2.get(), reason.get(), observaciones.get());
    }
    
    protected final SimpleIntegerProperty id;
    protected final Docente docente;
    protected final Materia materia;
    protected final SimpleObjectProperty<LocalDate> date0;
    protected final SimpleObjectProperty<LocalDate> date1;
    protected final SimpleObjectProperty<LocalDate> date2;
    protected final SimpleObjectProperty<Period> period;
    protected final SimpleStringProperty reason;
    protected final SimpleStringProperty observaciones;
    protected final boolean ended;
    
    protected final ReadOnlyObjectWrapper docenteName, materiaName;
    
    public SERUDData(ResultSet rs) throws SQLException {
        this.id = new SimpleIntegerProperty(rs.getInt("id"));
        long sqlDoc = rs.getLong("docente");
        this.docente = DocenteModel.get()
                .find(doc -> doc.getId() == sqlDoc)
                .orElse(DocenteModel.getDefaultValue())
                .copy();
        int sqlMat = rs.getInt("materia");
        this.materia = MateriaModel.get()
                .find(mat -> mat.getId() == sqlMat)
                .orElse(MateriaModel.getDefaultValue())
                .copy();
        this.date0 = new SimpleObjectProperty<>(rs.getDate("date0").toLocalDate());
        this.date1 = new SimpleObjectProperty<>(rs.getDate("date1").toLocalDate());
        Date sqlEnd = rs.getDate("date2");
        if(sqlEnd != null){
            this.date2 = new SimpleObjectProperty<>(sqlEnd.toLocalDate());
            this.ended = true;
        } else {
            this.date2 = null;
            this.ended = false;
        }
        this.reason = new SimpleStringProperty(rs.getString("reason"));
        this.observaciones = new SimpleStringProperty(rs.getString("observaciones"));
        
        this.docenteName = new ReadOnlyObjectWrapper();
        this.docenteName.bind(this.docente.nameProperty());
        
        this.materiaName = new ReadOnlyObjectWrapper();
        this.materiaName.bind(this.materia.nameProperty());
        
        if(ended) this.period = new SimpleObjectProperty<>(this.date0.get().until(this.date2.get()));
        else this.period = new SimpleObjectProperty<>(this.date0.get().until(LocalDate.now()));
    }
    
    public SERUDData(int id, Docente docente, Materia materia,
            LocalDate date0, LocalDate date1, LocalDate date2, String reason,
            String observaciones){
        this.id = new SimpleIntegerProperty(id);
        this.docente = docente.copy();
        this.materia = materia.copy();
        this.date0 = new SimpleObjectProperty<>(date0);
        this.date1 = new SimpleObjectProperty<>(date1);
        this.date2 = new SimpleObjectProperty<>(date2);
        this.reason = new SimpleStringProperty(reason);
        this.observaciones = new SimpleStringProperty(observaciones);
        
        this.ended = true;
        
        this.docenteName = new ReadOnlyObjectWrapper();
        this.docenteName.bind(this.docente.nameProperty());
        
        this.materiaName = new ReadOnlyObjectWrapper();
        this.materiaName.bind(this.materia.nameProperty());
        
        this.period = new SimpleObjectProperty<>(this.date0.get().until(this.date2.get()));
    }
    
    public SERUDData(int id, Docente doc, Materia mat, LocalDate date0,
            LocalDate date1, String reason, String observaciones){
        this.id = new SimpleIntegerProperty(id);
        this.docente = doc.copy();
        this.materia = mat.copy();
        this.date0 = new SimpleObjectProperty<>(date0);
        this.date1 = new SimpleObjectProperty<>(date1);
        this.date2 = null;
        this.reason = new SimpleStringProperty(reason);
        this.observaciones = new SimpleStringProperty(observaciones);
        
        this.ended = false;
        
        this.docenteName = new ReadOnlyObjectWrapper();
        this.docenteName.bind(this.docente.nameProperty());
        
        this.materiaName = new ReadOnlyObjectWrapper();
        this.materiaName.bind(this.materia.nameProperty());
        
        this.period = new SimpleObjectProperty<>(this.date0.get().until(LocalDate.now()));
    }
    
    public final boolean isEnded(){
        return this.ended;
    }
    
    public final SimpleIntegerProperty idProperty(){
        return this.id;
    }
    public final int getId(){
        return this.id.get();
    }
    
    public final ReadOnlyObjectWrapper docenteProperty(){
        return this.docenteName;
    }
    public final Docente getDocente(){
        return this.docente;
    }
    
    public final ReadOnlyObjectWrapper materiaProperty(){
        return this.materiaName;
    }
    public final Materia getMateria(){
        return this.materia;
    }
    
    public final SimpleStringProperty startDateProperty(){
        return new SimpleStringProperty(date0.get().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }
    public final LocalDate getDate0(){
        return this.date0.get();
    }
    
    public final SimpleStringProperty temptativeEndDateProperty(){
        return new SimpleStringProperty(date1.get().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }
    public final LocalDate getDate1(){
        return this.date1.get();
    }
    
    public final SimpleStringProperty endDateProperty(){
        if(ended)
            return new SimpleStringProperty(date2.get().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        return new SimpleStringProperty("Vigente");
    }
    public final LocalDate getDate2(){
        return this.date2.get();
    }
    
    public final SimpleStringProperty durationProperty(){
        return new SimpleStringProperty(
            String.valueOf(this.period.get().getYears()) + " años, " +
            String.valueOf(this.period.get().getMonths()) + " meses y " +
            String.valueOf(this.period.get().getDays()) + " días"
        );
    }
    public final Period getDuration(){
        return this.period.get();
    }
    public final SimpleStringProperty reasonProperty(){
        return this.reason;
    }
    public final String getReason(){
        return this.reason.get();
    }
    
    public final SimpleStringProperty observacionesProperty(){
        return this.observaciones;
    }
    public final String getObservaciones(){
        if(observaciones == null) return "";
        return this.observaciones.get();
    }
}
