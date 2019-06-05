/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.dialog;

import java.time.LocalDate;
import javafx.scene.control.DatePicker;

/**
 *
 * @author Agustín
 */
public class DateEntry extends Entry<LocalDate> {
    
    public DateEntry(String lbl, boolean required, LocalDate initial){
        super(lbl, required);
        
        this.node = new DatePicker(initial);
        this.getChildren().addAll(node);
    }
    
    public DateEntry(String lbl, boolean required){
        super(lbl, required);
        
        this.node = new DatePicker(LocalDate.now());
        this.getChildren().addAll(node);
    }
    
    @Override public LocalDate getValue(){
        return ((DatePicker) node).getValue();
    }
    
    @Override public boolean isEmptyCompliant(){
        return !(((DatePicker) node).getValue() == null && required);
    }
    @Override public boolean isCompliant(){
        return isEmptyCompliant();
    }
}
