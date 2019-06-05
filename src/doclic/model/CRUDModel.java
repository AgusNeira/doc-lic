
package doclic.model;

import doclic.data.CRUDData;
import doclic.dialog.CRUDDialog;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;

/**
 *
 * @author Agustín
 * @param <T> Data type
 * 
 * 
 */
public abstract class CRUDModel<T extends CRUDData>
        extends ReadOnlyModel<T> {
    
    protected final HBox buttons;
    protected final Button insert, update, delete;
    
    protected T DEFAULT_VALUE = null;
    public final T getDEFAULT_VALUE() { return this.DEFAULT_VALUE; }
    
    public CRUDModel(
            Class<T> clazz,
            TableColumn<T, String>... columns
    ){
        super(clazz, columns);
        
        this.insert = new Button("Agregar");
        this.insert.setOnAction(this::insertOnAction);
        
        this.update = new Button("Modificar");
        this.update.addEventFilter(ActionEvent.ACTION, this::selectionFilter);
        this.update.setOnAction(this::updateOnAction);
        
        this.delete = new Button("Eliminar");
        this.delete.addEventFilter(ActionEvent.ACTION, this::selectionFilter);
        this.delete.setOnAction(this::deleteOnAction);
        
        this.buttons = new HBox(insert, update, delete);
        this.buttons.setSpacing(15);
        this.buttons.setAlignment(Pos.BASELINE_CENTER);
        
        this.content.getChildren().add(buttons);
    }
    
    abstract protected void insert(T obj);
    abstract protected void update(T from, T to);
    abstract protected void delete(T obj);
    
    protected final void insertOnAction(ActionEvent event){
        CRUDDialog.get(clazz)
            .insert()
            .ifResult((selected, result) -> this.insert((T) result));
    }
    protected final void updateOnAction(ActionEvent event){
        CRUDDialog.get(clazz)
            .update((T) table.getSelectionModel().getSelectedItem())
            .ifResult((selected, result) -> this.update((T) selected, (T) result));
    }
    protected final void deleteOnAction(ActionEvent event){
        CRUDDialog.get(clazz)
            .delete((T) table.getSelectionModel().getSelectedItem())
            .ifResult((selected, result) -> this.delete((T) selected));
    }
    
}
