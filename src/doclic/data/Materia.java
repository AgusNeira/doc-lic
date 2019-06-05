
package doclic.data;

import doclic.model.DocenteModel;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Agustín
 */
public class Materia extends CRUDData {
    
    @Override public Materia copy(){
        return new Materia(id, name, year, div, titular,
                suplente, load, licensed);
    }
    
    private final Integer id;
    private final String name;
    private final int year;
    private final int div;
    private final Docente titular;
    private final Docente suplente;
    private final Integer load;
    private final boolean licensed;
    
    private Materia(int id, String name, int year, int div,
            Docente tit, Docente sup, int load, boolean licensed){
        this.id = id;
        this.name = name;
        this.year = year;
        this.div = div;
        this.titular = tit.copy();
        this.suplente = sup.copy();
        this.load = load;
        this.licensed = licensed;
    }
    
    public Materia(int id,
            String name, String year, String div,
            String carga){
        this.id =    id;
        this.name =         name;
        this.year =         Integer.parseInt(year);
        this.div =          Integer.parseInt(div);
        this.titular =      DocenteModel.getDefaultValue().copy();
        this.suplente =     DocenteModel.getDefaultValue().copy();
        this.load =         Integer.parseInt(carga);
        this.licensed =     false;
    }
    
    public Materia(ResultSet rs) throws SQLException {
        this.id  = rs.getInt("id");
        this.name       = rs.getString("name");
        this.year       = rs.getInt("year");
        this.div        = rs.getInt("division");
        long sqlTit   = rs.getLong("titular");
        this.titular    = DocenteModel.get()
                .find(doc -> doc.getCUIT() == sqlTit)
                .orElse(DocenteModel.getDefaultValue())
                .copy();
        long sqlSup   = rs.getLong("suplente");
        this.suplente    = DocenteModel.get()
                .find(doc -> doc.getCUIT() == sqlSup)
                .orElse(DocenteModel.getDefaultValue())
                .copy();
        this.load      = rs.getInt("hourlyload");
        this.licensed = rs.getBoolean("licenseactive");
    }
    
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getYear(){
        return this.year;
    }
    public int getDivision(){
        return this.div;
    }
    public Docente getTitular(){
        return this.titular;
    }
    public Docente getSuplente(){
        return this.suplente;
    }
    public int getLoad(){
        return this.load;
    }
    public boolean getLicensed(){
        return this.licensed;
    }
    
    public SimpleStringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf(this.id));
    }
    
    public SimpleStringProperty nameProperty(){
        return new SimpleStringProperty(this.toString());
    }
    public SimpleStringProperty titularProperty(){
        return this.titular.nameProperty();
    }
    public SimpleStringProperty suplenteProperty(){
        return this.suplente.nameProperty();
    }
    public SimpleStringProperty loadProperty(){
        return new SimpleStringProperty(String.valueOf(this.load) + " horas");
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
