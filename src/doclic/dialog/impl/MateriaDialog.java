package doclic.dialog.impl;

import doclic.data.Materia;
import doclic.dialog.CRUDDialog;
import doclic.dialog.DoubleTextEntry;
import doclic.dialog.TextEntry;
import doclic.model.MateriaModel;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

/**
 *
 * @author Agustín
 */
public class MateriaDialog extends CRUDDialog<Materia> {
    
    private final String NAME_REGEX = "^([A-Z][a-zA-ZÀ-ÿ\\u00f1\\u00d1]+)"
            + "(\\s[A-Z][a-zA-ZÀ-ÿ\\u00f1\\u00d1]+)*$";
    private final String YEAR_DIV_REGEX = "^\\d$";
    private final String LOAD_REGEX = "^\\d+$";
    
    @Override public MateriaDialog insert(){
        TextEntry nameEntry = new TextEntry("Nombre",
                true, NAME_REGEX);
        nameEntry.setFormattingMessage("Este campo debe estar conformado\n"
                + "por al menos una palabra que comience con mayúscula");
        DoubleTextEntry ydEntry = new DoubleTextEntry("Año y división", true,
                YEAR_DIV_REGEX, YEAR_DIV_REGEX);
        TextEntry loadEntry = new TextEntry("Carga", true, LOAD_REGEX);
        loadEntry.setFormattingMessage("Este campo debe ser un número entero");
        
        Callback<ButtonType, Materia> resultConverter = (buttonType) -> {
            if(buttonType == ButtonType.APPLY){
                return new Materia(
                        0,
                        nameEntry.getValue(),
                        ydEntry.getValue().first(),
                        ydEntry.getValue().second(),
                        loadEntry.getValue()
                );
            }
            return null;
        };
        
        return (MateriaDialog) this.reset(FORM_TYPE.INSERT,
                MateriaModel.getDefaultValue(),
                resultConverter, nameEntry, ydEntry,
                loadEntry
        );
        
    }
    @Override public MateriaDialog update(Materia selected){
        TextEntry nameEntry = new TextEntry("Nombre",
                true, NAME_REGEX, selected.getName());
        nameEntry.setFormattingMessage("Este campo debe estar conformado\n"
                + "por al menos una palabra que comience con mayúscula");
        DoubleTextEntry ydEntry = new DoubleTextEntry("Año y división",
                true, YEAR_DIV_REGEX, YEAR_DIV_REGEX,
                String.valueOf(selected.getYear()),
                String.valueOf(selected.getDivision()));
        TextEntry loadEntry = new TextEntry("Carga", true, LOAD_REGEX, String.valueOf(selected.getLoad()));
        loadEntry.setFormattingMessage("Este campo debe ser un número entero");
        
        Callback<ButtonType, Materia> resultConverter = (buttonType) -> {
            if(buttonType == ButtonType.APPLY){
                return new Materia(
                        selected.getId(),
                        nameEntry.getValue(),
                        ydEntry.getValue().first(),
                        ydEntry.getValue().second(),
                        loadEntry.getValue()
                );
            }
            return null;
        };
        
        return (MateriaDialog) this.reset(FORM_TYPE.INSERT,
                selected,
                resultConverter, nameEntry, ydEntry,
                loadEntry
        );
    }
    @Override public MateriaDialog delete(Materia mat){
        return (MateriaDialog) this.reset(mat);
    }
    
    public MateriaDialog(){ super(Materia.class); }    
}
