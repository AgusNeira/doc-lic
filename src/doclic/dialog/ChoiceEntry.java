package doclic.dialog;

import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;

/**
 *
 * @author Agustín
 * @param <T> the type of the ChoiceBox
 */
public class ChoiceEntry<T> extends Entry<T> {
    
    public ChoiceEntry(String lbl, ObservableList<T> choices,
            boolean required, T initial){
        super(lbl, required);
        
        this.node = new ChoiceBox<T>();
        ((ChoiceBox<T>) this.node).setItems(choices);
        ((ChoiceBox<T>) this.node).setValue(initial);

        this.getChildren().addAll((ChoiceBox<T>) this.node);
    }
    
    public ChoiceEntry(String lbl, ObservableList<T> choices, boolean required){
        super(lbl, required);
        
        this.node = new ChoiceBox<T>();
        ((ChoiceBox<T>) this.node).setItems(choices);

        this.getChildren().addAll((ChoiceBox<T>) this.node);
    }
    
    @Override public T getValue(){
        return ((ChoiceBox<T>) this.node).getValue();
    }
    
    @Override public boolean isEmptyCompliant(){
        return !(((ChoiceBox<T>) this.node).getSelectionModel().isEmpty() && required);
    }
    @Override public boolean isCompliant(){
        return isEmptyCompliant();
    }
}
