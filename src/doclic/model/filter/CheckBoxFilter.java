package doclic.model.filter;

import doclic.data.Data;
import java.util.function.Predicate;
import javafx.scene.control.CheckBox;

/**
 *
 * @author Agustín
 * @param <T>
 */
public class CheckBoxFilter<T extends Data> extends Filter {
    
    public CheckBoxFilter(
            String text,
            Predicate<T> pred){
        super(pred);
        
        this.node = new CheckBox(text);
        
        this.apply
            .bindBidirectional(((CheckBox) this.node).selectedProperty());
        
        ((CheckBox) this.node).setAllowIndeterminate(false);
        ((CheckBox) this.node).setSelected(false);
        
        this.getChildren().add(this.node);
    }
}
