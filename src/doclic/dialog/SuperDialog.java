/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.dialog;

import doclic.data.Data;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 *
 * @author Agustín
 * @param <T>
 */
public abstract class SuperDialog<T extends Data> extends Dialog<T> {
    
    public static enum FORM_TYPE { INSERT, UPDATE, START, END }
    
    protected T selected;
    protected VBox content;
    protected Button apply;
    protected Entry entries[];
    protected final Class<T> clazz;
    
    protected SuperDialog(Class<T> clazz){
        super();
        
        this.clazz = clazz;
    }
    
    protected SuperDialog reset(FORM_TYPE operation, T selected,
            Callback<ButtonType, T> resultConverter, Entry... form){
        this.clear();
        
        if(selected != null) this.selected = (T) selected.copy();
        
        this.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.APPLY, ButtonType.CANCEL);
        
        if(null != operation)
            switch (operation) {
            case INSERT:
                this.setTitle("Agregar " + Data.getClassName(clazz));
                break;
            case UPDATE:
                this.setTitle("Modificar " + Data.getClassName(clazz));
                break;
            case START:
                this.setTitle("Iniciar " + Data.getClassName(clazz));
                break;
            case END:
                this.setTitle("Finalizar " + Data.getClassName(clazz));
                break;
            default:
                this.setTitle("Operación desconocida");
                break;
        }
        this.content = new VBox(form);
        this.content.setPadding(new Insets(20, 10, 20, 10));
        
        this.getDialogPane().setContent(content);
        
        this.apply = (Button)this.getDialogPane().lookupButton(ButtonType.APPLY);
        this.apply.addEventFilter(ActionEvent.ACTION, this::validation);
        this.apply.setDefaultButton(true);
        
        this.setResultConverter(resultConverter);
        
        this.entries = form;
        
        return this;
    }
    
    protected SuperDialog reset(T selected){
        this.clear();
        
        Objects.requireNonNull(selected);
        this.selected = selected;
        
        this.getDialogPane()
                .getButtonTypes()
                .addAll(ButtonType.APPLY, ButtonType.CANCEL);
        
        this.setTitle("Eliminar elemento");
        this.setContentText(
                "Está seguro que desea eliminar al\n" +
                Data.getClassName(clazz) + " " + selected.toString() + "?");
        
        this.apply = (Button)this.getDialogPane().lookupButton(ButtonType.APPLY);
        this.apply.setDefaultButton(true);
        
        this.setResultConverter(buttonType -> {
            if(buttonType == ButtonType.APPLY) return this.selected;
            else return null;
        });
        
        return this;
    }
    
    protected final void clear(){
        this.selected = null;
        this.content = null;
        this.apply = null;
        this.entries = null;
        this.setHeaderText(null);
        this.setContentText(null);
        this.getDialogPane().getButtonTypes().clear();
        this.getDialogPane().setContent(null);
        this.setResultConverter(null);
        
    }
    
    protected final void validation(ActionEvent event){
        for(Entry entry : this.entries){
            if(!entry.validationFilter(event)) break;
        }
    }
    
    public interface SelectedAndResult<U extends Data>{
        public void exec(U selected, U result);
    }
    public final void ifResult(SelectedAndResult<T> operation){
        this.showAndWait()
            .ifPresent(obj -> operation.exec(selected, obj));
    }
    
}
