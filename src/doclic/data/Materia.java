
package doclic.data;

import doclic.model.DocenteModel;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleIntegerProperty;


/**
 *
 * @author Agustín
 */
public class Materia extends CRUDData {
    
    @Override public Materia copy(){
        return new Materia(id.get(), name.get(), year.get(), div.get(), titular,
                suplente, load.get(), licensed);
    }
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty year;
    private final SimpleIntegerProperty div;
    private final SimpleStringProperty fullName;
    private final Docente titular;
    private final Docente suplente;
    private final SimpleIntegerProperty load;
    private final boolean licensed;
    
    private Materia(int id, String name, int year, int div,
            Docente tit, Docente sup, int load, boolean licensed){
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.year = new SimpleIntegerProperty(year);
        this.div = new SimpleIntegerProperty(div);
        this.titular = tit.copy();
        this.suplente = sup.copy();
        this.load = new SimpleIntegerProperty(load);
        this.licensed = licensed;
        
        this.fullName = new SimpleStringProperty(this.name + " " + this.year + "° " + this.div + "°");
    }
    
    public Materia(int id,
            String name, String year, String div,
            String carga){
        this.id =           new SimpleIntegerProperty(id);
        this.name =         new SimpleStringProperty(name);
        this.year =         new SimpleIntegerProperty(Integer.parseInt(year));
        this.div =          new SimpleIntegerProperty(Integer.parseInt(div));
        this.titular =      DocenteModel.getDefaultValue().copy();
        this.suplente =     DocenteModel.getDefaultValue().copy();
        this.load =         new SimpleIntegerProperty(Integer.parseInt(carga));
        this.licensed =     false;
        
        this.fullName = new SimpleStringProperty(this.name + " " + this.year + "° " + this.div + "°");
    }
    
    public Materia(ResultSet rs) throws SQLException {
        this.id         = new SimpleIntegerProperty(rs.getInt("id"));
        this.name       = new SimpleStringProperty(rs.getString("name"));
        this.year       = new SimpleIntegerProperty(rs.getInt("year"));
        this.div        = new SimpleIntegerProperty(rs.getInt("division"));
        int sqlTit     = rs.getInt("titular");
        this.titular    = DocenteModel.get()
                .find(doc -> doc.getId() == sqlTit)
                .orElse(DocenteModel.getDefaultValue())
                .copy();
        int sqlSup   = rs.getInt("suplente");
        this.suplente    = DocenteModel.get()
                .find(doc -> doc.getId() == sqlSup)
                .orElse(DocenteModel.getDefaultValue())
                .copy();
        this.load      = new SimpleIntegerProperty(rs.getInt("hourlyload"));
        this.licensed = rs.getBoolean("licenseactive");
        
        this.fullName = new SimpleStringProperty(this.name + " " + this.year + "° " + this.div + "°");
    }
    
    public int getId(){
        return this.id.get();
    }
    public SimpleStringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(this.id));
    }
    
    public String getName(){
        return this.name.get();
    }
    public SimpleStringProperty nameProperty(){
        return this.name;
    }
    
    public int getYear(){
        return this.year.get();
    }
    public int getDivision(){
        return this.div.get();
    }
    
    public SimpleStringProperty fullNameProperty(){
        return this.fullName;
    }
    
    public Docente getTitular(){
        return this.titular;
    }
    public SimpleStringProperty titularProperty(){
        return this.titular.nameProperty();
    }
    
    public Docente getSuplente(){
        return this.suplente;
    }
    public SimpleStringProperty suplenteProperty(){
        return this.suplente.nameProperty();
    }
    
    public int getLoad(){
        return this.load.get();
    }
    public SimpleStringProperty loadProperty(){
        return new SimpleStringProperty(String.valueOf(this.load) + " horas");
    }
    
    public boolean getLicensed(){
        return this.licensed;
    }
    public SimpleStringProperty licensedProperty(){
        if(licensed) return new SimpleStringProperty("Activa");
        else return new SimpleStringProperty("Inactiva");
    }
    
    @Override
    public String toString(){
        return this.name + " " + this.year + "° " + this.div + "°";
    }
    
}
