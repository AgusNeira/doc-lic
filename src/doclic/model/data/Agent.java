package doclic.model.data;

import java.sql.ResultSet;
import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author neira
 */
public class Agent extends Data {
    
    public static DataFactory<Agent> AgentFactory = (ResultSet rs) -> {
        Agent agent = new Agent();
        agent.setId(rs.getInt("id"));
        agent.setLastName(rs.getString("lname"));
        agent.setFirstName(rs.getString("fname"));
        agent.setCuilcuit(rs.getString("cuilcuit"));
        agent.setDegree(rs.getString("degree"));
        agent.setBirthDate(rs.getDate("birth").toLocalDate());
        agent.setPhone(rs.getString("phone"));
        agent.setIngreso(rs.getDate("ingreso").toLocalDate());
        
        return agent;
    };
    public static TableColumnsFactory AgentColumnsFactory = () -> {
        TableColumn<Agent, Integer> cid = new TableColumn("Código");
        cid.setCellValueFactory(new PropertyValueFactory("id"));
        
        TableColumn<Agent, String> clname = new TableColumn("Apellido");
        clname.setCellValueFactory(new PropertyValueFactory("lastName"));
        
        TableColumn<Agent, String> cfname = new TableColumn("Nombre");
        cfname.setCellValueFactory(new PropertyValueFactory("firstName"));
        
        TableColumn<Agent, String> ccuit = new TableColumn("CUIL-CUIT");
        ccuit.setCellValueFactory(new PropertyValueFactory("cuilcuit"));
        
        TableColumn<Agent, String> cdegree = new TableColumn("Título");
        cdegree.setCellValueFactory(new PropertyValueFactory("degree"));
        
        TableColumn<Agent, LocalDate> cbirth = new TableColumn("Fecha de nacimiento");
        cbirth.setCellValueFactory(new PropertyValueFactory("birth"));
        
        TableColumn<Agent, String> cphone = new TableColumn("Teléfono");
        cphone.setCellValueFactory(new PropertyValueFactory("phone"));
        
        TableColumn<Agent, LocalDate> cingreso = new TableColumn("Fecha de ingreso");
        cingreso.setCellValueFactory(new PropertyValueFactory("ingreso"));
        
        TableColumn[] columns = {cid, clname, cfname, ccuit, cdegree, cbirth, cphone, cingreso};
        return columns;
    };
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty lname;
    private final SimpleStringProperty fname;
    private final SimpleStringProperty cuilcuit;
    private final SimpleStringProperty degree;
    private final SimpleObjectProperty<LocalDate> birth;
    private final SimpleStringProperty phone;
    private final SimpleObjectProperty<LocalDate> ingreso;
    
    public SimpleIntegerProperty idProperty()               { return id; }
    public SimpleStringProperty lastNameProperty()          { return lname; }
    public SimpleStringProperty firstNameProperty()         { return fname; }
    public SimpleStringProperty cuilcuitProperty()            { return cuilcuit; }
    public SimpleStringProperty degreeProperty()            { return degree; }
    public SimpleObjectProperty<LocalDate> birthProperty()  { return birth; }
    public SimpleStringProperty phoneProperty()             { return phone; }
    public SimpleObjectProperty<LocalDate> ingresoProperty(){ return ingreso; }
    
    public void setId(int newid)                { id.set(newid); }
    public void setLastName(String newName)     { lname.set(newName); }
    public void setFirstName(String newName)    { fname.set(newName); }
    public void setCuilcuit(String newcuilcuit)   { cuilcuit.set(newcuilcuit); }
    public void setDegree(String newTitulo)     { degree.set(newTitulo); }
    public void setBirthDate(LocalDate newBirth){ birth.set(newBirth); }
    public void setPhone(String newPhone)       { phone.set(newPhone); }
    public void setIngreso(LocalDate newIngreso){ ingreso.set(newIngreso); }
    
    public Agent(){
        id = new SimpleIntegerProperty();
        lname = new SimpleStringProperty();
        fname = new SimpleStringProperty();
        cuilcuit = new SimpleStringProperty();
        degree = new SimpleStringProperty();
        birth = new SimpleObjectProperty<>();
        phone = new SimpleStringProperty();
        ingreso = new SimpleObjectProperty<>();
    }
    
}


