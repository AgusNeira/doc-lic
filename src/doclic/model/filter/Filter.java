/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doclic.model.filter;

import doclic.data.Data;
import doclic.model.DataList;
import doclic.model.ReadOnlyModel;
import java.util.function.Predicate;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.beans.property.ReadOnlyBooleanWrapper;

/**
 *
 * @author Agustín
 * @param <T>
 */
public abstract class Filter<T extends Data> extends Group {
    protected final Predicate<T> predicate;
    protected Node node;
    
    protected final ReadOnlyBooleanWrapper apply;
    
    public Filter(Predicate<T> pred){
        super();
        
        this.predicate = pred;
        this.apply = new ReadOnlyBooleanWrapper();
    }
    
    public final ReadOnlyBooleanWrapper applyProperty(){
        return this.apply;
    }
    public final Predicate<T> getPredicate(){
         return this.predicate;
    }
    
    public final void linkFilterToData(DataList<T> data){
                
        this.apply.addListener((obs, old, niw) -> {
            if(niw) data.addFilter(this.predicate);
            else data.removeFilter(this.predicate);
        });
    }
}
