package doclic.dialog;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author Agustín
 * @param <T> the type returned by getValue()
 */
public abstract class Entry<T> extends HBox {
    
    protected Label label;
    protected Node node;
    
    protected boolean required;
    
    protected final String EMPTY_MESSAGE = "Este campo no puede quedar vacío";
    
    public Entry(String lbl, boolean required){
        super();
        
        this.label = new Label(lbl);
        this.label.setMinWidth(150);
        this.required = required;
        this.getChildren().add(label);
    }
    
    public abstract T getValue();
    
    public abstract boolean isEmptyCompliant();
    public final    boolean isRequired(){
        return this.required;
    }
    public abstract boolean isCompliant();
    
    public String getMessage(){
        if(!isCompliant()) return EMPTY_MESSAGE;
        else return "";
    }
    
    public boolean validationFilter(ActionEvent event){
        if(!isCompliant()){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Advertencia");
           alert.setHeaderText(label.getText());
           alert.setContentText(getMessage());
           alert.showAndWait();
           
           event.consume();
           return false;
        }
        else return true;
    }
}
