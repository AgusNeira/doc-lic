
package doclic.model.data;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author neira
 */
public class Materia extends Data {
    
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<Curso> curso;
    private final SimpleIntegerProperty horascat;
    
    public SimpleIntegerProperty idProperty() { return id; }
    public SimpleStringProperty nameProperty() { return name; }
    public SimpleStringProperty cursoProperty() { return curso.get().cursoProperty(); }
    public SimpleIntegerProperty horascatProperty() { return horascat; }
    
    public void setId(int i) { id.set(i); }
    public void setName(String n) { name.set(n); }
    public void setCursoId(int i) { curso.get().setId(i); }
    public void setCursoName(String s) { curso.get().setCurso(s); }
    public void setHorascat(int i) { horascat.set(i); }
    
    public Materia(){
        id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        curso = new SimpleObjectProperty();
        horascat = new SimpleIntegerProperty();
    }
}
